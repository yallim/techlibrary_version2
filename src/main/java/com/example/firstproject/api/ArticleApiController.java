package com.example.firstproject.api;

import java.util.List;

import com.example.firstproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;

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
 * PATCH 특정 Article 내용 수정, 일부만 수정
 * ==> /api/articles/{id}
 * 
 * 4. 삭제
 * DELETE 특정 Article 삭제
 * ==> /api/articles/{id}
 */

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //REST API에서 반환값을 JSON으로 얻기 위해 @RequestBody어노테이션 사용
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);

        return (created!=null) ?
        ResponseEntity.status(HttpStatus.OK).body(created):
        ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//    //잘못 된 수정 요청에 대한 예외처리 필요, 반환 값을 Repository에 담은 데이터로 설정
//    //RepositoryEntity -> REST API 컨트롤러의 반환형
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm form){
//        Article article = form.toEntity();
//        Article target = articleRepository.findById(id).orElse(null);
//
//        if(target==null || id!=article.getId()){
//            log.info("id: {}, article: {}", id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        //일부 값만 수정할 경우 patch 메서드 수행
//        target.patch(article);
//        Article updated = articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//
//    }
//
//    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Article> delete(@PathVariable Long id, ArticleForm form){
//        Article target = articleRepository.findById(id).orElse(null);
//        if(target==null){
//            ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
//        }
//        articleRepository.delete(target);
//        // ~.body == .build()
//        //build() => HTTP 응답 바디가 없는 ResponseEntity 객체 생성
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }


    //잘못 된 수정 요청에 대한 예외처리 필요, 반환 값을 Repository에 담은 데이터로 설정
    //RepositoryEntity -> REST API 컨트롤러의 반환형
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm form){
        Article updated = articleService.update(form,id);

        return (updated!=null)?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);

        return (deleted!=null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Transactional
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transcationTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticles(dtos);

        return (createdList!=null)?
                ResponseEntity.status(HttpStatus.OK).body(createdList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
