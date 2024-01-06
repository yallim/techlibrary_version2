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

import lombok.extern.slf4j.Slf4j;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;

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
    
}
