package io.dsub.feedapispring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class FeedLike extends Like {
    @NotNull
    @ManyToOne(optional = false)
    private Feed feed;

    public FeedLike(Feed feed, Long userId) {
        this.feed = feed;
        this.setUserId(userId);
    }

    @Override
    public String toString() {
        return "FeedLike{" +
                "feed= " + this.feed.getId() +
                ", " + super.toString() +
                '}';
    }

}
