package io.dsub.feedapispring.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class TrackedDomain {

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "created_date",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdDate;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "last_modified",
            nullable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime lastModified;

    @PrePersist
    protected void persist() {
        if (this.createdDate == null) {
            this.createdDate = OffsetDateTime.now();
            this.lastModified = OffsetDateTime.now();
        }
    }

    @PreUpdate
    protected void update() {
        this.lastModified = OffsetDateTime.now();
    }

    @Override
    public String toString() {
        return  "createdDate=" + dateTimeString(this.createdDate) +
                ", lastModified=" + dateTimeString(this.lastModified);
    }

    /**
     * @param offsetDateTime target offsetDateTime instance
     * @return string trimmed of UTC and millisecond information
     */
    private String dateTimeString(OffsetDateTime offsetDateTime) {
        return DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").format(offsetDateTime);
    }

    public String getFormattedCreateDate() {
        if (this.createdDate != null) {
            return dateTimeString(this.createdDate);
        }
        return "empty";
    }

    public String getFormattedlastModified() {
        if (this.createdDate != null) {
            return dateTimeString(this.createdDate);
        }
        return "empty";
    }
}
