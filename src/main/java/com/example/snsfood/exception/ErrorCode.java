package com.example.snsfood.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_VALID_USER(HttpStatus.BAD_REQUEST, "유저정보가 일치 하지 않습니다.")
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
