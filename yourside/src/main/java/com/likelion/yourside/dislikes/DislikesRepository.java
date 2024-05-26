package com.likelion.yourside.dislikes;

import com.likelion.yourside.domain.Comment;
import com.likelion.yourside.domain.Dislikes;
import com.likelion.yourside.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DislikesRepository extends JpaRepository<Dislikes, Long> {
    Optional<Dislikes> findByUserAndComment(User user, Comment comment);
}
