package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;

import lombok.AllArgsConstructor;

//생성자 자동추가 어노테이션
@AllArgsConstructor
public class ArticleForm {
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드

    public Article toEntity(){
        return new Article(null, title, content);
    }

}
