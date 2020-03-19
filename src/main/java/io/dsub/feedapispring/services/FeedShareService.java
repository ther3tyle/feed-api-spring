package io.dsub.feedapispring.services;

import io.dsub.feedapispring.api.v1.model.FeedShareDto;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedShare;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.repositories.FeedShareRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class FeedShareService {

    FeedShareRepository feedShareRepository;
    FeedService feedService;
    ModelMapper mapper;

    public FeedShareService(FeedShareRepository feedShareRepository, FeedService feedService, ModelMapper mapper) {
        this.feedShareRepository = feedShareRepository;
        this.feedService = feedService;
        this.mapper = mapper;
    }

    public Boolean addFeedShare(FeedShareDto dto) throws FeedNotFoundException {
        Feed feed = feedService.get(dto.getFeedId());

        Optional<FeedShare> optShare =
                feedShareRepository.findByFeedAndUserId(feed, dto.getUserId());

        FeedShare feedShare;

        feedShare = optShare.orElseGet(() -> new FeedShare(feed, dto.getUserId()));
        feedShare.increment();
        feedShare.getFeed().addShareCount();
        this.feedShareRepository.save(feedShare);
        return true;
    }

    public FeedShare save(FeedShare feedShare) {
        Optional<FeedShare> optional =
                feedShareRepository.findByFeedAndUserId(feedShare.getFeed(), feedShare.getUserId());
        if (optional.isPresent()) {
            feedShare = optional.get();
        }
        feedShare.increment();
        feedShare.getFeed().addShareCount();
        return feedShareRepository.save(feedShare);
    }

    public FeedShare findByFeedAndUserId(Feed feed, Long userId) {
        Optional<FeedShare> optional =
                feedShareRepository.findByFeedAndUserId(feed, userId);
        if (optional.isEmpty()) {
            FeedShare feedShare = new FeedShare();
            feedShare.setUserId(userId);
            feedShare.setFeed(feed);
            feedShare.setShareCount(0);
            return feedShare;
        } else {
            return optional.get();
        }
    }
}
