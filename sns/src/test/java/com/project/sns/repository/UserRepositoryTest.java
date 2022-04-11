package com.project.sns.repository;

import com.project.sns.domain.Authority;
import com.project.sns.domain.Gender;
import com.project.sns.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    @Rollback
    public void join() {
        //given
        Authority authority = Authority.createAuthority("USER");
        User user = User.createUser("joinTest", "test", "test", Gender.MALE, authority);


        //when
        if (!userRepository.duplicateUser(user.getEmail())){
            System.out.println("duplicate");
        }

        Long saveId = userRepository.save(user);
        User findUser = em.find(User.class, saveId);


        //then
        Assertions.assertThat(saveId).isEqualTo(findUser.getId());

    }

}