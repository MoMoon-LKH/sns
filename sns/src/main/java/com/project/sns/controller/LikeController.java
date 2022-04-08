package com.project.sns.controller;

import com.project.sns.domain.Likes;
import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.LikeDto;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.service.LikeService;
import com.project.sns.service.PostService;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/like")
public class LikeController {

    private final PostService postService;
    private final UserService userService;
    private final LikeService likeService;


    @GetMapping("/{postId}")
    public ResponseEntity<LikeDto> getLikeCount(@PathVariable("postId") Long postId) {
        LikeDto likeDto = LikeDto.builder().post_id(postId).build();
        return ResponseEntity.ok(likeService.getLikeCount(likeDto));
    }


    @PostMapping("/plus")
    public ResponseEntity<LikeDto> plusLike(@Valid @RequestBody LikeDto likeDto) {

        Post post = postService.getPostClass(likeDto.getPost_id()).orElseThrow(NoSuchElementException::new);
        User user = userService.findOneWithId(likeDto.getUser_id()).orElseThrow(NoSuchElementException::new);
        Likes likes = Likes.createLikes(post, user);

        Long likeId = likeService.save(likes);

        return ResponseEntity.ok(likeService.getLikeCount(likeDto));
    }

    @PostMapping("/minus")
    public ResponseEntity<?> minusLike(@Valid @RequestBody LikeDto likeDto) {
        Likes like = likeService.getLike(likeDto);

        if (likeService.delete(like)) {
            return ResponseEntity.ok(likeService.getLikeCount(likeDto));
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제에 실패했습니다.");
        }
    }

}
