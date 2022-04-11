package com.project.sns.controller;

import com.project.sns.domain.Comment;
import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.CommentDto;
import com.project.sns.service.CommentService;
import com.project.sns.service.PostService;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping("new")
    public ResponseEntity<CommentDto> newComment(@Valid @RequestBody CommentDto commentDto) {
        User user = userService.findOneWithId(commentDto.getUserId());
        Post post = postService.getPostClass(commentDto.getPostId());

        Comment comment = Comment.createComment(commentDto.getContent(), user, post);
        Long newId = commentService.newComment(comment);
        return ResponseEntity.ok(commentService.findOneForId(newId));
    }


    @PostMapping("delete")
    public ResponseEntity<Boolean> deleteComment(@Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.deleteComment(commentDto.getId()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> commentsOnPost(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.findWithPostId(postId));
    }
}
