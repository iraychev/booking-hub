package com.iraychev.booking;

import com.iraychev.booking.DTO.UserDTO;
import com.iraychev.booking.controller.UserController;
import com.iraychev.booking.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateUser() {
        UserDTO newUser = new UserDTO();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");

        when(userService.createUser(newUser)).thenReturn(newUser);

        ResponseEntity<UserDTO> response = userController.create(newUser);
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
        UserDTO existingUser = new UserDTO();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");

        UserDTO updatedUserDetails = new UserDTO();
        updatedUserDetails.setUsername("updatedUser");

        when(userService.updateUserById(userId,updatedUserDetails)).thenReturn(existingUser);

        ResponseEntity<UserDTO> response = userController.update(userId, updatedUserDetails);
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
