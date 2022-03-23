package com.project.sns.repository;

import com.project.sns.domain.Comment;
import com.project.sns.domain.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public Optional<Comment> findClassId(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }


    public boolean delete(Long id) {
        try {
            em.remove(em.find(Comment.class, id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<CommentDto> findForPostId(Long postId) {
        return em.createQuery(
                "select " +
                        "new com.project.sns.domain.dto.CommentDto(c.id, c.content, c.create_date, p.id , u.id, u.nickname) " +
                        "from Comment c " +
                        "left join User u on u.id = c.user.id " +
                        "left join Post p on p.id = c.post.id " +
                        "where c.post.id = :postId order by c.create_date asc", CommentDto.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public Optional<CommentDto> findOneForId(Long id) {
        return em.createQuery("select " +
                "new com.project.sns.domain.dto.CommentDto(c.id, c.content, c.create_date, p.id , u.id, u.nickname) " +
                "from Comment c " +
                "left join User u on u.id = c.user.id " +
                "left join Post p on p.id = c.post.id " +
                "where c.id = :id", CommentDto.class)
                .setParameter("id", id)
                .getResultList().stream().findAny();
    }


}
