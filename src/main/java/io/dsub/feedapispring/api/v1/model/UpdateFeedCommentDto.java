package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "user_id", "text", "created_date", "parent_id", "child_id", "feed_id"})
public class UpdateFeedCommentDto extends BaseFeedCommentDto {
    @JsonProperty("feed_id")
    Long feedId;
}
