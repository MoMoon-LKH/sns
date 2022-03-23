package com.project.sns.controller;

import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.CommentDto;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.domain.dto.UserDto;
import com.project.sns.service.CommentService;
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


    @PostMapping("/new")
    public ResponseEntity<PostDto> newPost(@Valid @RequestBody PostDto postDto) {
        User user = userService.findOneWithEmail(postDto.getUserDto().getEmail()).orElseThrow(NoSuchElementException::new);

        Post post = Post.createPost(postDto.getContent(), user);

        Long newId = postService.newPost(post);

        Post findPost = postService.findOneWithId(newId).orElseThrow(NoSuchElementException::new);

        return ResponseEntity.ok(convertPostDto(findPost));
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
        Post post = postService.findOneWithId(id).orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok(convertPostDto(post));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostForUserId(@PathVariable("userId") Long userId) {
        List<Post> posts = postService.findAllWithUserId(userId);
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : posts) {
            postDtoList.add(convertPostDto(post));
        }

        return ResponseEntity.ok(postDtoList);
    }


    @GetMapping("/all/{page}")
    public ResponseEntity<List<PostDto>> getPost(@PathVariable("page") int page) {
        List<PostDto> postDtoList = new ArrayList<>();
        List<Post> posts = postService.findAll(page);

        for (Post post : posts) {
            postDtoList.add(convertPostDto(post));
        }

        return ResponseEntity.ok(postDtoList);
    }


    public PostDto convertPostDto(Post post) {
        User user = post.getUser();
        UserDto userDto = new UserDto(user.getEmail(),user.getPw(), user.getNickname(), user.getGender());

        return new PostDto(post.getId(), post.getContent(), post.getCreate_date(), post.getUpdate_date(), userDto);
    }
}
