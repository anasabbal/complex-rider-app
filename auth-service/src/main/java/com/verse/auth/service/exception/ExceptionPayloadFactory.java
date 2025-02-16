package com.verse.auth.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionPayloadFactory {

    TECHNICAL_ERROR(0, HttpStatus.INTERNAL_SERVER_ERROR, "technical.error"),

    INVALID_PAYLOAD(1, HttpStatus.BAD_REQUEST, "invalid.request.payload"),
    MISSING_REQUEST_BODY_ERROR_CODE(2, HttpStatus.BAD_REQUEST, "request.missing.body"),
    EMAIL_ALREADY_EXIST(3, HttpStatus.BAD_REQUEST, "email.already.exist"),
    USER_NAME_ALREADY_EXIST(4, HttpStatus.BAD_REQUEST, "username.already.exist"),
    USER_NOT_FOUND(5, HttpStatus.NOT_FOUND, "user.not.found");

    private final Integer code;
    private final HttpStatus status;
    private final String message;

    public ExceptionPayload get() {
        return new ExceptionPayload(code, status, message);
    }
}
