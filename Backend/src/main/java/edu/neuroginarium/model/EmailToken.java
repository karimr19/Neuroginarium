package edu.neuroginarium.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@SequenceGenerator(allocationSize = 1, name = "email_token_seq", sequenceName = "email_token_seq")
public class EmailToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_token_seq")
    private Long id;

    String email;

    String token;
}