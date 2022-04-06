package com.project.sns.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30)
    private String tag;

    private Hashtag(String tag) {
        this.tag = tag;
    }

    public static Hashtag createHashTag(String tag) {
        return new Hashtag(tag);
    }

}
