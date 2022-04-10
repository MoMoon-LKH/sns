package com.project.sns.service;

import com.project.sns.domain.Post;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.PostDto;
import com.project.sns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        
        Post post = postRepository.findPostClass(postDto.getId()).orElseThrow(NoSuchElementException::new);

        return postRepository.remove(post);
    }


    @Transactional
    public Post updatePost(PostDto postDto) {
        Optional<Post> getPost = postRepository.findPostClass(postDto.getId());

        Post post = getPost.orElseThrow(NoSuchElementException::new);
        post.update(postDto.getContent());

        return post;
    }


    public Optional<PostDto> findOneWithId(Long id) {
            return postRepository.findOne(id);
    }


    public List<PostDto> findAllWithUserId(Long userId) {
        List<Post> posts = postRepository.findUserId(userId);
        return getPostDtos(posts);
    }

    public List<PostDto> findAll(int page) {
        List<Post> posts = postRepository.findAll(page);
        return getPostDtos(posts);
    }


    public List<PostDto> getPostWithTag(String tag) {
        return getPostDtos(postRepository.findPostTag(tag));
    }


    public Optional<Post> getPostClass(Long id) {
        return postRepository.findPostClass(id);
    }


    private List<PostDto> getPostDtos(List<Post> posts) {
        List<PostDto> postList = new ArrayList<>();

        for (Post post : posts) {
            postList.add(
                    new PostDto(post.getId(), post.getContent(), (long) post.getLikes().size(), post.getCreate_date(), post.getUpdate_date(), post.getUser().getId(), post.getUser().getEmail(), post.getUser().getNickname())
            );
        }

        return postList;
    }
}
