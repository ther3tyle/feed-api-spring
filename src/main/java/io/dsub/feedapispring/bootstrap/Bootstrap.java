package io.dsub.feedapispring.bootstrap;

import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedComment;
import io.dsub.feedapispring.domain.FeedLike;
import io.dsub.feedapispring.domain.FeedShare;
import io.dsub.feedapispring.exceptions.FeedCommentNotFoundException;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import io.dsub.feedapispring.services.FeedCommentService;
import io.dsub.feedapispring.services.FeedLikeService;
import io.dsub.feedapispring.services.FeedService;
import io.dsub.feedapispring.services.FeedShareService;
import net.bytebuddy.utility.RandomString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Bootstrap implements CommandLineRunner {

    private FeedService feedService;
    private FeedCommentService feedCommentService;
    private FeedLikeService feedLikeService;
    private FeedShareService feedShareService;

    public Bootstrap(FeedService feedService, FeedCommentService feedCommentService, FeedLikeService feedLikeService, FeedShareService feedShareService) {
        this.feedService = feedService;
        this.feedCommentService = feedCommentService;
        this.feedLikeService = feedLikeService;
        this.feedShareService = feedShareService;
    }

    @Override
    public void run(String... args) throws FeedNotFoundException {

        for (int i = 0; i < 10; i++) {
            makeDummyFeed();
        }

        for (int i = 0; i < 100; i++) {
            addFeedComment();
            addFeedLike();
            addFeedShare();
        }
    }

    private Feed makeDummyFeed() {
        Feed feed = new Feed();
        feed.setText(RandomString.make(10));
        feed.setMdImage("https://" + RandomString.make(12) + ".com/" + RandomString.make(32));
        feed.setMdName(RandomString.make(5) + " " + RandomString.make(5));
        return feedService.save(feed);
    }

    private void addFeedShare() {
        Feed feed = getOrCreateFeed(getRandomLong(20));
        FeedShare feedShare = new FeedShare();
        feedShare.setFeed(feed);
        feedShare.setUserId(getRandomLong(100));
        feedShareService.save(feedShare);
    }

    private void addFeedLike() {
        Feed feed = getOrCreateFeed(getRandomLong(20));
        FeedLike feedLike = new FeedLike();
        feedLike.setFeed(feed);
        feedLike.setUserId(getRandomLong(100));
        feedLikeService.save(feedLike);
    }

    private void addFeedComment() {
        Feed feed = getOrCreateFeed(getRandomLong(20));

        FeedComment feedComment = new FeedComment();
        feedComment.setFeed(feed);
        feedComment.setText(RandomString.make(10));
        feedComment.setUserId(getRandomLong(100));
        Long count = feedCommentService.count();
        if (new Random().nextBoolean() && count > 0) {
            Long rand = getRandomLong(5);
            FeedComment parent;
            try {
                parent = feedCommentService.get(rand);
                if (!parent.getFeed().getId().equals(feed.getId())) {
                    return;
                }
            } catch (FeedCommentNotFoundException e) {
                parent = new FeedComment();
                parent.setText(RandomString.make(10));
                parent.setFeed(feed);
                parent.setUserId(getRandomLong(100));
                parent = feedCommentService.save(parent);
            }
            feedComment.setParentComment(parent);
        }
        feedCommentService.save(feedComment);
    }

    private int getRandomInt(int bound) {
        return (new Random().nextInt(bound) % 10) + 1;
    }

    private long getRandomLong(int bound) {
        return getRandomInt(bound);
    }

    private Feed getOrCreateFeed(Long id) {
        try {
            return feedService.get(id);
        } catch (FeedNotFoundException e) {
            Feed feed = makeDummyFeed();
            feed.setId(id);
            return feed;
        }
    }
}
