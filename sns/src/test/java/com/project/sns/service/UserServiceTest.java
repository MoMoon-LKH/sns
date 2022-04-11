package com.project.sns.service;

import com.project.sns.domain.Gender;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.UserDto;
import com.project.sns.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    @Transactional
    @Rollback
    public void signUp() {
        //given
        UserDto userDto = UserDto.builder()
                .email("jointest")
                .pw("jointest")
                .nickname("jointest")
                .gender(Gender.FEMALE)
                .build();
        //when
        Long saveId = userService.signUp(userDto);
        User getUser = getUserForEmail(userDto.getEmail());

        //then
        assertThat(saveId).isEqualTo(getUser.getId());


    }

    private User getUserForEmail(String email) {
        return userRepository.findOneToEmail(email).get();
    }

    @Test
    public void passwordEncode() {
        System.out.println(passwordEncoder.encode("test2"));
    }
}