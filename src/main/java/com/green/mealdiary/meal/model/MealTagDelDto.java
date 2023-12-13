package com.green.mealdiary.meal.model;

import lombok.Data;

@Data
public class MealTagDelDto {//일지 태그 삭제
    private int imeal;//해당 일지pk
    private int itag;//해당 태그pk
}
