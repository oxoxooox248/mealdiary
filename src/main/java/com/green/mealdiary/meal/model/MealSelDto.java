package com.green.mealdiary.meal.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



@Data
public class MealSelDto {
    private int page;
    @JsonIgnore
    private int startIdx;
    private int rowCount;
    private int bookmark;
    private String search;
    @JsonIgnore
    private String searchText;
    @JsonIgnore
    private String search2;
    //%d %S

    public void setPage(int page){
        this.startIdx= (page-1)*rowCount;
    }


    public void setSearch(String search){
        if(search.substring(0,1).equals("#")){//(0이상 1미만) 첫 한 글자가 "#"이면
            searchText=search.substring(0,1);// searchText= "#"
            search2= String.format("%%%s%%",search.substring(1));//#찌개 > %찌개%(태그)
        } else{
            search2= String.format("%%%s%%",search);//searchText= null, 찌개 > %찌개%(제목)
        }
    }


}
