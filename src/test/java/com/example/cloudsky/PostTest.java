//package com.example.cloudsky;
//
//import com.example.cloudsky.entity.Post;
//import com.example.cloudsky.entity.User;
//import com.example.cloudsky.entity.UserRoleEnum;
//import com.example.cloudsky.repository.PostRepository;
//import com.example.cloudsky.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.parameters.P;
//
//@SpringBootTest
//public class PostTest {
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    void test1() {
//        Post post = new Post();
//        User user = new User();
//
//        user.setUsername("username");
//        user.setRealname("닉네임");
//        user.setPassword("password");
//        user.setIntroduction("introduction");
//        user.setRole(UserRoleEnum.USER);
//
//        userRepository.save(user);
//
//        post.setTitle("title");
//        post.setContent("content");
//        post.setUser(user);
//
//
//        postRepository.save(post);
//
//
//    }
//}
