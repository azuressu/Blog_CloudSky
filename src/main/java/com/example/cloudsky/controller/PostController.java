package com.example.cloudsky.controller;

import com.example.cloudsky.dto.PostRequestDto;
import com.example.cloudsky.dto.PostResponseDto;
import com.example.cloudsky.entity.Post;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 선택 게시글 조회
//    @GetMapping("/post/{id}")
//    public PostResponseDto getOnePost(@PathVariable Long id) {
//        return postService.getOnePost(id);
//    }

    // 게시글 목록 조회
//    @GetMapping("/post")
//    public List<PostResponseDto> getAllPosts() {
//        List<Post> posts = postService.getAllPosts();
//        return posts.stream()
//                .map(PostResponseDto::new)
//                .collect(Collectors.toList());
//    }

    // 게시글 작성
    @PostMapping("/dev/post")
    public void createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        log.info("게시글 작성 시도");
        postService.createPost(requestDto, userDetails.getUser());
//        return new RedirectView("/");
    }

    // 게시글 수정
    @Transactional
    @PutMapping("/dev/post/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("게시글 수정 시도");
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }
    // 게시글 삭제
    @DeleteMapping("/dev/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }
}