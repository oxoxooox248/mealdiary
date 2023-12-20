package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealSelDetailVo {//상세 정보(재료, 레시피 추가)
    private String ingredient;//재료
    private String recipe;//레시피
    private int bookmark;//북마크 여부
}
