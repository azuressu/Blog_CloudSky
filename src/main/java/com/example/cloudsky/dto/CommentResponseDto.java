package com.example.cloudsky.dto;

import com.example.cloudsky.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String username;
    private String commentcontents;
    private Integer commentlikeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer likeCount;
    private boolean like;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.commentcontents = comment.getCommentcontents();
        this.commentlikeCount = comment.getCommentlikeCount();
        this.createdAt = comment.getCreatedAt();
        this.likeCount = comment.getCommentlikeCount();
        this.modifiedAt = comment.getModifiedAt();
    }
}
