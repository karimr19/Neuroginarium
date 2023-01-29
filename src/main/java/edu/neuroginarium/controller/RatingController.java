package edu.neuroginarium.controller;

import edu.neuroginarium.dto.UserEmailRatingDto;
import edu.neuroginarium.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public Long getRating(@RequestParam Long id) {
        return ratingService.getRatingById(id);
    }

    @GetMapping
    public List<UserEmailRatingDto> getEmailRatingByRatingDesc() {
        return ratingService.getUserEmailRatingByRatingDesc();
    }
}
