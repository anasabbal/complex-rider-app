package com.verse.auth.service.validator;

import com.verse.auth.service.annotation.ValidUserCommand;
import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.models.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

public class UserCommandValidator implements ConstraintValidator<ValidUserCommand, UserCommand> {

    @Override
    public void initialize(ValidUserCommand constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserCommand userCommand, ConstraintValidatorContext context) {
        if (userCommand == null) {
            return false;
        }

        boolean isValid = true;

        isValid &= validateUsername(userCommand.getUsername(), context);
        isValid &= validateEmail(userCommand.getEmail(), context);
        isValid &= validatePassword(userCommand.getPassword(), context);
        isValid &= validateRoles(userCommand.getRoles(), context); // Updated for Set<Role>

        return isValid;
    }

    private boolean validateUsername(String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty() || !username.matches("^[a-zA-Z0-9_]+$")) {
            context.buildConstraintViolationWithTemplate("Invalid username").addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email, ConstraintValidatorContext context) {
        if (email == null || !Pattern.matches("^[\\w-]+(?:\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$", email)) {
            context.buildConstraintViolationWithTemplate("Invalid email").addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password, ConstraintValidatorContext context) {
        if (password == null || password.length() < 6) {
            context.buildConstraintViolationWithTemplate("Password must be at least 6 characters").addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validateRoles(Set<Role> roles, ConstraintValidatorContext context) {
        if (roles == null || roles.isEmpty()) {
            context.buildConstraintViolationWithTemplate("At least one role must be provided").addConstraintViolation();
            return false;
        }
        return true;
    }
}
