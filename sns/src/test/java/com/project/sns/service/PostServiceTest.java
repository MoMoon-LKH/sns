package com.project.sns.service;

import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @Test
    @Transactional
    public void newPost() {
        //given
        User user = userService.findOneToEmail("test").orElseThrow(IllegalStateException::new);
        Post post = Post.createPost("test content", user);


        //when
        Long postId = postService.newPost(post);
        Post findPost = postService.findOneForId(postId).orElseThrow(IllegalStateException::new);


        //then
        assertThat(findPost.getContent()).isEqualTo(post.getContent());
    }


    @Test
    @Transactional
    public void updatePost() {

    }


    @Test
    public void findAll() {

    }


    @Test
    public void findOne() {

    }
}