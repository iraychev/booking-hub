package com.iraychev.model.entities;

import com.iraychev.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String email;

    @ElementCollection(targetClass = UserRole.class)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();
}
