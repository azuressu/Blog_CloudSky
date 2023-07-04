package com.example.cloudsky.service;

import com.example.cloudsky.entity.Like;
import com.example.cloudsky.entity.Post;
import com.example.cloudsky.entity.User;
import com.example.cloudsky.repository.LikeRepository;
import com.example.cloudsky.repository.PostRepository;
import com.example.cloudsky.security.UserDetailsImpl;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public void denote(Long postId, UserDetailsImpl userDetails) {
        // 선택한 게시글이 DB에 있는지 확인
        Post post = findPost(postId);
        User user = userDetails.getUser();
        // 게시글을 작성한 당사자인지 검사
        if (user.getUsername().equals(post.getUser().getUsername())) {
            throw new IllegalArgumentException("자신의 게시글에는 좋아요를 누를 수 없습니다.");
        } else {
            // 현재 로그인한 사용자가 해당 게시글에 좋아요를 이미 누르지 않았는지 검사
            if (likeRepository.findByUserAndPost(user, post).isPresent()) {
                throw new DuplicateRequestException("좋아요가 이미 눌러져 있습니다.");
            } else {
                Like like = new Like(user, post);
                likeRepository.save(like);
                // 해당 게시글의 likeCount +1
                post.setLikeCount(post.getLikeCount() + 1);
                postRepository.save(post);
            }
        }
    }

    public void cancel(Long postId, UserDetailsImpl userDetails) {
        // 선택한 게시글이 DB에 있는지 확인
        Post post = findPost(postId);
        User user = userDetails.getUser();
        // 현재 로그인한 사용자가 해당 게시글에 좋아요를 누른 적이 있는지 검사
        if (likeRepository.findByUserAndPost(user, post).isPresent()) {
            Like like = likeRepository.findByUserAndPost(user, post).orElseThrow(() -> new IllegalArgumentException("이 게시글에 좋아요가 눌러져 있지 않습니다."));
            likeRepository.delete(like);
            // 해당 게시글의 likeCount -1
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("이 게시글에 좋아요가 눌러져 있지 않습니다.");
        }
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

}