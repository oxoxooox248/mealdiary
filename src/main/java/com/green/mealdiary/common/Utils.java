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
}
