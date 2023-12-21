package com.green.mealdiary.meal.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.mealdiary.common.Const;
import com.green.mealdiary.common.Utils;
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
    private boolean existHashSign;//(1) 첫 글자가 "#"일 때 (2) 아닐 때 null
    @JsonIgnore
    private String search2;//(1)검색어 (2)"#"뒤에 검색어


    public void setPage(int page){
        this.startIdx= (page-1)*this.rowCount;
    }
    //page와 rowCount를 이용하여 startIdx를 구한다.
    public void setSearch2(String search){
        if(search.substring(0,1).equals(Const.HASH_SIGN)){//검색어 첫 글자가 #이면(#찌개)
            this.existHashSign=true;
            if(Utils.formCheck(search.substring(1))){
            //#뒤에 띄어쓰기나 특수문자 없으면
                this.search2=String.format("%%%s%%",search.substring(1));//search2= "%찌개%" > 태그 검색
            }
            //#뒤에 띄어쓰기나 특수문자 있으면
        }
        if(Utils.formCheck(search)){
        //검색어에 띄어쓰기나 특수문자 없으면
           this.search2=String.format("%%%s%%",search);//search2= "%찌개%" > 제목 검색
        }
        //비정상적인 검색어 형식

    }
}
