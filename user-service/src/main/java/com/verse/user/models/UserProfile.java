package com.verse.user.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.verse.user.command.UserProfileCommand;
import com.verse.user.utils.DateTimeToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("users-profile")
public class UserProfile {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;
    private String address;
    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    @JsonSerialize(using = DateTimeToStringSerializer.class)
    private LocalDateTime updatedAt;

    private Double rating;


    public static UserProfile create(final UserProfileCommand command){
        final UserProfile userProfile = new UserProfile();

        userProfile.setFirstName(command.getFirstName());
        userProfile.setLastName(command.getLastName());
        userProfile.setPhoneNumber(command.getPhoneNumber());
        userProfile.setProfilePictureUrl(command.getProfilePictureUrl());
        userProfile.setAddress(command.getAddress());

        return userProfile;
    }
}
