package io.dsub.feedapispring.exceptions;

public class FeedCommentNotFoundException extends CustomNotFoundException {

    public FeedCommentNotFoundException(Long id) {
        super(id, "feed comment");
    }

    public FeedCommentNotFoundException(Long id, Exception e) {
        super(id, "feed comment", e);
    }
}
