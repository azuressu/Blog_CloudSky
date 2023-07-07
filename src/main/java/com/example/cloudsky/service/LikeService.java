package com.example.cloudsky.service;

import com.example.cloudsky.dto.CommentResponseDto;
import com.example.cloudsky.entity.*;
import com.example.cloudsky.repository.*;
import com.example.cloudsky.security.UserDetailsImpl;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentlikeRepository commentlikeRepository;

    public boolean likefind(Long postid, UserDetailsImpl userDetails) {
        User user = findUser(userDetails.getUsername());
        Post post = findPost(postid);

        if (likeRepository.findByUserAndPost(user, post).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    // 댓글 반환 리스트 수정 .. (해당 댓글 반환 타입마다 boolean 타입의 속성을 지정해줌)
    public List<CommentResponseDto> commentlikefind(Long postid, UserDetailsImpl userDetails, List<CommentResponseDto> list) {
        User user = findUser(userDetails.getUsername());
        Post post = findPost(postid);

        // post에 대한 comment 리스트를 만들어놓고, -> 그건 list임

        // comment like 에 있는 post에 대한 commentlike 리스트를 만든다 (해당 사용자와, post에 대한 리스트만 갖고옴)
        // 해당 게시글에 있는 댓글들 중, 사용자가 좋아요를 누른 댓글들의 좋아요 리스트만 갖고오게 됨
        List<Commentlike> commentlikeList = commentlikeRepository.findByUserAndPost(user, post).stream().toList();
        // 그 둘을 비교해서 responstdto에 boolean을 setting 해주면 되는데

        // for문을 돌아야 하나 ?
        for (CommentResponseDto c: list) {
            // 여기서 이제 하나씩 비교.. 를 해야되는데 .. 이중 for문 돌겠네
            for (Commentlike cc: commentlikeList) {
                // commentlike에 있는 댓글의 ld와, commentresponsedto에 있는 댓글의 id가 일치하면
                if (cc.getComment().getId() == c.getId()) {
                    c.setLike(true);
                } else {
                    c.setLike(false);
                }
            }
        }

        // 뭘 return 해줘야하나 ...
        return list;
    }

    // 게시글 좋아요 추가
    public ResponseEntity<String> denote(Long postId, UserDetailsImpl userDetails) {
        // 선택한 게시글이 DB에 있는지 확인
        Post post = findPost(postId);
        User user = userDetails.getUser();
        // 게시글을 작성한 당사자인지 검사
        if (user.getUsername().equals(post.getUser().getUsername())) {
            return ResponseEntity.badRequest().body("Error");
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
            return ResponseEntity.ok().body("Success");
        }
    }

    // 댓글 좋아요 추가
    public ResponseEntity<String> addLike(Long postId, Long commentId, UserDetailsImpl userDetails) {
        Post post = findPost(postId);
        Comment comment = findComment(commentId);
        User user = userDetails.getUser();

        if (user.getUsername().equals(comment.getUser().getUsername())) {
            return ResponseEntity.badRequest().body("Error");
        } else {
            if (commentlikeRepository.findByUserAndPostAndComment(user, post, comment).isPresent()) {
                throw new DuplicateRequestException("좋아요가 이미 눌러져 있습니다.");
            } else {
                Commentlike commentlike = new Commentlike(user, comment, post);
                commentlikeRepository.save(commentlike);

                // 해당 댓글의 likeconut + 1
                comment.setCommentlikeCount(comment.getCommentlikeCount() + 1);
                commentRepository.save(comment);
            }
            return ResponseEntity.ok().body("Success");
        }
    }

    // 게시글 좋아요 취소
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

    // 댓글 좋아요 취소
    public void cancleLike(Long postId, Long commentId, UserDetailsImpl userDetails) {
        // 선택한 댓글이 DB에 있는지 확인
        Post post = findPost(postId);
        Comment comment = findComment(commentId);
        User user = userDetails.getUser();

        // 현재 로그인한 사용자가 해당 게시글에 좋아요를 누른 적이 있는지 없는지 확인
        if (commentlikeRepository.findByUserAndPostAndComment(user, post, comment).isPresent()) {
            Commentlike commentlike = commentlikeRepository.findByUserAndPostAndComment(user, post, comment).orElseThrow(() -> new IllegalArgumentException("이 댓글에 좋아요가 눌러져 있지 않습니다."));
            commentlikeRepository.delete(commentlike);

            // 해당 게시글의 likecount - 1
            comment.setCommentlikeCount(comment.getCommentlikeCount() - 1);
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("이 댓글에 좋아요 눌러져 있지 않습니다.");
        }
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}