package io.dsub.feedapispring.repositories;

import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedShareRepository extends JpaRepository<FeedShare, Long> {
    List<FeedShare> findAllByFeed(Feed feed);
    Optional<FeedShare> findByFeedAndUserId(Feed feed, Long userId);
}
