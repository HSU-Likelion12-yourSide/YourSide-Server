package com.likelion.yourside.likes.repository;

import com.likelion.yourside.domain.Comment;
import com.likelion.yourside.domain.Likes;
import com.likelion.yourside.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndComment(User user, Comment comment);

}
