package com.iraychev.server.service;

import com.iraychev.model.DTO.UserDTO;
import com.iraychev.model.entities.User;
import com.iraychev.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    public UserDTO getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    public UserDTO getUserById(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }
    @PreAuthorize("hasRole('ADMIN') or (authentication.principal.id == #userId)")
    public UserDTO updateUserById(UUID userId, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            modelMapper.map(userDTO, existingUser);
            User updatedUser = userRepository.save(existingUser);
            return modelMapper.map(updatedUser, UserDTO.class);
        }
        return null;
    }
    @PreAuthorize("hasRole('ADMIN') or (authentication.principal.id == #userId)")
    public boolean deleteUserById(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }
        return false;
    }
}
