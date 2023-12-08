package com.green.mealdiary.meal.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class MealSelDto {
    private int page;
    @JsonIgnore
    private int startIdx;
    @Schema(defaultValue = "4")
    private int rowCount;
    @Schema(required = false)
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
        this.searchText=search.substring(0,1);
        if(this.searchText.equals("#")){
            this.search2= search.substring(1);
        }
    }

}
