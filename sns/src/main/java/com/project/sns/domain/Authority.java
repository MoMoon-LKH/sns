package com.project.sns.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Authority {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 30)
    private String authority_name;


    private Authority(String authority_name){
        this.authority_name = authority_name;
    }

    public Authority createAuthority(String authority_name){
        return new Authority(authority_name);
    }

}
