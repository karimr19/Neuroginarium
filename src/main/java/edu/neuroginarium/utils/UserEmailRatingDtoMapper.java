package edu.neuroginarium.utils;

import edu.neuroginarium.dto.UserEmailRatingDto;
import edu.neuroginarium.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserEmailRatingDtoMapper {
    public UserEmailRatingDto map(User user) {
        return new UserEmailRatingDto()
                    .setEmail(user.getEmail())
                    .setRating(user.getRating());
    }
}
