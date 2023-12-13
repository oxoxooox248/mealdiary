package com.green.mealdiary.meal.model;

import lombok.Data;

@Data
public class MealTagInsDto {//태그 추가
    private int imeal;//해당 일지pk
    private String tag;//추가할 태그 내용
}
