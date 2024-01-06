package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.firstproject.entity.Article;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArticleServiceTest {

    //외부에서 객체를 주입해야하므로 Autowired 기재
    @Autowired
    ArticleService articleService;
    private ArticleService articleService1;

    @Test
    void index() {
        // 1. 예상 데이터
        Article a = new Article(1L, "a", "aaaaaa");
        Article b = new Article(2L, "b", "bbbbbb");
        Article c = new Article(3L, "c", "cccccc");
        List<Article> expected = new ArrayList<Article>(java.util.Arrays.asList(a, b, c));

        // 2. 실제 데이터
        List<Article> articles = articleService.index();

        // 3. 비교 및 검증 assertEquals(x,y) 예상데이터 x가 y와 일치하는지 검증
        assertEquals(expected.toString(), articles.toString());

//        asList -> 정적 리스트/ add, remove 불가
//       String[] arr = {"test1","test2","test3"};
//       List<String> arr_test = Arrays.asList(arr);
//
//        asList에 add, remove 하고싶은 경우 일반리스트로 새로생성
//        List<String> arr_test2 = new ArrayList<>(arr_test);
    }
    @Test
    void show_성공_존재하는_id입력() {
        //1. 예상데이터
        Long id = 1L;

        Article expected = new Article(id,"a","aaaaaa");

        //2. 실제데이터
        Article article = articleService.show(id);

        //3. 검증
        assertEquals(expected.toString(),article.toString());

    }
    @Test
    void show_실패_존재하지않는_id입력() {
        //1. 예상데이터
        Long id = -1L;

        Article expected = null;

        //2. 실제데이터
        Article article = articleService.show(id);

        //3. 검증
        assertEquals(expected,article);

    }

    @Transactional
    @Test
    void create_title_and_content_포함dto입력() {
        //1.예상 데이터
        ArticleForm dto = new ArticleForm(null,"d","dddddd");

        Article excepted = new Article(4L,"d","dddddd");

        //2.실제 데이터
        Article article = articleService.create(dto);
        //3.검증
        assertEquals(excepted.toString(),article.toString());
    }
    @Transactional
    @Test
    void create_실패_아이디가_포함된dto입력() {
        //1.예상 데이터
        ArticleForm dto = new ArticleForm(4L,"d","dddddd");

        Article excepted = null;

        //2.실제 데이터
        Article article = articleService.create(dto);
        //3.검증
        assertEquals(excepted,article);
    }



}

