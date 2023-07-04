package com.example.cloudsky.controller;

import com.example.cloudsky.dto.PostResponseDto;
import com.example.cloudsky.entity.Post;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Slf4j
@Controller
public class PostViewController {

    private final PostService postService;

    // 메인 페이지 반환
    @GetMapping("/")
    public String getPostList(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 페이지 동적 처리 : 사용자 이름
        model.addAttribute("username", userDetails.getUser().getUsername());
        return "index";
    }

    // post 내용 반환
    @GetMapping("/dev/post/{id}")
    public String getOnePost(@PathVariable Long id, Model model) {
        Post post = postService.findByPostId(id);
        model.addAttribute("post", new PostResponseDto(post));

        return "post"; // post.html 뷰 조회
    }
}
