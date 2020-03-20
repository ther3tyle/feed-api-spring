package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class FeedLikeDto {
    @NotBlank(message = "feedId must not be blank")
    private Long feedId;
    @NotBlank(message = "userId must not be blank")
    private Long userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isLiked;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isDeleted;

    public FeedLikeDto(Long feedId, Long userId) {
        this.feedId = feedId;
        this.userId = userId;
    }
}
