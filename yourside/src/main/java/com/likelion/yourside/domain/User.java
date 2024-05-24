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

    public void changeIsExpert() {this.isExpert = true;}
}
