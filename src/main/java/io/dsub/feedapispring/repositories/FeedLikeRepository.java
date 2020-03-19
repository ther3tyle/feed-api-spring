package io.dsub.feedapispring.repositories;

import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    Optional<FeedLike> findByFeedAndUserId(Feed feed, Long userId);
    List<FeedLike> findAllByFeed(Feed feed);
    void deleteByFeedAndUserId(Feed feed, Long userId);
    boolean existsByFeedAndUserId(Feed feed, Long userId);
}
