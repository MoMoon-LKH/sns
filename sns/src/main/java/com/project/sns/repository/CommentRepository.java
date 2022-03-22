package com.project.sns.repository;

import com.project.sns.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private static EntityManager em;

    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }


    public boolean delete(Comment comment) {
        try {
            em.remove(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Comment> findForPostId(Long postId) {
        return em.createQuery(
                "select c, u.nickname from Comment c join fetch c.user u " +
                        "where c.post.id = :postId order by c.create_date asc", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public Optional<Comment> findOneForId(Long id) {
        return em.createQuery("select c from Comment c where c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList().stream().findAny();
    }


}
