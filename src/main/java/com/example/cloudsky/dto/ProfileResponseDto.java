package com.example.cloudsky.dto;

import com.example.cloudsky.entity.Post;
import com.example.cloudsky.entity.User;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

@Getter
public class ProfileResponseDto {
    private String username;
    // private String password;
    private String realname;
    private String introduction;
    private List<ProfilePostListResponseDto> posts;

    public ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.realname = user.getRealname();
        this.introduction = user.getIntroduction();
        this.posts = user.getPostList().stream().map(ProfilePostListResponseDto::new).sorted(Comparator.comparing(ProfilePostListResponseDto::getCreatedAt).reversed()).toList();
    }
}
