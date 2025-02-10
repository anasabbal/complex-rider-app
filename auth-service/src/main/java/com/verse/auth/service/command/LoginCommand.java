package com.verse.auth.service.command;

import com.verse.auth.service.annotation.ValidUserCommand;
import lombok.Data;


@Data
public class LoginCommand {
    @ValidUserCommand(message = "Invalid email")
    private String email;

    @ValidUserCommand(message = "Password must be at least 6 characters")
    private String password;

}
