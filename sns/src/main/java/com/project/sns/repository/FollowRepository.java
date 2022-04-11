package com.project.sns.repository;


import com.project.sns.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private final EntityManager em;


    public Long save(Follow follow) {
        em.persist(follow);
        return follow.getId();
    }


    public boolean delete(Follow follow) {
        try {
            em.remove(follow);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Optional<Follow> getFollow(Long userId, Long followId) {
        return em.createQuery("select f from Follow f " +
                "where f.follower.id = :userId and f.followee.id = :followId", Follow.class)
                .setParameter("userId", userId)
                .setParameter("followId", followId)
                .getResultStream().findAny();
    }


    public List<Follow> userFollows(Long userId) {
        return em.createQuery("select f from Follow f where f.follower.id = :userId", Follow.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Follow> userFollowee(Long userId) {
        return em.createQuery("select f from Follow  f " +
                "where f.followee.id = :userId", Follow.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Long followCount(Long userId) {
        return em.createQuery("select f from Follow f " +
                "where f.followee.id = :id", Follow.class)
                .setParameter("id", userId)
                .getResultStream().count();
    }

}
