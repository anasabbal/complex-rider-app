package com.verse.auth.service.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@Document(collection = "refresh_tokens")  // Marks this class as a MongoDB document
public class RefreshTokenEntity extends BaseEntity {

    @Id
    private String id;  // Use String as the identifier for MongoDB

    private String userId;
    private String token;
    private Instant expiryDate;
}
