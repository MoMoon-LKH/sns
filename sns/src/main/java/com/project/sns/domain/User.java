package com.project.sns.domain;

import org.hibernate.boot.model.source.spi.FetchCharacteristics;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(length = 50)
    private String pw;

    @Column(length = 50)
    private String nickname;

    private Gender gender;

    private Date creat_date;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")}
    )
    private Set<Authority> authorities;

}
