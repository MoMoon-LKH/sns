package com.project.sns.service;

import com.project.sns.domain.Hashtag;
import com.project.sns.domain.Post;
import com.project.sns.domain.Post_Hashtag;
import com.project.sns.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;


    @Transactional
    public Long save(Post post, Hashtag hashtag) {
        return hashtagRepository.save(new Post_Hashtag(post, hashtag));
    }

    @Transactional
    public Long saveNewHashTag(String tag, Post post) {
        Hashtag hashtag = Hashtag.createHashTag(tag);
        return hashtagRepository.saveNew(hashtag, post);
    }

    public boolean findStatus(String tag) {
        return hashtagRepository.find(tag);
    }

}
