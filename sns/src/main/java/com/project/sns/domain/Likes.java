package com.project.sns.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Long user_id;

    private Likes(Post post, Long user_id) {
        this.post = post;
        this.user_id = user_id;
    }

    public static Likes createLikes(Post post, Long user_id) {
        return new Likes(post, user_id);
    }
}
