package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//모든 필드값을 파라미터로 받는 생성자 생성 
@AllArgsConstructor
/*파라미터가 없는 기본 생성자 생성
@RequiredArgsConstructor은 final이나 @Nonnull인 필드 값만 파라미터로 받는 생성자 생성해줌.*/
@NoArgsConstructor
@ToString
@Getter
public class Article {
    @Id
    //DB가 알아서 id에 고유 값 부여
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

}
