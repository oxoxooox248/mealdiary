package com.green.mealdiary.meal.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class MealSelDto {
    private int page;
    @JsonIgnore
    private int startIdx;
    private int rowCount;
    private int bookmark;
    public void setPage(int page){
        this.startIdx= (page-1)*rowCount;
    }

}
