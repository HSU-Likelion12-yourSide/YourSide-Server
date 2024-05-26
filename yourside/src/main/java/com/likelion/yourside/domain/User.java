package com.likelion.yourside.domain;

import com.likelion.yourside.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private Long totalComments; //누적 댓글
    @Column(name="total_likes")
    private Long totalLikes;//누적 좋아요
    @Column(name = "delete_comments")
    private Long deleteComments;//삭제된 답변 수
    private int tier;//티어(일반인, 네편, 네편 메이트)

    public void changeIsExpert() {this.isExpert = true;}
}
