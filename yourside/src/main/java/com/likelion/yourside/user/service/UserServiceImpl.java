package com.likelion.yourside.user.service;

import com.likelion.yourside.domain.User;
import com.likelion.yourside.user.dto.UserLoginRequestDto;
import com.likelion.yourside.user.dto.UserLoginResponseDto;
import com.likelion.yourside.user.dto.UserSignUpRequestDto;
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
    public ResponseEntity<CustomAPIResponse<?>> signUp(UserSignUpRequestDto signUpDto) {
        // 1. Nickname 중복 여부 파악
        String nickname = signUpDto.getNickname();
        Optional<User> foundUser = userRepository.findByNickname(nickname);
        if (foundUser.isPresent()) {
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

    @Override
    public ResponseEntity<CustomAPIResponse<?>> login(UserLoginRequestDto loginDto) {
        // 1. 해당 username을 가진 User가 존재하는지
        String username = loginDto.getUsername();
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isEmpty()) {
            // 1.1 data
            // 1.2 responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "일치하는 아이디가 없습니다.");
            // 1.3 ResponseEntity return
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        // 2. 비밀번호 일치 여부
        User user = foundUser.get();
        if (!user.getPassword().equals(loginDto.getPassword())) {
            // 2.1 data
            // 2.2 responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다.");
            // 2.3 ResponseEntity Return
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseBody);
        }
        // 3. 로그인 성공 Return
        // 3.1 data
        UserLoginResponseDto data = UserLoginResponseDto.builder()
                .id(user.getId())
                .build();
        // 3.2 response body
        CustomAPIResponse<UserLoginResponseDto> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "로그인 성공 하였습니다.");
        // 3.3 Response Entity Return
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);

    }


}
