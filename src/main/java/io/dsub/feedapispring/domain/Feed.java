package io.dsub.feedapispring.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "feeds")
@AllArgsConstructor
@NoArgsConstructor
public class Feed extends TrackedDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @NotNull
    @Column(name = "md_name", columnDefinition = "VARCHAR")
    private String mdName;

    @NotNull
    @Column(name = "md_image", columnDefinition = "TEXT")
    private String mdImage;

    @Column(name = "comment_count")
    @EqualsAndHashCode.Exclude
    @With
    private Integer commentCount = 0;

    @Column(name = "like_count")
    @EqualsAndHashCode.Exclude
    @With
    private Integer likeCount = 0;

    @Column(name = "share_count")
    @EqualsAndHashCode.Exclude
    @With
    private Integer shareCount = 0;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "feed")
    @EqualsAndHashCode.Exclude
    private List<FeedComment> feedComments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "feed")
    @EqualsAndHashCode.Exclude
    private List<FeedLike> feedLikes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "feed")
    @EqualsAndHashCode.Exclude
    private List<FeedShare> feedShares = new ArrayList<>();

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", mdName='" + mdName + '\'' +
                ", mdImage='" + mdImage + '\'' +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", shareCount=" + shareCount +
                '}';
    }

    public void addCommentCount() {
        this.commentCount++;
    }

    public void addShareCount() {
        this.shareCount++;
    }

    public void addLikeCount() {
        this.likeCount++;
    }

    public void deductCommentCount() {
        this.commentCount--;
    }

    public void reduceShareCount() {
        this.shareCount--;
    }

    public void deductLikeCount() {
        this.likeCount--;
    }
}
