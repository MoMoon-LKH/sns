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

    public boolean remove(Post post) {
        try {
            em.remove(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Post> findOne(Long postId) {
        return Optional.ofNullable(em.find(Post.class,postId));
    }



    public List<Post> findUserId(Long userId) {
        return em.createQuery("select p from Post p where p.user.id = :id", Post.class)
                .setParameter("id", userId)
                .getResultList();
    }

    public List<Post> findAll(int page) {
        return em.createQuery("select p from Post p order by p.create_date desc", Post.class)
                .setFirstResult(5 * page)
                .setMaxResults(5)
                .getResultList();
    }
}
