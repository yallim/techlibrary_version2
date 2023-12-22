package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//모든 필드값을 파라미터로 받는 생성자 생성 
@AllArgsConstructor
/*파라미터가 없는 기본 생성자 생성
@RequiredArgsConstructor은 final이나 @Nonnull인 필드 값만 파라미터로 받는 생성자 생성해줌.*/
@NoArgsConstructor
@ToString
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

}
