package com.example.cloudsky.controller;

import com.example.cloudsky.dto.ApiResponseDto;
import com.example.cloudsky.dto.PasswordRequestDto;
import com.example.cloudsky.dto.ProfileRequestDto;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    // 프로필 조회
//    @GetMapping("/my-page")
//    public String getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
//        ProfileResponseDto profileResponseDto = userService.getMyPage(userDetails.getUser());
//
//        // 속성을 담을 Map 객체 생성
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("users", profileResponseDto);
//        attributes.put("post", profileResponseDto.getPosts());
//        model.addAllAttributes(attributes);
//
//        return "mypage";
//    }

    // 프로필 수정
    @Transactional
    @PutMapping("/dev/profile")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {
        log.info("프로필 수정 시도");
        return userService.updateProfile(userDetails.getUser(), profileRequestDto);
    }

    // 비밀번호 확인
    @PostMapping("/dev/profile/password")
    public ResponseEntity<String> checkPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto passwordRequestDto) {
        log.info("비밀번호 일치 여부 확인");
        return userService.confirmPassword(userDetails, passwordRequestDto);
    }

    // 비밀번호 변경
    @Transactional
    @PutMapping("/dev/profile/passwordupdate")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto passwordRequestDto) {
        try {
            userService.updatePassword(userDetails, passwordRequestDto);
            return ResponseEntity.ok().body("Success");
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
