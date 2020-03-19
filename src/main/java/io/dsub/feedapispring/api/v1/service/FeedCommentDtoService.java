package io.dsub.feedapispring.api.v1.service;

import io.dsub.feedapispring.api.v1.model.FeedCommentDto;
import io.dsub.feedapispring.api.v1.response.FeedCommentDtoPagedResponse;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedComment;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.services.FeedCommentService;
import io.dsub.feedapispring.services.FeedService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FeedCommentDtoService {
    private FeedCommentService feedCommentService;
    private FeedService feedService;
    private ModelMapper mapper;
    private TypeMap<FeedComment, FeedCommentDto> flatMap;

    public FeedCommentDtoService(FeedCommentService feedCommentService,
                                 FeedService feedService,
                                 ModelMapper mapper,
                                 TypeMap<FeedComment, FeedCommentDto> flatMap) {
        this.feedCommentService = feedCommentService;
        this.feedService = feedService;
        this.mapper = mapper;
        this.flatMap = flatMap;
    }

    public FeedCommentDtoPagedResponse getFeedComments(Long feedId, Pageable pageable, boolean isFlat) {
        Feed feed;
        try {
            feed = feedService.get(feedId);
        } catch (FeedNotFoundException e) {
            return new FeedCommentDtoPagedResponse(null, e.getMessage());
        }

        Page<FeedComment> pagedResult = feedCommentService.getAllByFeedAndPageable(feed, pageable);
        Page<FeedCommentDto> pagedDto = pagedResult.map(flatMap::map);
        return new FeedCommentDtoPagedResponse(pagedDto, null);
    }
}
