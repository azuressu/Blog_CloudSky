package com.example.cloudsky.controller;

import com.example.cloudsky.dto.*;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.PostService;
import com.example.cloudsky.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final PostService postService;

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
    @PutMapping("/profile")
    public ProfileResponseDto updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {
        return userService.updateProfile(userDetails.getUser(), profileRequestDto);
    }

    // 비밀번호 변경
    @PutMapping("/profile/password")
    public ResponseEntity<ApiResponseDto> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto passwordRequestDto) {
        try {
            userService.updatePassword(userDetails, passwordRequestDto);
            return ResponseEntity.ok().body(new ApiResponseDto("비밀번호 변경 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

}
