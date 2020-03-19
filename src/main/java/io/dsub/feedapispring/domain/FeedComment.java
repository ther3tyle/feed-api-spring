package io.dsub.feedapispring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class FeedComment extends Comment {
    @NotNull
    @ManyToOne(optional = false)
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    private FeedComment parentComment;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "parentComment", orphanRemoval = true)
    private List<FeedComment> childComments = new ArrayList<>();

    public void setParentComment(FeedComment parentComment) {
        if (this.getId() != null) {
            if (this.getId().equals(parentComment.getId())) {
                return;
            }
        }
        this.parentComment = parentComment;
    }

    public void removeChild(Long childId) {
        this.getChildComments().removeIf(entry -> entry.getId().equals(childId));
    }

    public List<Long> getChildIdList() {
        return this.childComments.stream()
                .map(Comment::getId).collect(Collectors.toList());
    }

    public Long getParentId() {
        if (this.parentComment == null) {
            return null;
        }
        return parentComment.getId();
    }

    @Override
    public String toString() {
        return "FeedComment{" +
                super.toString() +
                ", feedId=" + feed.getId() +
                ", parentCommentId=" + getParentString() +
                ", directChildCommentIds=" + getDirectChildString() +
                ", fullChildCommentIds=" + getFullChildString() +
                '}';
    }

    private String getParentString() {
        if (this.parentComment == null) {
            return "empty";
        } else {
            return this.parentComment.getId().toString();
        }
    }

    private String getDirectChildString() {
        if (this.childComments == null || this.childComments.size() == 0) {
            return "empty";
        } else {
            StringBuilder sb = new StringBuilder("[");
            this.childComments.forEach(e ->  {
                sb.append(e.getId()).append(",");
            });
            return sb.deleteCharAt(sb.length()-1).append("]").toString();
        }
    }

    private String getFullChildString() {
        if (this.childComments == null || this.childComments.size() == 0) {
            return "empty";
        } else {
            StringBuilder sb = new StringBuilder("[");
            this.childComments.forEach(e ->  {
                sb.append(e.getId()).append(",");
                if (e.getChildComments() != null && !e.getChildComments().isEmpty()) {
                    e.getChildComments().forEach(c -> {
                        sb.append(c.getId()).append(",");
                    });
                }
            });
            return sb.deleteCharAt(sb.length()-1).append("]").toString();
        }
    }
}
