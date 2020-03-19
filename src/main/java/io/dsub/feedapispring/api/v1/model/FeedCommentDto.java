package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FeedCommentDto {
    Long id;
    Long userId;
    String text;
    String createdDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<FeedCommentDto> child = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("child_id")
    List<Long> childIdList;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Long parentId;
}
