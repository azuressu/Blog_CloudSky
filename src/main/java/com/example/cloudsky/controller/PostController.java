package com.example.cloudsky.controller;

import com.example.cloudsky.dto.PostRequestDto;
import com.example.cloudsky.dto.PostResponseDto;
import com.example.cloudsky.entity.Post;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/dev")
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
    @PostMapping("/post")
    public PostResponseDto createPost(HttpServletResponse response, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(requestDto, userDetails.getUser());
        // 게시글 전체 목록 조회하는 url로 넘기기
//        String redirect_uri = "http://localhost:8080/dev";
//        response.sendRedirect(redirect_uri);
    }

    // 게시글 수정
    @Transactional
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }
    // 게시글 삭제
    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
    }
}