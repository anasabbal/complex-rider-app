package com.verse.auth.service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.verse.auth.service.validator.DateTimeToStringSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
public abstract class BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    private String id; // Use String as the identifier

    @Version
    private Integer version;

    @CreatedDate
    @JsonSerialize(using = DateTimeToStringSerializer.class)
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    @JsonSerialize(using = DateTimeToStringSerializer.class)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    private Boolean deleted = false;
    private Boolean active = true;
}
