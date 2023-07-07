package com.example.cloudsky.repository;

import com.example.cloudsky.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentlikeRepository extends JpaRepository<Commentlike, Long> {

    Optional<Commentlike> findByUserAndPost(User user, Post post);

    Optional<Commentlike> findByPost(Post post);

    Optional<Commentlike> findByUserAndPostAndComment(User user, Post post, Comment comment);
}
