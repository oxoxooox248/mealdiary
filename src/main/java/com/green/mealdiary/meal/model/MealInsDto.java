package com.green.mealdiary.meal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
public class MealInsDto {
    @JsonIgnore
    private int imeal;
    @Schema(name = "유저 pk", hidden = true, required = false, defaultValue = "1")
    private int iuser;
    private String title;
    private String ingredient;
    private String recipe;
    private String review;
    private List<String> pics;
    private List<String> tags;
}
