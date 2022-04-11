package com.project.sns.service;

import com.project.sns.domain.Comment;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @Test
    @Transactional
    @Rollback
    public void saveComment() {
        //given
        String commentContent = "comment";

        User user = userService.findOneWithEmail("test");
        Post post = Post.createPost("test Text", user);
        postService.newPost(post);

        Comment comment = Comment.createComment(commentContent, user, post);

        //when
        Long commentId = commentService.newComment(comment);


        //then
        assertThat(commentContent).isEqualTo(commentService.findOneForId(commentId).getContent());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteComment() {
        //given
        Comment comment = initialNewComment();


        //when
        boolean bool = commentService.deleteComment(comment.getId());

        //then
        assertThat(bool).isTrue();

    }



    public Comment initialNewComment() {
        //given
        String commentContent = "comment";

        User user = userService.findOneWithEmail("test");
        Post post = Post.createPost("test Text", user);
        postService.newPost(post);

        Comment comment = Comment.createComment(commentContent, user, post);

        //when
        Long commentId = commentService.newComment(comment);

        return commentService.findClassId(commentId);
    }
}