package com.project.sns.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String content;

    private Date create_date;

    @NotNull
    private Long postId;

    @NotNull
    private Long userId;

    private String user_nickname;
}
