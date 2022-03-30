package com.project.sns.service;

import com.project.sns.domain.Gender;
import com.project.sns.domain.Likes;
import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.domain.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void plusLike() {
        //given

        UserDto userDto = UserDto.builder().email("qwer").pw("qwer").gender(Gender.FEMALE).nickname("qwer").build();
        UserDto userDto2 = UserDto.builder().email("qwer2").pw("qwer2").gender(Gender.FEMALE).nickname("qwer2").build();

        User test = userService.findOneWithEmail("test").orElseThrow(NoSuchFieldError::new);
        User user1 = userService.findOneWithId(userService.signUp(userDto)).orElseThrow(NoSuchFieldError::new);
        User user2 = userService.findOneWithId(userService.signUp(userDto2)).orElseThrow(NoSuchFieldError::new);

        Post post = Post.createPost("dddd", test);

        Post findPost = postService.getPostClass(postService.newPost(post)).orElseThrow(NoSuchFieldError::new);

        Likes likes1 = Likes.createLikes(findPost, user1.getId());
        Likes likes2 = Likes.createLikes(findPost, user2.getId());

        //when
        likeService.save(likes1);
        likeService.save(likes2);

        PostDto postDto = postService.findOneWithId(findPost.getId()).orElseThrow(NoSuchFieldError::new);

        //then
        assertThat(postDto.getLike()).isEqualTo(2);

    }

    @Test
    @Transactional
    public void minusLike() {

        //given
        UserDto userDto = UserDto.builder().email("qwer").pw("qwer").gender(Gender.FEMALE).nickname("qwer").build();
        UserDto userDto2 = UserDto.builder().email("qwer2").pw("qwer2").gender(Gender.FEMALE).nickname("qwer2").build();

        User test = userService.findOneWithEmail("test").orElseThrow(NoSuchFieldError::new);
        User user1 = userService.findOneWithId(userService.signUp(userDto)).orElseThrow(NoSuchFieldError::new);
        User user2 = userService.findOneWithId(userService.signUp(userDto2)).orElseThrow(NoSuchFieldError::new);

        Post post = Post.createPost("dddd", test);

        Post findPost = postService.getPostClass(postService.newPost(post)).orElseThrow(NoSuchFieldError::new);

        Likes likes1 = Likes.createLikes(findPost, user1.getId());
        Likes likes2 = Likes.createLikes(findPost, user2.getId());

        likeService.save(likes1);
        likeService.save(likes2);

        PostDto postDto = postService.findOneWithId(findPost.getId()).orElseThrow(NoSuchFieldError::new);

        assertThat(postDto.getLike()).isEqualTo(2);


        PostDto getPostDto = PostDto.builder().id(findPost.getId()).user_id(user1.getId()).build();
        Likes findLikes = likeService.getLike(getPostDto);

        //when
        likeService.delete(findLikes);

        PostDto findPostDto = postService.findOneWithId(postDto.getId()).orElseThrow(NoSuchFieldError::new);

        assertThat(findPostDto.getLike()).isEqualTo(1);

    }

}