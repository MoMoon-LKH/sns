package com.project.sns.repository;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.project.sns.domain.Likes;
import com.project.sns.domain.dto.LikeDto;
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


    public Optional<Likes> getLikeUser(LikeDto likeDto) {
        return em.createQuery("select l from Likes l " +
                "where l.post.id = :postId and " +
                "l.user_id = :userId", Likes.class)
                .setParameter("postId", likeDto.getPost_id())
                .setParameter("userId", likeDto.getUser_id())
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

    public LikeDto getLikeCount(LikeDto likeDto) {
        boolean activated;
        Long count = em.createQuery("select l from Likes l where l.post.id = :id", Likes.class)
                .setParameter("id", likeDto.getPost_id())
                .getResultStream().count();

        if (likeDto.getUser_id() == null) {
            activated = false;
        } else{
            activated = em.createQuery("select l from Likes  l where l.user_id = :userId and l.post.id = :postId", Likes.class)
                    .setParameter("userId",likeDto.getUser_id())
                    .setParameter("postId",likeDto.getPost_id())
                    .getResultStream().count() > 0;
        }

        likeDto.setLike_count(Math.toIntExact(count));
        likeDto.setActivated(activated);

        return likeDto;
    }


}
