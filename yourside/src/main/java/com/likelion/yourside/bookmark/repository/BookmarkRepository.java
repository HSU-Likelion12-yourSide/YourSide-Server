package com.likelion.yourside.bookmark.repository;

import com.likelion.yourside.domain.Bookmark;
import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndPosting(User user, Posting posting);
}
