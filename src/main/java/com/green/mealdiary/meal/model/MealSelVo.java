package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealSelVo {
    private String title;
    private String comment;
    private List<String> pics;
    private List<String> tags;
}
