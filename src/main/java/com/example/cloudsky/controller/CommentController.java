package com.example.cloudsky.controller;

import com.example.cloudsky.dto.ApiResponseDto;
import com.example.cloudsky.dto.CommentRequestDto;
import com.example.cloudsky.dto.CommentResponseDto;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.CommentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {


    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("/dev/post/{id}/comment")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, requestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/dev/post/{id}/comment/{commentid}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @PathVariable Long commentid, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse res) {
        return commentService.updateCommnet(id, commentid, requestDto, userDetails.getUser(), res);
    }

    @DeleteMapping("/dev/post/{id}/comment/{commentid}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, @PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, commentid, userDetails.getUser());
    }
}
