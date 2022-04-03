package com.project.sns.controller;

import com.project.sns.domain.Likes;
import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.CommentDto;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.domain.dto.UserDto;
import com.project.sns.service.CommentService;
import com.project.sns.service.LikeService;
import com.project.sns.service.PostService;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;


    @PostMapping("/new")
    public ResponseEntity<PostDto> newPost(@Valid @RequestBody PostDto postDto) {
        User user = userService.findOneWithEmail(postDto.getUser_email()).orElseThrow(NoSuchElementException::new);

        Post post = Post.createPost(postDto.getContent(), user);

        Long newId = postService.newPost(post);

        PostDto findPost = postService.findOneWithId(newId).orElseThrow(NoSuchElementException::new);

        return ResponseEntity.ok(findPost);
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(@Valid @RequestBody PostDto postDto) {

        // Post 에 대한 코멘트들 삭제
        List<CommentDto> commentDto = commentService.findWithPostId(postDto.getId());
        for (CommentDto comment : commentDto) {
            commentService.deleteComment(comment.getId());
        }

        return ResponseEntity.ok(postService.delete(postDto));
    }


    @PostMapping("/update")
    public ResponseEntity<Long> updatePost(@Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postDto).getId());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") Long id) {
        PostDto post = postService.findOneWithId(id).orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok(post);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostForUserId(@PathVariable("userId") Long userId) {
        List<PostDto> posts = postService.findAllWithUserId(userId);

        return ResponseEntity.ok(posts);
    }


    @GetMapping("/all/{page}")
    public ResponseEntity<List<PostDto>> getPost(@PathVariable("page") int page) {
        List<PostDto> posts = postService.findAll(page);


        return ResponseEntity.ok(posts);
    }



}
