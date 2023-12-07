package com.green.mealdiary.meal.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MealSelDto {
    private int page;

    @JsonIgnore
    private int startIdx;

    @JsonIgnore
    private int rowCount;

    public MealSelDto(int page){
        startIdx= (page-1)*rowCount;
    }
}
