package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public abstract class BaseFeedCommentDto {
    @JsonProperty("id")
    Long id;
    @Min(value = 1, message = "userId must be over 1")
    @NotBlank(message = "user_id must not be blank")
    @JsonProperty("user_id")
    Long userId;
    @Size(min = 5, max = 2000, message = "text size must be over 5, less than 2000")
    @NotBlank(message = "text must not be blank")
    @JsonProperty("text")
    String text;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("created_date")
    String createdDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("parent_id")
    Long parentId;
}
