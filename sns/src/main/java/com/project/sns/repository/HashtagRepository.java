package com.project.sns.repository;

import com.project.sns.domain.Hashtag;
import com.project.sns.domain.Post;
import com.project.sns.domain.Post_Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashtagRepository {

    private final EntityManager em;

    public Long save(Post_Hashtag post_hashtag) {
        em.persist(post_hashtag);
        return post_hashtag.getId();
    }

    public Long saveNew(Hashtag hashtag, Post post) {
        Post_Hashtag post_hashtag = new Post_Hashtag(post, hashtag);

        em.persist(hashtag);
        em.persist(post_hashtag);

        return hashtag.getId();
    }

    public boolean find(String tag) {
        return em.createQuery("select h from Hashtag h where h.tag = :tag", Hashtag.class)
                .setParameter("tag", tag)
                .getResultStream().count() > 0;
    }


    public List<Hashtag> getHashTagList(Long postId) {
        return em.createQuery("select h from Hashtag h " +
                "inner join Post_Hashtag ph on ph.hashtag.id = h.id " +
                "inner join Post p on p.id = ph.post.id " +
                "where p.id = :id", Hashtag.class)
                .setParameter("id", postId)
                .getResultList();
    }

}
