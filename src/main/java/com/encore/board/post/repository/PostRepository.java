package com.encore.board.post.repository;

import com.encore.board.post.domian.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>
{
//    List<Post> findAllByOrderByCreate_TimeDesc();
//    Pageable객체:  pageNumber(page = 1), page마다 개수(size = 10), 정렬(sort = create_time, desc)
//    Page : List<Post> + 해당Page의 각종정보
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByAppointmentNot(String appointment, Pageable pageable);

//    SELECT p.* FROM post p left join author a on a.id=p.author_id;
//    해당 jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적합
    @Query("select p from Post p left join p.author") //jpql문
    List<Post> findAllJoin();
    @Query("select p from Post p left join fetch p.author") //jpql문
    List<Post> findAllFetchJoin();
//    @Query("select p from Post p where p.appointMent = 'N'")
//    List<Post> findAllByNotAppointMent();
}
