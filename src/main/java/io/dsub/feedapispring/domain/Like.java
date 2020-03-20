package io.dsub.feedapispring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class Like extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "userId")
    private Long userId;

    @Override
    public String toString() {
        return  "id=" + id +
                ", userId=" + userId +
                ", " + super.toString();
    }
}
