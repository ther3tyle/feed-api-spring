package io.dsub.feedapispring.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AddFeedCommentDto {
    @Min(value = 1, message = "userId must be over 1")
    @NotBlank(message = "user_id must not be blank")
    @JsonProperty("user_id")
    private Long userId = 0L;
    @JsonProperty("feed_id")
    private Long feedId = 0L;
    @JsonProperty("parent_id")
    private Long parentId = 0L;
    @Size(min = 5, max = 2000, message = "text size must be over 5, less than 2000")
    @NotBlank(message = "text must not be blank")
    @JsonProperty("text")
    private String text = "";
}
