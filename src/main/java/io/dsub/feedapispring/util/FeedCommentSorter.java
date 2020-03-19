package io.dsub.feedapispring.util;

import io.dsub.feedapispring.domain.FeedComment;

import java.util.Comparator;

public class FeedCommentSorter implements Comparator<FeedComment> {
    @Override
    public int compare(FeedComment o1, FeedComment o2) {
        return o1.getCreatedDate().compareTo(o2.getCreatedDate());
    }
}
