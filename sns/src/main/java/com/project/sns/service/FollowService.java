package com.project.sns.service;

import com.project.sns.domain.Follow;
import com.project.sns.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;


    @Transactional
    public Long save(Follow follow) {
        return followRepository.save(follow);
    }


    @Transactional
    public boolean delete(Follow follow) {
        return followRepository.delete(follow);
    }


    public List<Follow> userFollows(Long userId) {
        return followRepository.userFollows(userId);
    }
    

    public List<Follow> userFollowee(Long userId) {
        return followRepository.userFollowee(userId);
    }


    public Follow getFollow(Long userId, Long followId) {
        return followRepository.getFollow(userId, followId).orElseThrow(NoSuchFieldError::new);
    }


    public Long followCount(Long userId) {
        return followRepository.followCount(userId);
    }
}
