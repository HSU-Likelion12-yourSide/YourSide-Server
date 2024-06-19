package com.likelion.yourside.comment.repository;

import com.likelion.yourside.domain.Comment;
import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.posting = :posting")
    List<Comment> findAllByPosting(Posting posting);

    @Query("select c from Comment c where c.user = :user")
    List<Comment> findAllByUser(User user);

}
