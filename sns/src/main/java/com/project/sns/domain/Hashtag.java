package com.project.sns.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30)
    private String tag;

    @OneToMany(mappedBy = "hashtag")
    private List<Post_Hashtag> post_hashtag;

    private Hashtag(String tag) {
        this.tag = tag;
    }

    public static Hashtag createHashTag(String tag) {
        return new Hashtag(tag);
    }

}
