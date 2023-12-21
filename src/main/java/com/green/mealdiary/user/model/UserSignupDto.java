package com.green.mealdiary.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSignupDto {
    @NotNull
    private String uid;
    @NotNull
    private String upw;
    @NotNull
    private String nm;
    private String pic;
    @JsonIgnore
    private String hashedPw;
}
