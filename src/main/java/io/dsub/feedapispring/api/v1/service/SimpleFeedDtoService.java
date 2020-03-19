package io.dsub.feedapispring.api.v1.service;

import io.dsub.feedapispring.api.v1.model.SimpleFeedDto;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.services.FeedService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SimpleFeedDtoService {
    private FeedService feedService;
    private ModelMapper mapper;

    public SimpleFeedDtoService(FeedService feedService, ModelMapper mapper) {
        this.feedService = feedService;
        this.mapper = mapper;
    }

    public Page<SimpleFeedDto> getPagedList(Pageable pageable) {
        Page<Feed> pagedFeed = this.feedService.getAll(pageable);
        return pagedFeed.map(feed -> mapper.map(feed, SimpleFeedDto.class));
    }
}
