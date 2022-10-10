package com.example.snsfood.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ResetPasswordInput {
    private String userId;
    private String userName;
    private String nickname;

    private String password;
    private String id;
}

