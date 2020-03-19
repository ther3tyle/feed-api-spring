package io.dsub.feedapispring.services;

import io.dsub.feedapispring.api.v1.model.SimpleFeedDto;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.repositories.FeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class FeedService {

    // fields
    private final FeedRepository feedRepository;
    private final ModelMapper modelMapper;

    public FeedService(FeedRepository feedRepository, ModelMapper modelMapper) {
        this.feedRepository = feedRepository;
        this.modelMapper = modelMapper;
    }

    public Feed addFeed(SimpleFeedDto simpleFeedDto) {
        Feed feed = modelMapper.map(simpleFeedDto, Feed.class);
        return this.feedRepository.save(feed);
    }

    public Feed save(Feed feed) {
        return this.feedRepository.save(feed);
    }

    public Feed get(Long id) throws FeedNotFoundException {
        Optional<Feed> optionalFeed = this.feedRepository.findById(id);
        if (optionalFeed.isEmpty()) {
            throw new FeedNotFoundException(id);
        }
        return optionalFeed.get();
    }

    public List<Feed> findAllByMdName(String mdName) {
        return feedRepository.findAllByMdName(mdName);
    }

    public void deleteById(Long id) {
        this.feedRepository.deleteById(id);
    }

    public void deleteAllByMdName(String mdName) {
        this.feedRepository.deleteAllByMdName(mdName);
    }

    public Long count() {
        return this.feedRepository.count();
    }

    public Page<Feed> getAll(Pageable pageable) {
        return this.feedRepository.findAll(pageable);
    }

    public void addShareCount(Long id) throws FeedNotFoundException {
        Feed feed = get(id);
        feed.addShareCount();
        save(feed);
    }

    public void addCommentCount(Long id) throws FeedNotFoundException {
        Feed feed = get(id);
        feed.addCommentCount();
        save(feed);
    }

    public void addLikeCount(Long id) throws FeedNotFoundException {
        Feed feed = get(id);
        feed.addLikeCount();
        save(feed);
    }


    public void deductLikeCount(Long id) throws FeedNotFoundException {
        Feed feed = get(id);
        feed.deductLikeCount();
        save(feed);
    }

    public void deductCommentCount(Long id) throws FeedNotFoundException {
        Feed feed = get(id);
        feed.deductCommentCount();
        save(feed);
    }
}
