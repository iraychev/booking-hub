package com.iraychev.server.controller;

import com.iraychev.model.DTO.AuthRequestDTO;
import com.iraychev.model.DTO.JwtResponseDTO;
import com.iraychev.server.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/booking-api/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequestDTO.getUsername());
            long expirationTimeInSeconds = jwtService.getExpirationTimeInSeconds();

            return JwtResponseDTO.builder()
                    .accessToken(token)
                    .expiresIn(expirationTimeInSeconds)
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
