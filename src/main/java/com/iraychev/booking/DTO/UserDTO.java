package com.iraychev.booking.DTO;

import com.iraychev.booking.model.Listing;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserDTO {


    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private List<Listing> listings;
}
