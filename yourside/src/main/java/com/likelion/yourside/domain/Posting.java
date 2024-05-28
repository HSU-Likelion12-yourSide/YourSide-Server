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
    @Column(columnDefinition = "TEXT")
    private String content; // 내용
    @Column(name = "bookmark_count")
    private int bookmarkCount; // 북마크 수
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "worksheet_id")
    private Worksheet worksheet;
    private int type; //네편 현답: 0 , 네편 정보: 1

    public void changeBookmarkCount(boolean isIncrease) {
        if(isIncrease) this.bookmarkCount += 1;
        else this.bookmarkCount -= 1;
    }
}
