package com.project.sns.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sns.domain.Gender;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    @NotNull
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pw;

    private String nickname;

    private Gender gender;

}
