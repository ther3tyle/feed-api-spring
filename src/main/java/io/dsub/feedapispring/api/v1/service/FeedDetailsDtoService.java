package io.dsub.feedapispring.api.v1.service;

import io.dsub.feedapispring.api.v1.model.FeedCommentDto;
import io.dsub.feedapispring.api.v1.model.FeedDetailsDto;
import io.dsub.feedapispring.api.v1.model.NestedFeedCommentDto;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedComment;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.services.FeedCommentService;
import io.dsub.feedapispring.services.FeedService;
import io.dsub.feedapispring.util.FeedCommentSorter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedDetailsDtoService {
    private FeedService feedService;
    private FeedCommentService feedCommentService;
    private ModelMapper mapper;

    public FeedDetailsDtoService(FeedService feedService, FeedCommentService feedCommentService, ModelMapper mapper) {
        this.feedService = feedService;
        this.feedCommentService = feedCommentService;
        this.mapper = mapper;
    }

    public FeedDetailsDto getFeedDetailsDto(Long id) throws FeedNotFoundException {
        Feed feed = feedService.get(id);
        FeedDetailsDto dto = mapper.map(feed, FeedDetailsDto.class);
        List<NestedFeedCommentDto> commentList = getCommentList(this.feedCommentService.getAllByFeed(feed));
        dto.setComments(commentList);
        return dto;
    }

    private List<NestedFeedCommentDto> getCommentList(List<FeedComment> source) {
        List<NestedFeedCommentDto> target = new ArrayList<>();
        List<FeedComment> topLevelList = new ArrayList<>();

        for (FeedComment feedComment : source) {
            if (feedComment.getParentComment() == null) {
                topLevelList.add(feedComment);
            }
        }

        topLevelList.sort(new FeedCommentSorter());

        for (FeedComment feedComment : topLevelList) {
            target.add(mapper.map(feedComment, NestedFeedCommentDto.class));
        }

        return target;
    }
}
