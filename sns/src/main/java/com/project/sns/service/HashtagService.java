package com.project.sns.service;

import com.project.sns.domain.Hashtag;
import com.project.sns.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;


    public Long save(String tag) {
        Hashtag hashtag = Hashtag.createHashTag(tag);
        return hashtagRepository.save(hashtag);
    }

    public boolean findStatus(String tag) {
        return hashtagRepository.find(tag);
    }
}
