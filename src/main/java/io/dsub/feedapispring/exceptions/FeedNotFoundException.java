package io.dsub.feedapispring.exceptions;

public class FeedNotFoundException extends CustomNotFoundException {

    public FeedNotFoundException(Long id) {
        super(id, "feed");
    }

    public FeedNotFoundException(Long id, Exception e) {
        super(id, "feed", e);
    }
}
