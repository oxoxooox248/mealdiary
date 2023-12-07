package com.green.mealdiary.meal.model;


import lombok.Data;

@Data
public class MealSelDto {
    private int page;
    private int startIdx;
    private int rowCount;

    public MealSelDto(int page){
        startIdx= (page-1)*rowCount;
    }
}
