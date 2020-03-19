package io.dsub.feedapispring.api.v1.response;

import io.dsub.feedapispring.api.v1.model.FeedCommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedCommentResponse extends BaseResponse<FeedCommentDto> {
    public FeedCommentResponse(FeedCommentDto data, String error) {
        super.setData(data);
        super.setError(error);
    }
}
