package com.project.sns.service;

import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public Long newPost(Post post) {
        return postRepository.save(post);
    }


    @Transactional
    public String delete(PostDto postDto) {

        Optional<Post> post = postRepository.findOne(postDto.getId());

        if (post.isEmpty()) {
            return "false";
        }

        postRepository.remove(post.get());

        return "success";
    }


    @Transactional
    public Post updatePost(PostDto postDto) {
        Optional<Post> getPost = postRepository.findOne(postDto.getId());

        Post post = getPost.orElseThrow(NoSuchElementException::new);
        post.update(postDto.getContent());

        return post;
    }


    public Optional<Post> findOneForId(Long id) {
        return postRepository.findOne(id);
    }

    public List<Post> findAllForUserId(Long userId) {
        return postRepository.findUserId(userId);
    }

}
