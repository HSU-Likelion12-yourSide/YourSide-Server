package com.likelion.yourside.user.service;

import com.likelion.yourside.domain.User;
import com.likelion.yourside.user.dto.*;
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
                .totalPostings(0L)
                .totalLikes(0L)
                .totalComments(0L)
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
                .user_id(user.getId())
                .build();
        // 3.2 response body
        CustomAPIResponse<UserLoginResponseDto> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "로그인 성공 하였습니다.");
        // 3.3 Response Entity Return
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);

    }
    @Override
    public ResponseEntity<CustomAPIResponse<?>> checkDuplicationUsername(String username) {
        // 1. username 중복 시 실패 리턴
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isPresent()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.CONFLICT.value(), "존재하는 아이디입니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(responseBody);
        }
        // 2. 중복 아닐 시 성공 리턴
        // 2-1. data
        // 2-2. responseBody
        CustomAPIResponse<Object> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "사용 가능한 아이디입니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
    @Override
    public ResponseEntity<CustomAPIResponse<?>> checkUsername(String name, String email) {
        // 1. name, email로 조회 시 존재하는지 확인
        Optional<User> foundUser = userRepository.findByNameAndEmail(name, email);
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "아이디가 존재하지 않습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseBody);
        }
        // 2. 존재한다면 성공 리턴
        User user = foundUser.get();
        // 2-1. data
        UserCheckUsernameResponseDto data = UserCheckUsernameResponseDto.builder()
                .username(user.getUsername())
                .build();
        // 2-2. responseBody
        CustomAPIResponse<UserCheckUsernameResponseDto> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "아이디 찾기 완료되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
    @Override
    public ResponseEntity<CustomAPIResponse<?>> checkPassword(String name, String email, String username) {
        // 1. password 존재 여부 확인
        Optional<User> foundUser = userRepository.findByNameAndEmailAndUsername(name, email, username);
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "비밀번호가 존재하지 않습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseBody);
        }
        // 2. 존재하면 password 리턴
        User user = foundUser.get();
        // 2-1. data
        UserCheckPasswordResponseDto data = UserCheckPasswordResponseDto.builder()
                .password(user.getPassword())
                .build();
        // 2-2. responseBody
        CustomAPIResponse<UserCheckPasswordResponseDto> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "비밀번호 조회 완료되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
