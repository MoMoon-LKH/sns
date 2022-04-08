package com.project.sns.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String content;
    private Date create_date;
    private Date update_date;

    @OneToMany(mappedBy = "post")
    private List<Likes> likes = new ArrayList<>();


    // 해시태그 추가할 것

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Post_Hashtag> post_hashtags = new ArrayList<>();


    private Post(String content, User user) {
        this.content = content;
        this.user = user;
        this.create_date = new Date();
        this.update_date = new Date();
    }

    public static Post createPost(String content, User user) {
        return new Post(content, user);
    }

    public void update(String content) {
        this.content = content;
        this.update_date = new Date();
    }


}
