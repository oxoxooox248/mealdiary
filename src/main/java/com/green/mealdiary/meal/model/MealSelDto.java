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


    public void setSearch(String search){
        if(search.substring(0,1).equals("#")){//(0이상 1미만) 첫 한 글자가 "#"이면
            searchText=search.substring(0,1);//searchText= "#"
            search2= String.format("%%%s%%",search.substring(1));//#찌개 > %찌개%(태그)
        } else{
            search2= String.format("%%%s%%",search);//searchText= null, 찌개 > %찌개%(제목)
        }
    }


}
