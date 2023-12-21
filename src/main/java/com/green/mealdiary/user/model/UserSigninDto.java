package com.green.mealdiary.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSigninDto {
    @NotNull
    private String uid;
    @NotNull
    private String upw;
}
