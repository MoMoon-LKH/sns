package com.project.sns.controller;

import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.domain.dto.UserDto;
import com.project.sns.service.PostService;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final UserService userService;
    private final PostService postService;


    @PostMapping("/new")
    public ResponseEntity<Long> newPost(@Valid @RequestBody PostDto postDto, @Valid @RequestBody UserDto userDto) {
        User user = userService.findOneToEmail(userDto.getEmail()).orElseThrow(NoSuchElementException::new);

        Post post = Post.createPost(postDto.getContent(), user);

        Long newId = postService.newPost(post);

        return ResponseEntity.ok(newId);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody PostDto postDto, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(postService.delete(postDto));

    }

    @PostMapping("/update")
    public ResponseEntity<Long> updatePost(@Valid @RequestBody PostDto postDto, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(postService.updatePost(postDto).getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") Long id) {
        Post post = postService.findOneForId(id).orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok(convertPostDto(post));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostForUserId(@PathVariable("userId") Long userId) {
        List<Post> posts = postService.findAllForUserId(userId);
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : posts) {
            postDtoList.add(convertPostDto(post));
        }

        return ResponseEntity.ok(postDtoList);
    }

    /*
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getPost() {
        return ResponseEntity
    }*/


    public PostDto convertPostDto(Post post) {

        return new PostDto(post.getId(), post.getContent(), post.getCreate_date(), post.getUpdate_date());
    }
}
