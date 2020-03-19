package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "user_id", "text", "created_date", "parent_id", "child_id"})
public class FlatFeedCommentDto extends BaseFeedCommentDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("child_id")
    List<Long> childIdList;
}
