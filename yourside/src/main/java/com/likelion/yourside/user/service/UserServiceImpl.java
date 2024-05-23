package com.likelion.yourside.user.service;

import com.likelion.yourside.domain.User;
import com.likelion.yourside.user.dto.UserSignUpDto;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<CustomAPIResponse<?>> signUp(UserSignUpDto signUpDto) {
        // 1. Nickname 중복 여부 파악
        String nickname = signUpDto.getNickname();
        Optional<User> foundNickname = userRepository.findByNickname(nickname);
        if (foundNickname.isPresent()) {
            // 1-1. data
            // 1-2. ResponseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.CONFLICT.value(), "이미 존재하는 nickname 입니다.");
            // 1-3. ResponseEntity return
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(responseBody);
        }
        // 2. 회원가입 수행
        // 2-1. User 객체 저장
        User user = User.builder()
                .username(signUpDto.getUsername())
                .password(signUpDto.getPassword())
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .nickname(signUpDto.getNickname())
                .isExpert(false)
                .build();
        userRepository.save(user);
        // 2-2. Response
        // 2-2-1. data
        // 2-2-2. ResponseBody
        CustomAPIResponse<Object> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.CREATED.value(), "회원가입 되었습니다.");
        // 2-2-3. ResponseEntity return
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);
    }
}
