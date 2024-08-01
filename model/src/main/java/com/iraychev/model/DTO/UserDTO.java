package com.iraychev.model.DTO;

import com.iraychev.model.entities.Image;
import com.iraychev.model.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {

    private UUID id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Image profileImage;
    private Set<UserRole> roles;
    private LocalDateTime joinedDate;
}
