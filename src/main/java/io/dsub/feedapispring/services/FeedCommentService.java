package io.dsub.feedapispring.services;

import io.dsub.feedapispring.api.v1.model.NewFeedCommentDto;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedComment;
import io.dsub.feedapispring.exceptions.FeedCommentNotFoundException;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.repositories.FeedCommentRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeedCommentService {

    FeedCommentRepository feedCommentRepository;
    FeedService feedService;

    public FeedCommentService(FeedCommentRepository feedCommentRepository, FeedService feedService) {
        this.feedCommentRepository = feedCommentRepository;
        this.feedService = feedService;
    }

    @SneakyThrows
    public FeedComment save(FeedComment feedComment) {
        this.feedService.addCommentCount(feedComment.getFeed().getId());
        return this.feedCommentRepository.save(feedComment);
    }

    public FeedComment get(Long id) throws FeedCommentNotFoundException {
        Optional<FeedComment> optional = this.feedCommentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new FeedCommentNotFoundException(id);
        }
        return optional.get();
    }

    public FeedComment addFromDto(NewFeedCommentDto dto) throws FeedNotFoundException, FeedCommentNotFoundException {
        Feed feed = this.feedService.get(dto.getFeedId());

        FeedComment feedComment = new FeedComment();

        if (dto.getParentCommentId() > 0L) {
            FeedComment parent = get(dto.getParentCommentId());
            feedComment.setParentComment(parent);
        }

        feedComment.setFeed(feed);
        feedComment.setUserId(dto.getUserId());
        feedComment.setText(dto.getText());

        return this.save(feedComment);
    }

    @SneakyThrows
    public void delete(FeedComment feedComment) {
        this.feedService.deductCommentCount(feedComment.getFeed().getId());
        this.feedCommentRepository.delete(feedComment);
    }

    public Long count() {
        return this.feedCommentRepository.count();
    }

    public Page<FeedComment> getAllByFeedAndPageable(Feed feed, Pageable pageable) {
        return this.feedCommentRepository.findAllByFeed(feed, pageable);
    }

    public List<FeedComment> getAllByFeed(Feed feed) {
        return this.feedCommentRepository.findAllByFeed(feed);
    }
}
