package com.example.cloudsky.dto;

import com.example.cloudsky.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProfilePostListResponseDto {
    private String realname; // 사용자의 닉네임으로 표시하기
    private String title;
    private LocalDateTime createdAt;

    public ProfilePostListResponseDto(Post post) {
        this.title = post.getTitle();
        this.realname = post.getUser().getRealname();
        this.createdAt = post.getCreatedAt();
    }
}
