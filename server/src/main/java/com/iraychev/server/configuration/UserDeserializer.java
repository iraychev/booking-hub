package com.iraychev.server.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.iraychev.model.entities.User;

import com.iraychev.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.UUID;

@JsonComponent
public class UserDeserializer extends JsonDeserializer<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        UUID userId = UUID.fromString(jsonParser.getText());
        return userRepository.findById(userId)
                .orElseThrow(() -> new IOException("User not found with ID: " + userId));
    }
}
