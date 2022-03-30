package com.project.sns.repository;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.project.sns.domain.Likes;
import com.project.sns.domain.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final EntityManager em;


    public Long save(Likes like) {
        em.persist(like);
        return like.getId();
    }

    public boolean delete(Likes likes) {
        try {
            em.remove(likes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Optional<Likes> getLikeUser(PostDto postDto) {
        return em.createQuery("select l from Likes l " +
                "where l.post.id = :postId and " +
                "l.user_id = :userId", Likes.class)
                .setParameter("postId", postDto.getId())
                .setParameter("userId", postDto.getUser_id())
                .getResultList().stream().findAny();
    }

    public boolean deleteList(List<Likes> likes) {
        try {
            for (Likes like : likes) {
                em.remove(like);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Likes> getLikesAtPost(Long postId) {
        return em.createQuery("select l from Likes l where l.post.id = :id", Likes.class)
                .setParameter("id", postId)
                .getResultList();
    }


}
