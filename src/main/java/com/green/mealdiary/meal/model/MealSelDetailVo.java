package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealSelDetailVo {//상세 정보(재료, 레시피 추가)
    private int imeal;
    private String title;//제목
    private String ingredient;//재료
    private String recipe;//레시피
    private int bookmark;//북마크 여부
    private String createdAt;//작성일자
    private List<String> pics;//일지의 사진들(1~3)
    private List<String> tags;//일지의 태그들(0~5)
}
