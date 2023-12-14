package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealSelVo {
    private int imeal;//일지pk
    private String title;//제목(음식이름)
    private String review;//후기
    private String createdAt;//작성일자
    private List<String> pics;//일지 사진
    private List<String> tags;//일지 태그
    private int result=1;
}
