package io.dsub.feedapispring.api.v1.response;

import io.dsub.feedapispring.api.v1.model.FeedLikeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedLikeResponse extends BaseResponse<FeedLikeDto> {
    public FeedLikeResponse(FeedLikeDto dto, String error) {
        super.setData(dto);
        super.setError(error);
    }
}
