package com.example.cloudsky.controller;

import com.example.cloudsky.dto.ApiResponseDto;
import com.example.cloudsky.security.UserDetailsImpl;
import com.example.cloudsky.service.LikeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

//@RestController
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dev/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}") // ResponseEntity<ApiResponseDto>
    public ResponseEntity<String>  denote(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("좋아요 시도");
        return likeService.denote(postId, userDetails);
//        return ResponseEntity.status(201).body(new ApiResponseDto("좋아요 성공", HttpStatus.CREATED.value()));
//        return new RedirectView("/");
    }

    @DeleteMapping("/{postId}")
    public void cancel(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.cancel(postId, userDetails);
//        return ResponseEntity.status(201).body(new ApiResponseDto("좋아요 취소", HttpStatus.CREATED.value()));
//        return new RedirectView("/");
    }
}
