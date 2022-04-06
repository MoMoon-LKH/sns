package com.project.sns.repository;

import com.project.sns.domain.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashtagRepository {

    private final EntityManager em;

    public Long save(Hashtag hashtag) {
        em.persist(hashtag);
        return hashtag.getId();
    }

    public boolean find(String tag) {
        return em.createQuery("select h from Hashtag h where h.tag = :tag", Hashtag.class)
                .setParameter("tag", tag)
                .getResultStream().count() > 0;
    }


    public List<Hashtag> getHashTagList(Long postId) {
        return em.createQuery("select h from Hashtag h " +
                "inner join post_hashtag ph on ph.tag_id = h.id " +
                "inner join Post p on p.id = ph.post_id " +
                "where p.id = :id", Hashtag.class)
                .setParameter("id", postId)
                .getResultList();
    }

}
