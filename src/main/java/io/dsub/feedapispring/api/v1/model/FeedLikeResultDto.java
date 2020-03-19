package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedLikeResultDto {
    private Long feedId;
    private Long userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isLiked;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isDeleted;
}
