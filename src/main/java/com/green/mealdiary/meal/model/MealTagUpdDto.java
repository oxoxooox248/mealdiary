package com.green.mealdiary.meal.model;

import lombok.Data;

@Data
public class MealTagUpdDto {//태그 수정
    private String tag;//수정할 태그 내용
    private int itag;//태그pk
}
