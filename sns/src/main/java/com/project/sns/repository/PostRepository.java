package com.project.sns.repository;

import com.project.sns.domain.Post;
import com.project.sns.domain.dto.PostDto;
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


    public Optional<Post> findPostClass(Long id) {
        return Optional.ofNullable(em.find(Post.class, id));
    }


    public Optional<PostDto> findOne(Long postId) {
        return em.createQuery("select " +
                "new com.project.sns.domain.dto.PostDto(p.id, p.content, count(l.id), p.create_date, p.update_date, u.id, u.email, u.nickname) " +
                "from Post p " +
                "left join Likes l on l.post.id = p.id " +
                "left join User u on u.id = p.user.id  " +
                "where p.id = :id", PostDto.class)
                .setParameter("id", postId)
                .getResultList().stream().findAny();
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


    public List<Post> findPostTag(String hashtag) {
        return em.createQuery("select p " +
                "from Post p " +
                "left join Likes l on l.post.id = p.id " +
                "left join User u on u.id = p.user.id  " +
                "where p.content like concat('%', :hashtag, '%')", Post.class)
                .setParameter("hashtag", hashtag)
                .getResultList();
    }
}
