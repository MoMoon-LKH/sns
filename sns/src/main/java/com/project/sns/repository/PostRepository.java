package com.project.sns.repository;

import com.project.sns.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public void remove(Post post) {
        em.remove(post);
    }

    public Optional<Post> findOne(Long postId) {
        List<Post> posts = em.createQuery("select p from Post p where p.id = :id", Post.class)
                .setParameter("id", postId)
                .getResultList();
        return posts.stream().findAny();
    }



    public List<Post> findUserId(Long userId) {
        return em.createQuery("select p from Post p where p.user.id = :id", Post.class)
                .setParameter("id", userId)
                .getResultList();
    }
}
