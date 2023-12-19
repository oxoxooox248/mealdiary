package com.green.mealdiary.common;

import java.util.List;
import java.util.regex.Pattern;

public class Utils{
    public static boolean nullCheck(Integer value){
        return value==null;
    }
    public static boolean nullCheck(String str){
        return str==null;
    }
    public static boolean nullCheck(List<String> strList){
        return strList==null;
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
    }//문자열이 null 혹은 빈 칸인지 체크
    public static boolean tagCheck(String tag){
        return !formCheck(tag)||onlySpace(tag)||blankCheck(tag);
    }//태그에 특수문자 혹흔 띄어쓰기가 있거나 공백만 있으면 안된다.
}
