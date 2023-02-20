package edu.neuroginarium.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserEmailRatingDto {
    private String email;
    private long rating;
}
