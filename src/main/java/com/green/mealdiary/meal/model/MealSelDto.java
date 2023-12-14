package com.green.mealdiary.meal.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



@Data
public class MealSelDto {
    private int page;//페이지
    @JsonIgnore
    private int startIdx;// 시작 인덱스 넘버
    private int rowCount;//페이지 당 일지 수
    private int bookmark;//북마크 여부
    private String search;//검색어
    @JsonIgnore
    private String searchText;//(1)첫 글자가 "#"일 때 (2)아닐 때
    @JsonIgnore
    private String search2;//(1)검색어 (2)"#"뒤에 검색어

    public void setPage(int page){
        this.startIdx= (page-1)*this.rowCount;//page와 rowCount를 이용하여 startIdx를 구한다.
    }



}
