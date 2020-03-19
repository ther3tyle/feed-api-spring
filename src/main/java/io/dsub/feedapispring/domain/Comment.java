package io.dsub.feedapispring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class Comment extends TrackedDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Override
    public String toString() {
        String[] arr =  makeString();
        return "id=" + arr[0] +
                ", userId=" + arr[1] +
                ", text='" + arr[2] + '\'' +
                super.toString();
    }

    private String[] makeString() {
        String[] arr = new String[3];
        arr[0] = id == null ? "empty" : id.toString();
        arr[1] = userId == null ? "empty" : userId.toString();
        arr[2] = text == null ? "" : text;
        return arr;
    }
}
