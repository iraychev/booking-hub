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
import static org.mockito.Mockito.*;

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

    @Test
    public void testCreateUserWithInvalidData() {
        UserDTO invalidUser = new UserDTO();
        invalidUser.setUsername("u"); // too short
        invalidUser.setEmail("invalid-email"); // invalid email
        invalidUser.setPassword("weak"); // weak password

        when(userValidator.isValidUser(invalidUser)).thenReturn(false);

        ResponseEntity<?> response = userController.create(invalidUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetUserByIdNotFound() {
        UUID userId = UUID.randomUUID();
        when(userService.getUserById(userId)).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.getById(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUserWithInvalidData() {
        UUID userId = UUID.randomUUID();
        UserDTO invalidUser = new UserDTO();
        invalidUser.setEmail("invalid-email");

        when(userValidator.isValidUser(invalidUser)).thenReturn(false);

        ResponseEntity<?> response = userController.update(userId, invalidUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteNonExistentUser() {
        UUID userId = UUID.randomUUID();
        when(userService.deleteUserById(userId)).thenReturn(false);

        ResponseEntity<Void> response = userController.delete(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserByUsername() {
        String username = "testUser";
        UserDTO user = new UserDTO();
        user.setUsername(username);

        when(userService.getUserByUsername(username)).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.getByUsername(username);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUpdateUserPassword() {
        UUID userId = UUID.randomUUID();
        UserDTO updatedUser = new UserDTO();
        updatedUser.setPassword("newPassword123!");

        when(userValidator.isValidUser(updatedUser)).thenReturn(true);
        when(userService.updateUserById(userId, updatedUser)).thenReturn(updatedUser);

        ResponseEntity<?> response = userController.update(userId, updatedUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).updateUserById(userId, updatedUser);
    }
}