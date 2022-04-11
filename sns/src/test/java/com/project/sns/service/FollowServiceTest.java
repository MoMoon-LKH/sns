package com.project.sns.service;

import com.project.sns.domain.Follow;
import com.project.sns.domain.Gender;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;


    @Test
    @Transactional
    @Rollback
    public void saveTest() {

        //given
        HashMap<String, User> users = getUsers();
        User followee = users.get("followTest1");
        System.out.println("followee.getId() = " + followee.getId());
        users.remove("followTest1");

        //when
        for (String user : users.keySet()) {
            Long save = followService.save(Follow.createFollow(users.get(user), followee));
        } // 팔로우 추가


        //then
        assertThat(followService.followCount(followee.getId())).isEqualTo(4);

    }


    @Test
    @Transactional
    @Rollback
    public void getFollowList() {

        //given
        HashMap<String, User> users = getUsers();
        int size = users.size() - 1;

        User follow = users.get("followTest1");
        System.out.println("follow.getId() = " + follow.getId());
        users.remove("followTest1");

        //when
        for (String user : users.keySet()) {
            followService.save(Follow.createFollow(follow, users.get(user)));
        }

        //then
        assertThat(followService.followCount(follow.getId())).isEqualTo(0);
        assertThat(followService.userFollows(follow.getId()).size()).isEqualTo(size);

    }


    @Test
    @Transactional
    @Rollback
    public void getFolloweeList() {

        //given
        HashMap<String, User> users = getUsers();
        User followee = users.get("followTest1");
        users.remove("followTest1");

        int size = users.size();

        //when
        for (String user : users.keySet()) {
            followService.save(Follow.createFollow(users.get(user), followee));
        }

        //then
        List<Follow> followees = followService.userFollowee(followee.getId());
        assertThat(followService.followCount(followee.getId())).isEqualTo(size);
        assertThat(followees.size()).isEqualTo(size);

        for (Follow fw : followees) {
            assertThat(fw.getFollowee().getId()).isEqualTo(followee.getId());
        }

    }


    public HashMap<String, User> getUsers() {
        HashMap<String, User> users = new HashMap<>();

        for (int i = 1; i < 6; i++) {
            String email = "followTest";

            UserDto userDto = UserDto.builder()
                    .email(email + i)
                    .pw("123")
                    .nickname(email + i)
                    .gender(Gender.MALE)
                    .build();

            System.out.println("userDto = " + userDto.getEmail());

            Long userId = userService.signUp(userDto);

            users.put(email + i, userService.findOneWithId(userId));
        }

        return users;
    }


}