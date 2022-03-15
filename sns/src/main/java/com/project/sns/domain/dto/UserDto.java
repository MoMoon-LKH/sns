package com.project.sns.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sns.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDto {

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pw;

    private String nickname;

    private Gender gender;

}
