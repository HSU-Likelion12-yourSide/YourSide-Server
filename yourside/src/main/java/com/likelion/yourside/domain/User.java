package com.likelion.yourside.domain;

import com.likelion.yourside.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    private String username; // 아이디
    private String password; // 비밀번호
    private String email;
    private String name; // 이름
    private String nickname; // 별명
    @Column(name="is_expert")
    private boolean isExpert; // "노무사" 자격 유무
    @Column(name="total_comments")
    private Long totalComments; // 누적 댓글
    @Column(name="total_postings")
    private Long totalPostings; // 누적 게시글
    @Column(name="total_likes")
    private Long totalLikes; // 누적 좋아요
    @Column(name = "delete_comments")
    private Integer deleteComments; // 삭제된 답변 수
    private int tier; // 티어(일반인 : 0, 네편 : 1, 네편 메이트 : 2)

    // Method
    public void changeIsExpert() {
        this.isExpert = true;
    }
    public void addDeleteComments() {
        this.deleteComments = (this.deleteComments == null) ? 1 : this.deleteComments + 1;
    }
    public void demoteToOrdinaryPerson() {
        this.tier = 0;
    }
    public void resetDeleteComments() {
        this.deleteComments = 0;
    }
    public void increaseTotalPostings() { this.totalPostings += 1; }
    public void increaseTotalLikes() { this.totalLikes += 1; }
    public void decreaseTotalLikes() { this.totalLikes -= 1; }
    public void increaseTotalComments() { this.totalComments += 1; }

    public void addDummy(boolean isExpert, Long totalComments, Long totalLikes, Long totalPostings, int deleteComments, int tier) {
        this.isExpert = isExpert;
        this.totalComments = totalComments;
        this.totalLikes = totalLikes;
        this.totalPostings = totalPostings;
        this.deleteComments = deleteComments;
        this.tier = tier;
    }
}