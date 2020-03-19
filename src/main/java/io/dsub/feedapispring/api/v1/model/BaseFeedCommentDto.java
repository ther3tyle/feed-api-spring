package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseFeedCommentDto {
    @JsonProperty("id")
    Long id;
    @JsonProperty("user_id")
    Long userId;
    @JsonProperty("text")
    String text;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("created_date")
    String createdDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("parent_id")
    Long parentId;
}
