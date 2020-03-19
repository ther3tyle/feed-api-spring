package io.dsub.feedapispring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class Share extends ActionDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "userId")
    private Long userId;

    @Column(name = "count")
    private Integer shareCount = 0;

    public void increment() {
        this.shareCount++;
    }

    public void decrement() {
        if (this.shareCount > 0) {
            this.shareCount--;
        }
    }
}
