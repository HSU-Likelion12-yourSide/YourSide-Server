package com.likelion.yourside.domain;

import com.likelion.yourside.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    @Column(columnDefinition = "TEXT")
    private String content; // 내용
    @Column(name = "like_count")
    private int likeCount; // 좋아요 수
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "posting_id")
    private Posting posting;

    public void changeLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
