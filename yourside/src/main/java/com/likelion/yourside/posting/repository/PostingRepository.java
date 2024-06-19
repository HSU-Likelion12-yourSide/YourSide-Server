package com.likelion.yourside.posting.repository;

import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    @Query("select p from Posting p where p.user = :user")
    List<Posting> findAllByUser(User user);

    //전체 게시글 조회
    @Query("select p from Posting p where p.type = :type")
    List<Posting> findAllByType(int type);

    //타입별 인기 게시글 상위 3개 조회
    @Query(value = "SELECT * FROM POSTING p WHERE p.TYPE = :type ORDER BY p.bookmark_count DESC LIMIT 3", nativeQuery = true)
    List<Posting> findTopThreeByType(int type);
}
