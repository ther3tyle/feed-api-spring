package io.dsub.feedapispring.services;

import io.dsub.feedapispring.api.v1.model.FeedLikeDto;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedLike;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.repositories.FeedLikeRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class FeedLikeService {

    private FeedService feedService;
    private FeedLikeRepository feedLikeRepository;

    public FeedLikeService(FeedService feedService, FeedLikeRepository feedLikeRepository) {
        this.feedService = feedService;
        this.feedLikeRepository = feedLikeRepository;
    }

    public boolean toggleFeedLike(FeedLikeDto dto) throws FeedNotFoundException {
        Feed feed = feedService.get(dto.getFeedId());

        FeedLike feedLike = this.get(feed, dto.getUserId());
        if (feedLike == null) {
            feedLike = new FeedLike(feed, dto.getUserId());
            this.save(feedLike);
            return true;
        } else {
            this.delete(feedLike);
            return false;
        }
    }

    @SneakyThrows
    public void delete(FeedLike feedLike) {
        this.feedService.deductLikeCount(feedLike.getFeed().getId());
        this.feedLikeRepository.delete(feedLike);
    }

    @SneakyThrows
    public FeedLike save(FeedLike feedLike) {
        this.feedService.addLikeCount(feedLike.getFeed().getId());
        return this.feedLikeRepository.save(feedLike);
    }

    public FeedLike get(Feed feed, Long userId) {
        Optional<FeedLike> optional = this.feedLikeRepository.findByFeedAndUserId(feed, userId);
        if (optional.isEmpty()) {
            return null;
        } else {
            return optional.get();
        }
    }

    public void deleteByFeedAndUserId(Feed feed, Long userId) {
        this.feedLikeRepository.deleteByFeedAndUserId(feed, userId);
    }

    public Boolean doExist(Feed feed, Long userId) {
        return this.feedLikeRepository.existsByFeedAndUserId(feed, userId);
    }
}
