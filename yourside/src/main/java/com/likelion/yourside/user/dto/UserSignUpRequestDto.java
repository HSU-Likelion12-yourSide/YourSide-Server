package com.likelion.yourside.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserSignUpRequestDto {
    // 유효성 검사 추가 해야하면 넣기.
    private String username;
    private String password;
    private String email;
    private String name;
    private String nickname;

}
