package com.example.cloudsky.controller;

import com.example.cloudsky.dto.ProfileRequestDto;
import com.example.cloudsky.dto.ProfileResponseDto;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Slf4j
@Controller
public class ProfileViewController {

    private final UserService userService;

    public ProfileViewController(UserService userService) {
        this.userService = userService;
    }

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
    public String getProfile() {
        return "mypageupdate";
    }

    @GetMapping("/dev/profile/password")
    public String confirmPassword(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "password";
    }

    @GetMapping("/dev/profile/passwordupdate")
    public String getPassword(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "passwordupdate";
    }
}
