package com.green.mealdiary.meal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.util.List;

@Data
public class MealInsDto {
    @JsonIgnore
    private int imeal;
    @JsonIgnore
    private int iuser= 1;
    private String title;
    private String ingredient;
    private String recipe;
    private String review;
    private List<String> pics;
    private List<String> tags;
}
