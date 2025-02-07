package com.verse.auth.service.models;



import com.verse.auth.service.command.UserCommand;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

import java.util.Set;
import java.util.UUID;


@Data
@Table("users")
public class UserEntity extends BaseEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("username")
    private String username;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("enabled")
    private Boolean enabled = true;

    @Column("roles")
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

    public Set<Role> getRoles() {
        return roles;
    }
}

