package com.example.cloudsky.entity;

import com.example.cloudsky.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값 자동 증가
    private Long id;
    @Column(name = "commentcontents", nullable = false)
    private String commentcontents;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(CommentRequestDto commentRequestDto) {
        this.commentcontents = commentRequestDto.getCommentcontents();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.commentcontents = commentRequestDto.getCommentcontents();
    }

}