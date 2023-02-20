package edu.neuroginarium.controller;

import edu.neuroginarium.dto.UserDto;
import edu.neuroginarium.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping
    public void sendEmailConfirmation(@RequestParam String email) {
        registrationService.sendEmailConfirmation(email);
    }

    @PostMapping
    public void checkEmailConfirmationToken(@RequestParam String token, @RequestParam String email) {
        registrationService.checkEmailConfirmationToken(token, email);
    }

    @PostMapping
    public void addUser(@Valid @RequestBody UserDto userDto) {
        registrationService.addUser(userDto);
    }
}