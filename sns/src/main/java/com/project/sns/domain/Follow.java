package com.project.sns.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    private User follower; // 팔로우 하는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee")
    private User followee; // 팔로우 대상

    private Follow(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public static Follow createFollow(User follower, User followee) {
        return new Follow(follower, followee);
    }
}
