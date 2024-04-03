package com.iraychev.booking.validators;

import com.iraychev.booking.DTO.UserDTO;

import java.util.regex.Pattern;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class UserValidator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    public boolean isValidUser(UserDTO userDTO) {
        return (userDTO.getEmail() == null || isValidEmail(userDTO.getEmail())) &&
                (userDTO.getUsername() == null || isValidUsername(userDTO.getUsername())) &&
                (userDTO.getPassword() == null || isValidPassword(userDTO.getPassword()));
    }

    private boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    private boolean isValidUsername(String username) {
        return username != null && Pattern.matches(USERNAME_REGEX, username);
    }

    private boolean isValidPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_REGEX, password);
    }
}
