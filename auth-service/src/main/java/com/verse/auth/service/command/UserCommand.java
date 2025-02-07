package com.verse.auth.service.command;

import com.verse.auth.service.annotation.ValidUserCommand;
import com.verse.auth.service.models.Role;
import lombok.Data;

import java.util.Set;


@Data
public class UserCommand {

    @ValidUserCommand(message = "Invalid username")
    private String username;

    @ValidUserCommand(message = "Invalid email")
    private String email;

    @ValidUserCommand(message = "Password must be at least 6 characters")
    private String password;

    @ValidUserCommand(message = "Role must be provided")
    private Set<Role> roles;
}
