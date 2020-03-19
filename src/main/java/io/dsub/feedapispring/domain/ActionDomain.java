package io.dsub.feedapispring.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class ActionDomain {
    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "created_date",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdDate;

    @PrePersist
    protected void persist() {
        if (this.createdDate == null) {
            this.createdDate = OffsetDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "createdDate=" + dateTimeString(this.createdDate);
    }

    private String dateTimeString(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return "empty";
        }
        return DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").format(offsetDateTime);
    }
}
