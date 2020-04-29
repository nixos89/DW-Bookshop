package com.nikolas.master_thesis.core;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Encoded;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"orders"})
public class User {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;

    @NotNull
    @Pattern(regexp = ".+@.+\\.[a-z]+")
    private String email;

    @Encoded
    private String password;
    private Role role; // Many-To-One
    private Set<Order> orders; // One-To-Many

    public User(Long userId, String firstName, String lastName, String username, String email, String password, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
