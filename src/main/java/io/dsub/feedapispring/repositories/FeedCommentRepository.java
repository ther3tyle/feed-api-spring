package io.dsub.feedapispring.repositories;

import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    List<FeedComment> findAllByFeed(Feed feed);
    List<FeedComment> findAllByFeedAndUserId(Feed feed, Long userId);
    List<FeedComment> findAllByParentComment(FeedComment parentComment);
    List<FeedComment> findAllByUserId(Long userId);
    Page<FeedComment> findAllByFeed(Feed feed, Pageable pageable);
}
