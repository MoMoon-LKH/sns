package com.project.sns.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {

    @NotNull
    private Long post_id;

    private Long user_id;

    private int like_count;

    private boolean activated;
}
