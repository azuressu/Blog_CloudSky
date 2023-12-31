package com.example.cloudsky.controller;

import com.example.cloudsky.dto.ApiResponseDto;
import com.example.cloudsky.dto.PasswordRequestDto;
import com.example.cloudsky.dto.ProfileRequestDto;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    // 프로필 수정
    @Transactional
    @PutMapping("/dev/profile")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {
        log.info("프로필 수정 시도");
        userService.updateProfile(userDetails.getUser(), profileRequestDto);
        log.info("프로필 수정 완료 후에 상태값 확인");
        URI redirectUri = URI.create("/dev/mypage"); // 리다이렉션할 URL
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(redirectUri)
                .build();
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
