package com.green.mealdiary.meal.model;

import lombok.Data;

import java.util.List;

@Data
public class MealSelDetailVo {//상세 정보(재료, 레시피 추가)
    private int imeal;//일지pk
    private String title;//제목(음식 제목)
    private String review;//후기
    private String createdAt;//작성 일자
    private List<String> pics;//일지 사진
    private List<String> tags;//일지 태그
    private String recipe;//레시피
    private String ingredient;//재료
    private int bookMark;//북마크 여부
}
