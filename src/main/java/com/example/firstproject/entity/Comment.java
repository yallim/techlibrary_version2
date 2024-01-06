package com.example.firstproject.entity;

import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키 설정:article_id, Article 클래스의 기본키(id)와 매칭
    @JoinColumn(name="article_id")
    //Article(게시글)과 댓글관계 관계 정의
    @ManyToOne
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

}
