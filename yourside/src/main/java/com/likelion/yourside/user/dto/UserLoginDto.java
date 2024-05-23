package com.likelion.yourside.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserLoginDto {
    // 유효성 검사가 필요하다면, Annotation 추가
    private String username;
    private String password;
}
