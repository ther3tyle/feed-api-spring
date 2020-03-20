package io.dsub.feedapispring.api.v1.service;

import io.dsub.feedapispring.api.v1.model.*;
import io.dsub.feedapispring.api.v1.response.FeedCommentDtoPagedResponse;
import io.dsub.feedapispring.api.v1.response.FeedCommentResponse;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedComment;
import io.dsub.feedapispring.exceptions.FeedCommentNotFoundException;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.services.FeedCommentService;
import io.dsub.feedapispring.services.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FeedCommentDtoService {
    private FeedCommentService feedCommentService;
    private FeedService feedService;
    private ModelMapper mapper;

    public FeedCommentDtoService(FeedCommentService feedCommentService,
                                 FeedService feedService,
                                 ModelMapper mapper) {
        this.feedCommentService = feedCommentService;
        this.feedService = feedService;
        this.mapper = mapper;
    }

    public FeedCommentDtoPagedResponse getFeedComments(Long feedId, Pageable pageable, boolean isFlat) {
        Feed feed;

        try {
            feed = feedService.get(feedId);
        } catch (FeedNotFoundException e) {
            return new FeedCommentDtoPagedResponse(null, e.getMessage());
        }

        Page<FeedComment> pagedResult = feedCommentService.getAllByFeedAndPageable(feed, pageable);
        Page<BaseFeedCommentDto> pagedDto;

        if (isFlat) {
            pagedDto = pagedResult.map(e -> mapper.map(e, FlatFeedCommentDto.class));
        } else {
            pagedDto = pagedResult.map(e -> mapper.map(e, NestedFeedCommentDto.class));
        }

        log.info("pagedDto: {}", pagedDto);
        return new FeedCommentDtoPagedResponse(pagedDto, null);
    }

    public FeedCommentResponse addFeedComments(Long feedId, FeedCommentDto dto) {
        FeedComment comment;

        if (dto.getUserId() == null) {
            return new FeedCommentResponse(null, "user_id must not be null");
        }  else if (dto.getUserId() <= 0) {
            return new FeedCommentResponse(null, "user_id must be greater than 0");
        }
        if (dto.getText().isBlank()) {
            return new FeedCommentResponse(null, "text must not be blank");
        } else if (dto.getText().trim().length() <= 5 || dto.getText().trim().length() > 2000) {
            return new FeedCommentResponse(null, "text size must be between 5 and 2000 char");
        }
        if (dto.getParentId() != null) {
            try {
                FeedComment parent = feedCommentService.get(dto.getParentId());
                if (!parent.getFeed().getId().equals(feedId)) {
                    return new FeedCommentResponse(null, "feed_id mismatch - parent and child points different feed");
                } else {
                    comment = mapper.map(dto, FeedComment.class);
                    comment.setFeed(parent.getFeed());
                    comment.setParentComment(parent);
                    comment = feedCommentService.save(comment);
                    return new FeedCommentResponse(mapper.map(comment, FeedCommentDto.class), null);
                }
            } catch (FeedCommentNotFoundException e) {
                return new FeedCommentResponse(null, e.getMessage());
            }
        } else {
            try {
                Feed feed = feedService.get(feedId);
                comment = mapper.map(dto, FeedComment.class);
                comment.setFeed(feed);
                comment = feedCommentService.save(comment);
                return new FeedCommentResponse(mapper.map(comment, FeedCommentDto.class), null);
            } catch (FeedNotFoundException e) {
                return new FeedCommentResponse(null, e.getMessage());
            }
        }
    }

    public FeedCommentResponse updateFeedComment(Long feedId, SimpleFeedCommentDto requestDto) {
        try {
            Feed feed = feedService.get(feedId);
        } catch (FeedNotFoundException e) {
            return new FeedCommentResponse(null, e.getMessage());
        }
        if (requestDto.getId() <= 0) {
            return new FeedCommentResponse(null, "feed_id must be greater than 0");
        }
        if (requestDto.getText().length() <= 0 || requestDto.getText().length() > 2000) {
            return new FeedCommentResponse(null, "text size must be between 5 and 2000 char");
        }
        try {
            FeedComment feedComment = feedCommentService.get(requestDto.getId());
            feedComment.setText(requestDto.getText());
            feedComment = feedCommentService.save(feedComment);
            FeedCommentDto resultDto = new FeedCommentDto();
            resultDto.setId(feedComment.getId());
            return new FeedCommentResponse(resultDto, null);
        } catch (FeedCommentNotFoundException e) {
            return new FeedCommentResponse(null, e.getMessage());
        }
    }

    public String deleteFeedComment(Long feedId, Long commentId) {
        try {
            this.feedCommentService.deleteById(commentId);
            return "Successfully deleted comment: " + commentId;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public FeedCommentResponse findById(Long commentId) {
        try {
            FeedComment feedComment = this.feedCommentService.get(commentId);
            FeedCommentDto dto = mapper.map(feedComment, FeedCommentDto.class);
            return new FeedCommentResponse(dto, null);
        } catch (FeedCommentNotFoundException e) {
            return new FeedCommentResponse(null, e.getMessage());
        }
    }
}
