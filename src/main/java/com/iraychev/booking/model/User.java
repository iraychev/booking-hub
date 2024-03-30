package com.iraychev.booking.model;

import com.iraychev.booking.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String password;
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Listing> listings;

}
