package io.dsub.feedapispring.api.v1.response;

import io.dsub.feedapispring.api.v1.model.FeedDetailsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedDetailsResponse extends BaseResponse<FeedDetailsDto> {
    public FeedDetailsResponse(FeedDetailsDto data, String error) {
        super.setData(data);
        super.setError(error);
    }
}
