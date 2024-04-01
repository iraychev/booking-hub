package com.iraychev.booking.DTO;

import com.iraychev.booking.enums.UserRole;
import com.iraychev.booking.model.Listing;

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
