package com.example.cloudsky.controller;

import com.example.cloudsky.dto.ProfileResponseDto;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ProfileViewController {

    private final UserService userService;

    // mypage 반환
    @GetMapping("/dev/mypage")
    public String getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        ProfileResponseDto profileResponseDto = userService.getMyPage(userDetails.getUser());

        // model 필요한 데이터 담아서 반환
        model.addAttribute("users", profileResponseDto);
        model.addAttribute("posts", profileResponseDto.getPosts());

        return "mypage";
    }

    @GetMapping("/dev/profile")
    public String getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "mypageupdate";
    }

    @GetMapping("/dev/password")
    public String confirmPassword(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return "password";
    }
    @GetMapping("/dev/passwordupdate")
    public String getPassword(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "passwordupdate";
    }
}
