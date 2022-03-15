package com.project.sns.repository;

import com.project.sns.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public void delete(User user) {
        em.remove(user);
    }

    @EntityGraph(attributePaths = "authorities")
    public Optional<User> findOneToEmail(String email) {
        List<User> user = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        return user.stream().findAny();
    }

    public boolean duplicateUser(String email) {

        if(findOneToEmail(email).orElse(null) != null){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        return true;

    }

}
