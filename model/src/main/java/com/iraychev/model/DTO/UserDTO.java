package com.iraychev.model.DTO;

import com.iraychev.model.enums.UserRole;
import com.iraychev.model.entities.Listing;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class UserDTO {

    private UUID id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private List<Listing> listings;
}
