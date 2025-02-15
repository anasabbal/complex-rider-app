package com.verse.user.dto;

import com.verse.user.models.UserProfile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Data
@Getter
@Setter
public class UserProfileDto {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;
    private String address;
    private String createdBy;
    private LocalDateTime updatedAt;
    private Double rating;

    public static UserProfileDto toUserProfileDto(final UserProfile userProfile) {
        final UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setId(userProfile.getId());
        userProfileDto.setFirstName(userProfile.getFirstName());
        userProfileDto.setLastName(userProfile.getLastName());
        userProfileDto.setPhoneNumber(userProfile.getPhoneNumber());
        userProfileDto.setProfilePictureUrl(userProfile.getProfilePictureUrl());
        userProfileDto.setAddress(userProfile.getAddress());
        userProfileDto.setCreatedBy(userProfile.getCreatedBy());
        userProfileDto.setUpdatedAt(LocalDateTime.now());
        userProfileDto.setRating(userProfile.getRating());

        return userProfileDto;
    }
}
