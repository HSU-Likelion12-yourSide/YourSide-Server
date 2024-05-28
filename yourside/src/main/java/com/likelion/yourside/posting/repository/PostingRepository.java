package com.likelion.yourside.posting.repository;

import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    @Query("select p from Posting p where p.user = :user")
    List<Posting> findALlByUser(User user);

    @Query("select p from Posting p where p.type = :type")
    List<Posting> findAllByType(int type);
}
