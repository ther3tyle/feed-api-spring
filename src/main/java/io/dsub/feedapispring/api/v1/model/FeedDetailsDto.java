package io.dsub.feedapispring.api.v1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FeedDetailsDto {
    private Long id;
    private String text;
    private Integer commentCount;
    private Integer likeCount;
    private Integer shareCount;
    private String mdName;
    private String mdImage;
    private String createdDate;
    private String lastModified;
    private List<FeedCommentDto> comments = new ArrayList<>();
}
