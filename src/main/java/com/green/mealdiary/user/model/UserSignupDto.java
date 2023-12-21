package com.green.mealdiary.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSignupDto {
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    @JsonIgnore
    private String hashedPw;
}
