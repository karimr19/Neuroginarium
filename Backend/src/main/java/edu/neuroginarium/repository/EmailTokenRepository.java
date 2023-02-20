package edu.neuroginarium.repository;

import java.util.Optional;

import edu.neuroginarium.model.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    public Optional<EmailToken> findEmailTokenByEmail(String email);
}