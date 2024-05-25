package com.likelion.yourside.bookmark.repository;

import com.likelion.yourside.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
