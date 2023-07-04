package com.example.cloudsky.repository;

import com.example.cloudsky.entity.Like;
import com.example.cloudsky.entity.Post;
import com.example.cloudsky.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);

}
