package io.dsub.feedapispring.api.v1.response;

import io.dsub.feedapispring.api.v1.model.FeedShareDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedShareResponse extends BaseResponse<FeedShareDto> {
    public FeedShareResponse(FeedShareDto dto, String error) {
        super.setData(dto);
        super.setError(error);
    }
}
