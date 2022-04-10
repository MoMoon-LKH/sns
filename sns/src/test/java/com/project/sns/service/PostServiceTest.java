package com.project.sns.service;

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


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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
        User user = userService.findOneWithEmail("test").orElseThrow(IllegalStateException::new);
        Post post = Post.createPost("test content", user);


        //when
        Long postId = postService.newPost(post);
        PostDto findPost = postService.findOneWithId(postId).orElseThrow(IllegalStateException::new);


        //then
        assertThat(findPost.getContent()).isEqualTo(post.getContent());
    }


    @Test
    @Transactional
    public void updatePost() {
        //given
        Post post = addPost("test Text");
        User user = userService.findOneWithEmail("test").orElseThrow(NoSuchFieldError::new);
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .content("update_test_text")
                .create_date(post.getCreate_date())
                .build();

        //when
        Post updatePost = postService.updatePost(postDto);

        //then
        assertThat(updatePost.getContent()).isEqualTo(postDto.getContent());
    }


    @Test
    @Transactional
    public void delete() {
        //given
        Post post = addPost("test Text");
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .like(post.getLikes().stream().count())
                .create_date(post.getCreate_date())
                .update_date(post.getUpdate_date())
                .build();

        //when
        boolean delBoolean = postService.delete(postDto);

        assertThat(delBoolean).isTrue();

    }


    @Test
    @Transactional
    public void findAll() {
        //given
        int page = 0;
        int i = 1;
        int count = 7;
        while (i <= 7) {
            addPost("test page Text " + i);
            System.out.println("i = " + i);
            i++;
        }


        //when
        List<PostDto> posts = postService.findAll(page);

        System.out.println("-------------------------------");
        System.out.println("posts = " + posts);
        System.out.println("-------------------------------" );


        //then
        assertThat(posts.size()).isEqualTo(5);

        for (PostDto post : posts) {
            assertThat(post.getContent()).isEqualTo("test page Text " + --i);
            System.out.println("post.getContent() = " + post.getContent());

        }

    }


    @Test
    @Transactional
    public void findOne() {
        //given
        Post post = addPost("findOne");

        //when
        Post findPost = postService.getPostClass(post.getId()).orElseThrow(NoSuchFieldError::new);

        //then
        assertThat(post.getId()).isEqualTo(findPost.getId());
        assertThat(post).isSameAs(findPost);


    }

    public Post addPost(String content) {
        //given
        User user = userService.findOneWithEmail("test").orElseThrow(IllegalStateException::new);
        Post post = Post.createPost(content, user);


        //when
        Long postId = postService.newPost(post);
        return postService.getPostClass(postId).orElseThrow(IllegalStateException::new);
    }


    @Test
    @Transactional
    public void getPostTag() {
        //given
        String tag = "#test";

        User user = userService.findOneWithEmail("test").orElseThrow(IllegalStateException::new);
        Post post1 = Post.createPost("test " + tag + " asdqw", user);
        Post post2 = Post.createPost(tag + " test sds ", user);
        Post post3 = Post.createPost("test #tes sds ", user);

        postService.newPost(post1);
        postService.newPost(post2);
        postService.newPost(post3);

        //when
        List<PostDto> list = postService.getPostWithTag(tag);

        //then
        assertThat(list.size()).isEqualTo(2);

        for (PostDto post : list) {
            System.out.println("--------------------------------");
            System.out.println("content : " + post.getContent());
            assertThat(post.getContent().contains(tag)).isTrue();
        }

    }
}