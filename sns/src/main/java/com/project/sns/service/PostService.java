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
    public boolean delete(PostDto postDto) {

        
        Post post = postRepository.findOne(postDto.getId()).orElseThrow(NoSuchElementException::new);

        return postRepository.remove(post);
    }


    @Transactional
    public Post updatePost(PostDto postDto) {
        Optional<Post> getPost = postRepository.findOne(postDto.getId());

        Post post = getPost.orElseThrow(NoSuchElementException::new);
        post.update(postDto.getContent());

        return post;
    }


    public Optional<Post> findOneWithId(Long id) {
        return postRepository.findOne(id);
    }


    public List<Post> findAllWithUserId(Long userId) {
        return postRepository.findUserId(userId);
    }

    public List<Post> findAll(int page) {
        return postRepository.findAll(page);
    }

}
