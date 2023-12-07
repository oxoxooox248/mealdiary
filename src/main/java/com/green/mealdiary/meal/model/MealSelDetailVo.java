package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealSelDetailVo {
    private int imeal;
    private String title;
    private String review;
    private String createdAt;
    private List<String> pics;
    private List<String> tags;
    private String recipe;
    private String ingredient;
    private int bookMark;
}
