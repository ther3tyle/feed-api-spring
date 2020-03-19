package io.dsub.feedapispring.api.v1.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SimpleFeedDto {
    private String text;
    @NotBlank(message = "mdName must not be blank")
    private String mdName;
    @NotBlank(message = "mdImage must not be blank")
    @URL(message = "invalid url detected")
    private String mdImage;
    private String createdDate;
}
