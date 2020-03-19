package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class FeedShareDto {
    private Long feedId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isShared;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer shareCount;
}
