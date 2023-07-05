package com.example.cloudsky.controller;

import com.example.cloudsky.dto.ApiResponseDto;
import com.example.cloudsky.dto.SignupRequestDto;
import com.example.cloudsky.jwt.JwtAuthenticationFilter;
import com.example.cloudsky.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/dev/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 로그인 페이지
    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // 회원가입
//    @PostMapping("/signup")
//    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
//        userService.signup(requestDto);
//        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
//    }

    @RequestMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException, IOException {
        jwtAuthenticationFilter.deleteAuthentication(response, authResult);
        return ResponseEntity.status(201).body(new ApiResponseDto("로그아웃 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size()>0) {
            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + "필드: "+fieldError.getDefaultMessage());
            }
            return "redirect:/dev/user/signup";
        }

        userService.signup(requestDto);

        return  "redirect:/dev/user/login-page";
    }
}
