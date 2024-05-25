package com.likelion.yourside.bookmark.repository;

import com.likelion.yourside.domain.Bookmark;
import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndPosting(User user, Posting posting);

    @Query("select b from Bookmark b where b.user = :user")
    List<Bookmark> findAllByUser(User user);
}
