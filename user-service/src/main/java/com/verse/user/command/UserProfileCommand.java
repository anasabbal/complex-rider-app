package com.verse.user.command;


import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserProfileCommand {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;
    private String address;
}
