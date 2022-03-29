package com.project.sns.service;

import com.project.sns.domain.Likes;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;


    @Transactional
    public Long save(Likes likes) {
        return likeRepository.save(likes);
    }

    @Transactional
    public boolean delete(Likes likes) {
        return likeRepository.delete(likes);
    }

    @Transactional
    public boolean deletePostLikes(Long postId) {
        List<Likes> likesList = likeRepository.getLikesAtPost(postId);
        return likeRepository.deleteList(likesList);
    }

    public Likes getLike(PostDto postDto) {
        return likeRepository.getLikeUser(postDto).orElseThrow(NoSuchFieldError::new);
    }

}
