package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealSelVo {
    private int imeal;
    private String title;
    private String review;
    private String createdAt;
    private List<String> pics;
    private List<String> tags;
}
