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

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Controller
public class PostViewController {

    private final PostService postService;

    // 메인 페이지 반환
    @GetMapping("/")
    public String getPostList(Model model) {
        List<PostResponseDto> posts = postService.getAllPosts().stream().map(PostResponseDto::new).collect(Collectors.toList());
        model.addAttribute("posts", posts); // 블로그 글 리스트 저장

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
