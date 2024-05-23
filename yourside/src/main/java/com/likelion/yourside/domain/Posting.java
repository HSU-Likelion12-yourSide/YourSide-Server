package com.likelion.yourside.domain;

import com.likelion.yourside.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Posting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    private String title; // 제목
    private String content; // 내용
    @Column(name = "bookmark_count")
    private int bookmarkCount; // 북마크 수
    private String image; // 결과지 이미지 (S3 Image url)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
