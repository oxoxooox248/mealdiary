package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealUpdDto {
    private int imeal;
    private String title;
    private String ingredient;
    private String recipe;
    private String review;
    private List<String> pics;
    private List<String> tags;
}
