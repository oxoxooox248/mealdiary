package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealUpdDto {//일지 변경
    private int imeal;//해당 일지pk
    private String title;//제목(음식 이름)
    private String ingredient;//재료
    private String recipe;//레시피
    private String review;//후기
}
