package com.verse.user.mapper;


import com.verse.user.dto.UserProfileDto;
import com.verse.user.models.UserProfile;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class UserProfileMapper {
    public abstract UserProfileDto toDto(UserProfileDto userProfileDto);
    public abstract List<UserProfileDto> toDtos(List<UserProfile> userProfiles);
}
