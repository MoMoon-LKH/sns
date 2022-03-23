package com.project.sns.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Date create_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Comment(String content, User user, Post post) {
        this.content = content;
        this.create_date = new Date();
        this.user = user;
        this.post = post;
    }

    public static Comment createComment(String content, User user, Post post) {
        return new Comment(content, user, post);
    }
}
