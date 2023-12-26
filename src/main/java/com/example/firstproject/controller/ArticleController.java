package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import org.springframework.ui.*;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*REST API 설계
 * 1. 조회
 * GET Article 목록 전체 또는 단일 Article 조회
 * ==> /api/articles 또는 /api/
 * 
 * 2. 생성
 * POST 새로운 Article 생성 후 목록에 저장
 * ==> /api/articles
 * 
 * 3.수정
 * PATCH 특정 Article 내용 수정
 * ==> /api/articles/{id}
 * 
 * 4. 삭제
 * DELETE 특정 Article 삭제
 * ==> /api/articles/{id}
 */

@Controller
//로그 찍는 어노테이션
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        //article(엔티티) 클래스가 DB에 잘 저장되는지 확인
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    /*@pathVariable : URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져옴
    articleEntity에 담긴 데이터를 모델에 등록. 데이터를 뷰페이지에 사용하기 위함
    */

    public String show(@PathVariable Long id, Model model){
        log.info("id="+id);
        /*레파지토리로 DB에 저장 된 데이터 가져오기 
        orElse 없이 코드 작성 시, 에러 -> DB에 id값이 null로 저장되어있기 때문
        orElse: id 값이 없을 경우, null을 넣고, 값이 없으면 null을 저장  */
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //해당 모델의 객체이름: article -> 머스테치에서 참조해서 사용
        model.addAttribute("article", articleEntity);

        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        //2. 모델에 데이터 등록
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰 페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        Article articleEntity = articleRepository.findById(id).orElse(null);

        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        Article articleEntity = form.toEntity();

        //기존 데이터 불러오기
        Article target =articleRepository.findById(articleEntity.getId()).orElse(null);

        //기존 데이터가 있으면, 새로운 데이터를 레포지토리에 저장
        if(target!=null){
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id,ArticleForm form, RedirectAttributes rtrr){
        Article target = articleRepository.findById(id).orElse(null);

        if(target!=null){
            articleRepository.delete(target);
            rtrr.addFlashAttribute("msg",target.getId()+"번 아이디가 삭제되었습니다!");
        }
        return "redirect:/articles";
    }

}
