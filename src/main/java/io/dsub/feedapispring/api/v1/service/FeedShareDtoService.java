package io.dsub.feedapispring.api.v1.service;

import io.dsub.feedapispring.api.v1.model.FeedShareDto;
import io.dsub.feedapispring.api.v1.response.FeedShareResponse;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedShare;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.services.FeedService;
import io.dsub.feedapispring.services.FeedShareService;
import org.springframework.stereotype.Service;

@Service
public class FeedShareDtoService {
    FeedShareService feedShareService;
    FeedService feedService;

    public FeedShareDtoService(FeedShareService feedShareService, FeedService feedService) {
        this.feedShareService = feedShareService;
        this.feedService = feedService;
    }

    public FeedShareResponse feedShare(FeedShareDto feedShareDto) {
        try {
            Feed feed = feedService.get(feedShareDto.getFeedId());
            FeedShare feedShare = new FeedShare();
            feedShare.setFeed(feed);
            feedShare.setUserId(feedShareDto.getUserId());
            feedShareService.save(feedShare);
            feedShareDto.setIsShared(true);
            return new FeedShareResponse(feedShareDto, null);
        } catch (FeedNotFoundException e) {
            return new FeedShareResponse(null, e.getMessage());
        }
    }

    public FeedShareResponse getFeedShares(Long feedId) {
        try {
            Feed feed = feedService.get(feedId);
            FeedShareDto dto = new FeedShareDto();
            dto.setFeedId(feedId);
            dto.setShareCount(feed.getShareCount());
            return new FeedShareResponse(dto, null);
        } catch (FeedNotFoundException e) {
            return new FeedShareResponse(null, e.getMessage());
        }
    }

    public FeedShareResponse getFeedShareDetails(Long feedId, Long userId) {
        try {
            Feed feed = feedService.get(feedId);
            FeedShare feedShare = feedShareService.findByFeedAndUserId(feed, userId);
            FeedShareDto dto = new FeedShareDto();
            dto.setShareCount(feedShare.getShareCount());
            dto.setFeedId(feedShare.getFeed().getId());
            dto.setUserId(feedShare.getUserId());
            return new FeedShareResponse(dto, null);
        } catch (FeedNotFoundException e) {
            return new FeedShareResponse(null, e.getMessage());
        }
    }
}
