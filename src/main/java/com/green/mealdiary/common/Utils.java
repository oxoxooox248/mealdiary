package com.green.mealdiary.common;

import com.green.mealdiary.meal.model.MealSelVo;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Utils{
    //nullCheck는 null이면 true
    //onlySpace는 스페이스만 있으면 true
    //blankCheck는 크기 0인 문자열이면 true
    //formCheck는 특수문자나 띄어쓰기가 있으면 true
    public static boolean formCheck(String str){
        return Pattern.matches(Const.REGEXP_PATTERN_CHAR, str);
    }//한글,영어,숫자 허용& 띄어쓰기, 특수문자 불가
    public static boolean isEmpty(String str){
        return str.isEmpty()
                ||Pattern.matches(Const.REGEXP_PATTERN_SPACE_CHAR, str);}
    public static boolean tagCheck(String tag){
        return !formCheck(tag)||isEmpty(tag);
    }//태그에 특수문자 혹흔 띄어쓰기가 있거나 공백만 있으면 안된다.
    //특수문자 혹흔 띄어쓰기가 있거나 공백만 있으면 true
    public static boolean tagListCheck(List<String> tagList){
        for (String tag : tagList){
            if(tagCheck(tag)){
                return true;
            }//태그에 빈 칸이거나 띄어쓰기 혹은 특수문자가 들어가면 안 된다
        }
        return false;
    }
    public static boolean picListCheck(List<String> picList){
        for (String pic : picList){
            if(isEmpty(pic)){
                return true;
            }//사진이 빈 칸이면 안 된다
        }
        return false;
    }
    public static boolean picIdxCheck(List<Integer> picIdx){
        if(picIdx.size()>Const.PIC_MAX||picIdx.size()<Const.PIC_MIN){
            return true;
        }
        for(int i=0; i<picIdx.size();i++){
            if(picIdx.get(i)<Const.PIC_IDX_MIN||picIdx.get(i)>Const.PIC_IDX_MAX){
                return true;
            }
        }
        switch (picIdx.size()){
            case 1:
                break;
            case 2:
                if(picIdx.get(0).equals(picIdx.get(1))){
                    return true;
                }
                break;
            case 3:
                if(picIdx.get(0).equals(picIdx.get(1))
                        ||picIdx.get(1).equals(picIdx.get(2))
                        ||picIdx.get(0).equals(picIdx.get(2))){
                    return true;
                }
                break;
        }
        return false;
    }
    public static boolean tagIdxCheck(List<Integer> tagIdx){
        return true;
    }
    public static List<MealSelVo> abnormalSearchForm(){
        List<MealSelVo> list= new ArrayList();
        MealSelVo vo= new MealSelVo();
        vo.setResult(Const.ABNORMAL_SEARCH_FORM);//비정상적인 검색어 형식
        list.add(vo);
        return list;
    }
    public static List<MealSelVo> abnormalValue(){
        List<MealSelVo> list= new ArrayList();
        MealSelVo vo= new MealSelVo();
        vo.setResult(Const.ABNORMAL_FORM);//비정상적인 검색어 형식
        list.add(vo);
        return list;
    }
    public static boolean normalValue(int rowCount, int page, int bookmark){
        return (rowCount == Const.ROW_COUNT1 || rowCount == Const.ROW_COUNT2)
                && page > Const.ZERO
                && (bookmark == Const.ZERO || bookmark == Const.BOOKMARK_ON);
    }
}
