package edu.neuroginarium.utils;

import edu.neuroginarium.dto.UserDto;
import edu.neuroginarium.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public User map(UserDto userDto) {
        return new User()
                .setEmail(userDto.getEmail())
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setPassword(userDto.getPassword())
                .setRating(0);
    }
}