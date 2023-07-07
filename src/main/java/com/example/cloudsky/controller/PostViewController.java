package com.example.cloudsky.controller;

import com.example.cloudsky.dto.CommentResponseDto;
import com.example.cloudsky.dto.PostRequestDto;
import com.example.cloudsky.dto.PostResponseDto;
import com.example.cloudsky.dto.ProfileResponseDto;
import com.example.cloudsky.entity.Commentlike;
import com.example.cloudsky.entity.Post;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.LikeService;
import com.example.cloudsky.service.PostService;
import com.example.cloudsky.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Controller
public class PostViewController {

    private final PostService postService;
    private final UserService userService;
    private final LikeService likeService;

    // 메인 페이지 반환
    @GetMapping("/")
    public String getPostList(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDto> posts = postService.getAllPosts().stream().map(PostResponseDto::new).collect(Collectors.toList());
        model.addAttribute("posts", posts); // 블로그 글 리스트 저장

        return "index";
    }

    // post 내용 반환
    @GetMapping("/dev/post/{id}")
    public String getOnePost(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Post post = postService.findByPostId(id);
        // 게시글 데이터
        PostResponseDto postResponseDto = new PostResponseDto(post);
        model.addAttribute("post", postResponseDto);
        // 게시글 좋아요 데이터
        Boolean like = likeService.likefind(id, userDetails);
        model.addAttribute("like", like);
        // 댓글 좋아요 데이터 - boolean 타입의 map으로 넘겨받아야 하나 ? map으로 넘겨받 .. 하

        List<CommentResponseDto> commentlist = likeService.commentlikefind(id, userDetails, postResponseDto.getCommentList());
        // 댓글 보여주기
        model.addAttribute("comments", commentlist);

        return "post"; // post.html 뷰 조회
    }

    // 새로운 post 반환
    @GetMapping("/dev/post")
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑 (id는 없을 수도 있음)
    public String newPost(@RequestParam(required=false) Long id, Model model) {
        if (id == null) { // id가 없으면 설정
            // 기본 생성자로 빈 객체를 만듦
            model.addAttribute("post", new PostResponseDto());
        } else { // id가 있으면 수정
            Post post = postService.findByPostId(id);
            // 기존 값을 가져오는 findById 메서드 호출
            model.addAttribute("post", new PostResponseDto(post));
        }
        return "newpost";
    }

}
