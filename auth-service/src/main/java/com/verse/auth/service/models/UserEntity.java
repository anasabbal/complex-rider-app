package com.verse.auth.service.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.validator.RoleDeserializer;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document("users") // This marks the class as a MongoDB document
public class UserEntity extends BaseEntity {

    private String id;

    private String username;
    private String email;
    private String password;
    private Boolean enabled = true;

    @JsonDeserialize(contentUsing = RoleDeserializer.class)
    private Set<Role> roles;

    public static UserEntity create(final UserCommand command) {
        UserEntity entity = new UserEntity();
        entity.setUsername(command.getUsername());
        entity.setEmail(command.getEmail());
        entity.setPassword(command.getPassword());
        entity.setRoles(command.getRoles());
        return entity;
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}
