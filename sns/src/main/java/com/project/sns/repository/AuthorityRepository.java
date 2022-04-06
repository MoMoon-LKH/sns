package com.project.sns.repository;

import com.project.sns.domain.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorityRepository {

    private final EntityManager em;

    public Authority getUserAuthority() {
        return em.find(Authority.class, 2);
    }
}
