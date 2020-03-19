package io.dsub.feedapispring.api.v1.response;

import io.dsub.feedapispring.api.v1.model.FeedCommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class FeedCommentDtoPagedResponse extends BaseResponse<Page<FeedCommentDto>> {
    public FeedCommentDtoPagedResponse(Page<FeedCommentDto> data, String error) {
        super.setData(data);
        super.setError(error);
    }
}

