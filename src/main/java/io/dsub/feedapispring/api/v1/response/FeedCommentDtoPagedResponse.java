package io.dsub.feedapispring.api.v1.response;

import io.dsub.feedapispring.api.v1.model.BaseFeedCommentDto;
import io.dsub.feedapispring.api.v1.model.FeedCommentDto;
import io.dsub.feedapispring.api.v1.model.FlatFeedCommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class FeedCommentDtoPagedResponse extends BaseResponse<Page<BaseFeedCommentDto>> {
    public FeedCommentDtoPagedResponse(Page<BaseFeedCommentDto> data, String error) {
        super.setData(data);
        super.setError(error);
    }
}

