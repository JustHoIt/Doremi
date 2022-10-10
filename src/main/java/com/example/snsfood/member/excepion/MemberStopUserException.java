package com.example.snsfood.member.excepion;

public class MemberStopUserException extends RuntimeException {
    public MemberStopUserException(String error) {
        super(error);
    }
}
