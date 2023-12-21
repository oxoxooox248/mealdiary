package com.green.mealdiary.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSigninDto {
    private String uid;
    private String upw;
}
