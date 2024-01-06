package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        // create()는 id값이 필요없다. 따라서 객체에 id가 존재한다면, null을 반환
        if(article.getId() !=null){
            return null;
        }

        return articleRepository.save(article);
    }


    public Article update(ArticleForm form,@PathVariable Long id) {
        Article article = form.toEntity();
        Article target = articleRepository.findById(id).orElse(null);

        if(target==null || id!= target.getId()){
            return null;
        }

        target.patch(article);
        Article updated = articleRepository.save(article);
        return updated;

    }
    public Article delete(@PathVariable Long id){
        Article target = articleRepository.findById(id).orElse(null);

        if(target==null){
            return null;
        }
        articleRepository.delete(target); 
        return target;
    }

    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 엔티티 묶음으로 변환
        List<Article> articlesList = dtos.stream()
            .map(dto -> dto.toEntity())
            .collect(Collectors.toList());
        // 2. 엔티티 묶음을 DB에 저장
        articlesList.stream()
            .forEach(article -> articleRepository.save(article));
        
        // 3. 강제 예외 발생시키기
        // orElseThrow(): 값이 존재하면 그 값을 반환, 값이 존재하지 않으면 예외처리
        articleRepository.findById(-1L)
            .orElseThrow(()-> new IllegalArgumentException("결제 실패"));
        
        return articlesList;
    }

}

