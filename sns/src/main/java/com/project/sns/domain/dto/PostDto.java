package com.project.sns.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class PostDto {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 300)
    private String content;

    private Date create_date;
    private Date update_date;


}
