package com.example.cloudsky.controller;

import com.example.cloudsky.dto.PostResponseDto;
import com.example.cloudsky.dto.SignupRequestDto;
import com.example.cloudsky.entity.Post;
import com.example.cloudsky.service.PostService;
import com.example.cloudsky.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Controller
public class PostViewController {

    private final PostService postService;
    private final UserService userService;

    // 메인 페이지 반환
    @GetMapping("/")
    public String getPostList(Model model) {
        List<PostResponseDto> posts = postService.getAllPosts().stream().map(PostResponseDto::new).collect(Collectors.toList());
        model.addAttribute("posts", posts); // 블로그 글 리스트 저장
        return "index";
    }

    // 로그인 페이지
    @GetMapping("/dev/user/login-page")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/dev/user/signup")
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

    @GetMapping("/dev/user/signup")
    public String signupPage() {
        return "signup";
    }

    // post 내용 반환
    @GetMapping("/dev/post/{id}")
    public String getOnePost(@PathVariable Long id, Model model) {
        Post post = postService.findByPostId(id);
        model.addAttribute("post", new PostResponseDto(post));

        return "post"; // post.html 뷰 조회
    }
}
