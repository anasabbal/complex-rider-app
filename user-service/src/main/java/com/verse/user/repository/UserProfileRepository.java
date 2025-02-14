package com.verse.user.repository;

import com.verse.user.models.UserProfile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserProfileRepository extends ReactiveMongoRepository<UserProfile, String> {
}
