package edu.neuroginarium.service;

import edu.neuroginarium.dto.UserEmailRatingDto;
import edu.neuroginarium.repository.exception.NotFoundException;
import edu.neuroginarium.model.User;
import edu.neuroginarium.repository.UserRepository;
import edu.neuroginarium.utils.UserEmailRatingDtoMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RatingService {
    private final UserRepository userRepository;

    private final UserEmailRatingDtoMapper userEmailRatingDtoMapper;

    public Long getRatingById(Long id) {
        var optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(User.class, id);
        }
        return optionalUser.get().getRating();
    }

    public List<UserEmailRatingDto> getUserEmailRatingByRatingDesc() {
        return userRepository.findTop50ByOrderByRatingDesc().stream()
                .map(userEmailRatingDtoMapper::map).collect(Collectors.toList());
    }
}
