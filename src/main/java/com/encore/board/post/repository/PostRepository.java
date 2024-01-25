package com.encore.board.post.repository;

import com.encore.board.post.domian.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>
{
//    List<Post> findAllByOrderByCreate_TimeDesc();
//    SELECT p.* FROM post p left join author a on a.id=p.author_id;

//    해당 jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적합
    @Query("select p from Post p left join p.author") //jpql문
    List<Post> findAllJoin();
    @Query("select p from Post p left join fetch p.author") //jpql문
    List<Post> findAllFetchJoin();
}