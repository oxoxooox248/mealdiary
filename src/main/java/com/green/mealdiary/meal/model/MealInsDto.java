package com.green.mealdiary.meal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class MealInsDto {
    @JsonIgnore
    private int imeal;//일지 작성할 때 생성되는 pk값
    @JsonIgnore
    private int iuser= 1;//유저pk 1로 고정
    @NotNull
    private String title;//제목(음식이름) not null
    @NotNull
    private String ingredient;//재료 not null
    @NotNull
    private String recipe;//레시피 not null
    private String review;//리뷰
    @NotNull
    private List<String> pics;//사진(1~3)
    private List<String> tags;//태그(~5)
}
