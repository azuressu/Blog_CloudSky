package com.example.cloudsky.service;

import com.example.cloudsky.dto.PasswordRequestDto;
import com.example.cloudsky.dto.ProfileRequestDto;
import com.example.cloudsky.dto.ProfileResponseDto;
import com.example.cloudsky.dto.SignupRequestDto;
import com.example.cloudsky.entity.User;
import com.example.cloudsky.entity.UserRoleEnum;
import com.example.cloudsky.repository.UserRepository;
import com.example.cloudsky.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String realname = requestDto.getRealname();
        String introduction = requestDto.getIntroduction();
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(username, password, realname, introduction, role);
        userRepository.save(user);
    }

    // 프로필 조회
    public ProfileResponseDto getMyPage(User user) {
        return new ProfileResponseDto(user);
    }

    // 프로필 수정
    @Transactional
    public void updateProfile(User user, ProfileRequestDto profileRequestDto) {
        log.info("회원정보 수정");
        user.setRealname(profileRequestDto.getRealname());
        user.setIntroduction(profileRequestDto.getIntroduction());
        log.info("회원정보 수정 시도");
        userRepository.save(user);
        log.info("회원정보 수정 완료");
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(UserDetailsImpl userDetails, PasswordRequestDto passwordRequestDto) {
        User user = userDetails.getUser();
        String password = passwordEncoder.encode(passwordRequestDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    // 비밀번호 확인
    public ResponseEntity<String> confirmPassword(UserDetailsImpl userDetails, PasswordRequestDto passwordRequestDto) {
        log.info(userDetails.getPassword());
        log.info(passwordRequestDto.getPassword());

        if (passwordEncoder.matches(passwordRequestDto.getPassword(), userDetails.getPassword())) {
            log.info("성공");
            return ResponseEntity.ok("Success");
        } else {
            log.info("실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"); // 상태 코드 400 반환
        }
    }
}
