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
    @Schema(required = false)
    private String search;
    @JsonIgnore
    private String searchText;
    @JsonIgnore
    private String search2;

    public void setPage(int page){
        this.startIdx= (page-1)*rowCount;
    }
    public void setSearch(String search){
        if(search.substring(0,1).equals("#")){
            searchText=search.substring(0,1);
            search2= String.format("%%%s%%",search.substring(1));
        } else{
            search2= String.format("%%%s%%",search);
        }
    }


}
