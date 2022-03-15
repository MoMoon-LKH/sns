package com.project.sns.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.boot.model.source.spi.FetchCharacteristics;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(length = 100)
    private String pw;

    @Column(length = 50)
    private String nickname;

    private Gender gender;

    private Date create_date;

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

    private User(String email, String pw, String nickname, Gender gender, Authority authority) {
        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
        this.gender = gender;
        this.create_date = new Date();
        this.authorities = Collections.singleton(authority);
    }

    public static User createUser(String email, String pw, String nickname, Gender gender, Authority authority) {
        return new User(email, pw, nickname, gender, authority);
    }

}
