package com.project.sns.controller;

import com.project.sns.domain.Likes;
import com.project.sns.domain.Post;
import com.project.sns.domain.dto.LikeDto;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.service.LikeService;
import com.project.sns.service.PostService;
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
    private final LikeService likeService;


    @GetMapping("/{postId}")
    public ResponseEntity<Integer> getLikeCount(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(likeService.getLikeCount(postId));
    }


    @PostMapping("/plus")
    public ResponseEntity<Integer> plusLike(@Valid @RequestBody LikeDto likeDto) {

        Post post = postService.getPostClass(likeDto.getPost_id()).orElseThrow(NoSuchElementException::new);
        Likes likes = Likes.createLikes(post, likeDto.getUser_id());

        Long likeId = likeService.save(likes);

        return ResponseEntity.ok(likeService.getLikeCount(likeDto.getPost_id()));
    }

    @PostMapping("/minus")
    public ResponseEntity<?> minusLike(@Valid @RequestBody LikeDto likeDto) {
        Likes like = likeService.getLike(likeDto);

        if (likeService.delete(like)) {
            return ResponseEntity.ok(likeService.getLikeCount(likeDto.getPost_id()));
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제에 실패했습니다.");
        }
    }

}
