package com.project.sns.service;

import com.project.sns.domain.Comment;
import com.project.sns.domain.dto.CommentDto;
import com.project.sns.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long newComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public boolean deleteComment(Long commentId) {
        return commentRepository.delete(commentId);
    }

    public Comment findClassId(Long id) {
        return commentRepository.findClassId(id).orElseThrow(NoSuchFieldError::new);
    }

    public List<CommentDto> findWithPostId(Long postId) {
        return commentRepository.findForPostId(postId);
    }


    public CommentDto findOneForId(Long id) {
        return commentRepository.findOneForId(id).orElseThrow(NoSuchFieldError::new);
    }


}
