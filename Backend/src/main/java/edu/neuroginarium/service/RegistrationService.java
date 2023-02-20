package edu.neuroginarium.service;

import java.util.Objects;
import java.util.UUID;

import edu.neuroginarium.dto.UserDto;
import edu.neuroginarium.exception.EmailConfirmationFailedException;
import edu.neuroginarium.exception.NotFoundException;
import edu.neuroginarium.model.EmailToken;
import edu.neuroginarium.model.User;
import edu.neuroginarium.repository.EmailTokenRepository;
import edu.neuroginarium.repository.UserRepository;
import edu.neuroginarium.utils.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
public class RegistrationService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final EmailTokenRepository emailTokenRepository;

    private final UserDtoMapper mapper;

    public void addUser(UserDto userDto) {
        User user = mapper.map(userDto);
        userRepository.save(user);
    }

    public void sendEmailConfirmation(String email) {
        String token = UUID.randomUUID().toString();
        emailTokenRepository.findEmailTokenByEmail(email)
                .ifPresentOrElse(emailToken -> emailTokenRepository.save(emailToken.setToken(token)),
                                 () -> emailTokenRepository.save(new EmailToken()
                                                                    .setEmail(email)
                                                                    .setToken(token)));
        String subject = "Registration Confirmation";
        String text = "Your registration token is: \"" + token + "\"";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(email);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }

    public void checkEmailConfirmationToken(String token, String email) {
        var optionalEmailToken = emailTokenRepository.findEmailTokenByEmail(email);
        if (optionalEmailToken.isPresent()) {
            if (!Objects.equals(optionalEmailToken.get().getToken(), token)) {
                throw new EmailConfirmationFailedException(email);
            }
        } else {
            throw new NotFoundException(EmailToken.class, email);
        }
    }
}