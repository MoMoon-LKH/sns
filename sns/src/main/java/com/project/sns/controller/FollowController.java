package com.project.sns.controller;

import com.project.sns.domain.Follow;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.FollowDto;
import com.project.sns.service.FollowService;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;


    @PostMapping("/following")
    public ResponseEntity<?> following(@Valid @RequestBody FollowDto followDto) {
        User follower = userService.findOneWithId(followDto.getFollowerId());
        User followee = userService.findOneWithId(followDto.getFolloweeId());

        Follow follow = Follow.createFollow(follower, followee);

        return ResponseEntity.ok(followService.save(follow));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@Valid @RequestBody FollowDto followDto) {
        Follow follow = followService.getFollow(followDto.getFollowerId(), followDto.getFolloweeId());
        return ResponseEntity.ok(followService.delete(follow));
    }


    @GetMapping("/follower/{followeeId}")
    public ResponseEntity<?> getFollowers(@PathVariable("followeeId") Long followeeId) {
        return ResponseEntity.ok(followService.userFollows(followeeId));
    } // 해당 유저를 팔로우한 사람들 리스트


    @GetMapping("/followee/{followId}")
    public ResponseEntity<?> getFollowee(@PathVariable("followId") Long followId) {
        return ResponseEntity.ok(followService.userFollowee(followId));
    } // 해당 유저가 팔로우한 사람들 리스트


    @GetMapping("/{userId}")
    public ResponseEntity<?> getCount(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(followService.followCount(userId));
    }
}
