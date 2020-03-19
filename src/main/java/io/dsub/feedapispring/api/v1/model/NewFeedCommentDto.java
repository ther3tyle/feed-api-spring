package io.dsub.feedapispring.api.v1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewFeedCommentDto {
    @NotBlank(message = "must contain userId")
    private Long userId = 0L;
    @NotBlank(message = "must contain feedId")
    private Long feedId = 0L;
    private Long parentCommentId = 0L;
    private String text = "";
}
