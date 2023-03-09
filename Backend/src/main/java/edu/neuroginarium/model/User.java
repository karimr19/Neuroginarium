package edu.neuroginarium.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@SequenceGenerator(allocationSize = 1, name = "user_seq", sequenceName = "user_seq")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private long rating;
}