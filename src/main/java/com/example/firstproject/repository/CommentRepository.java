package com.example.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.firstproject.entity.Comment;

//페이지 처리와 정렬 처리 하기위해 CRUDRepository 대신 JpaRepository 사용
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //특정 게시글의 모든 댓글 조회
    //WHERE앞에 콜론(:)을 꼭붙여야 한다.
    @Query(value = "select * "+
                   "from comment " +
                   "where article_id=:articleId",
                    nativeQuery = true)
    List<Comment> findByAritlceId(Long articleId);

    //특정 닉네임의 모든 댓글 조회
    
    List<Comment> findByNickname(String nickname);
}
