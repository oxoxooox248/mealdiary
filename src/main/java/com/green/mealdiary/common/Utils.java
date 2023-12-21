package com.green.mealdiary.common;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.green.mealdiary.meal.model.MealSelVo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Utils{
    //nullCheck는 null이면 true
    //onlySpace는 스페이스만 있으면 true
    //blankCheck는 크기 0인 문자열이면 true
    //formCheck는 특수문자나 띄어쓰기가 있으면 true
    public static boolean nullCheck(Integer value){
        return value==null;
    }
    public static boolean nullCheck(String... str){
        return str==null;
    }
    public static boolean nullCheck(List... list){
        return list==null;
    }
    public static boolean onlySpace(String str){
        return Pattern.matches(Const.REGEXP_PATTERN_SPACE_CHAR, str);
    }
    public static boolean formCheck(String str){
        return Pattern.matches(Const.REGEXP_PATTERN_CHAR, str);
    }
    public static boolean blankCheck(String str){
        return str.equals("");
    }
    public static boolean allCheck(String str){
        return nullCheck(str)||onlySpace(str)||blankCheck(str);
    }//문자열이 null 혹은 빈 칸인지 체크(null 혹은 빈 칸이면 true)
    public static boolean allCheck(String str1, String str2, String str3){
        return allCheck(str1)||allCheck(str2)||allCheck(str3);
    }
    public static boolean tagCheck(String tag){
        return !formCheck(tag)||onlySpace(tag)||blankCheck(tag);
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
            if(onlySpace(pic)
                    ||blankCheck(pic)){
                return true;
            }//사진이 빈 칸이면 안 된다
        }
        return false;
    }
    public static List<MealSelVo> abnormalSearchForm(){
        List<MealSelVo> list= new ArrayList();
        MealSelVo vo= new MealSelVo();
        vo.setResult(Const.ABNORMAL_SEARCH_FORM);//비정상적인 검색어 형식
        list.add(vo);
        return list;
    }
}
