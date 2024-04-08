package com.iraychev.server;

import com.iraychev.model.DTO.UserDTO;
import com.iraychev.model.enums.UserRole;
import com.iraychev.server.controller.UserController;
import com.iraychev.server.service.UserService;
import com.iraychev.server.validators.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private UserService userService;
    @Mock
    private UserValidator userValidator;
    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateUser() {
        UserDTO newUser = new UserDTO();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");
        newUser.setPassword("Password123!!");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.ADMIN);
        newUser.setRoles(userRoles);

        when(userService.createUser(newUser)).thenReturn(newUser);
        when(userValidator.isValidUser(newUser)).thenReturn(true);
        ResponseEntity<?> response = userController.create(newUser);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newUser, response.getBody());
    }

    @Test
    public void testGetAllUsers() {
        List<UserDTO> users = Arrays.asList(new UserDTO(), new UserDTO());

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = userController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetUserById() {
        UUID userId = UUID.randomUUID();
        UserDTO user = new UserDTO();
        user.setId(userId);

        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.getById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    public void testUpdateUser(){
        UUID userId = UUID.randomUUID();

        UserDTO updatedUserDetails = new UserDTO();
        updatedUserDetails.setUsername("updatedUser");

        when(userService.updateUserById(userId,updatedUserDetails)).thenReturn(updatedUserDetails);
        when(userValidator.isValidUser(updatedUserDetails)).thenReturn(true);


        ResponseEntity<?> response = userController.update(userId, updatedUserDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUserDetails, response.getBody());
    }

    @Test
    public void testDeleteUser() {
        UUID userId = UUID.randomUUID();

        when(userService.deleteUserById(userId)).thenReturn(true);

        ResponseEntity<Void> response = userController.delete(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());


        verify(userService).deleteUserById(userId);
    }
}