package io.dsub.feedapispring.api.v1.service;

import io.dsub.feedapispring.api.v1.model.FeedLikeDto;
import io.dsub.feedapispring.api.v1.model.FeedLikeResultDto;
import io.dsub.feedapispring.api.v1.response.FeedLikeResponse;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedLike;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.services.FeedLikeService;
import io.dsub.feedapispring.services.FeedService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FeedLikeResultDtoService {
    private FeedLikeService feedLikeService;
    private FeedService feedService;
    private ModelMapper mapper;

    public FeedLikeResultDtoService(FeedLikeService feedLikeService, FeedService feedService, ModelMapper mapper) {
        this.feedLikeService = feedLikeService;
        this.feedService = feedService;
        this.mapper = mapper;
    }

    public FeedLikeResponse toggleFeedLike(FeedLikeDto requestDto) throws FeedNotFoundException {
        String err;
        FeedLikeResultDto resultDto;

        Boolean result = this.feedLikeService.toggleFeedLike(requestDto);
        resultDto = new FeedLikeResultDto();
        resultDto.setFeedId(requestDto.getFeedId());
        resultDto.setUserId(requestDto.getUserId());
        resultDto.setIsLiked(result);
        return new FeedLikeResponse(resultDto, null);
    }



    public FeedLikeResponse addFeedLike(FeedLikeDto feedLikeDto) {
        try {
            Feed feed = feedService.get(feedLikeDto.getFeedId());
            if (feedLikeService.doExist(feed, feedLikeDto.getUserId())) {
                return new FeedLikeResponse(null, "duplicated entry: " +
                        "[feed: " + feedLikeDto.getFeedId() +
                        ", userId: " + feedLikeDto.getUserId() + "]");
            } else {
                FeedLike feedLike = feedLikeService.save(new FeedLike(feed, feedLikeDto.getUserId()));
                FeedLikeResultDto resultDto = mapper.map(feedLike, FeedLikeResultDto.class);
                resultDto.setIsLiked(true);
                return new FeedLikeResponse(resultDto, null);
            }
        } catch (FeedNotFoundException e) {
            return new FeedLikeResponse(null, e.getMessage());
        }
    }

    public FeedLikeResponse removeFeedLike(FeedLikeDto feedLikeDto) {
        try {
            Feed feed = feedService.get(feedLikeDto.getFeedId());
            if (feedLikeService.doExist(feed, feedLikeDto.getUserId())) {
                feedLikeService.deleteByFeedAndUserId(feed, feedLikeDto.getUserId());
                FeedLikeResultDto resultDto = mapper.map(feedLikeDto, FeedLikeResultDto.class);
                resultDto.setIsDeleted(true);
                return new FeedLikeResponse(resultDto, null);
            } else {
                return new FeedLikeResponse(null, "entry not exist: " +
                        "[feed: " + feedLikeDto.getFeedId() +
                        ", userId: " + feedLikeDto.getUserId() + "]");
            }

        } catch (FeedNotFoundException e) {
            return new FeedLikeResponse(null, e.getMessage());
        }
    }
}
