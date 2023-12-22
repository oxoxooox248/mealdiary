package com.green.mealdiary.common;

public class Const {
    public static final int ZERO= 0;
    public static final int FAIL= -1;
    public static final int SUCCESS= 1;
    public static final int BOOKMARK_ON= 1;
    public static final int BOOKMARK_OFF= 0;
    public static final int PIC_MIN= 1;
    public static final int PIC_MAX= 3;
    public static final int TAG_MAX= 5;
    public static final int NO_EXIST= -1;
    public static final String REGEXP_PATTERN_CHAR = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$";
    //한글,영어,숫자만 허용(띄어쓰기, 특수문자 사용 불가)
    public static final String REGEXP_PATTERN_SPACE_CHAR = "^[\\s]*$";
    //스페이스와 탭만 존재하는 문자열
    public static final int BAD_REQUEST= -1;
    public static final int SEARCH_MAX= 10;
    public static final String HASH_SIGN= "#";
    public static final int LOGIN_NO_ID= 2;
    public static final int LOGIN_DIFF_PW= 3;
    public static final int ROW_COUNT1= 1;
    public static final int ROW_COUNT2= 4;
    public static final int PIC_IDX_MIN= 0;
    public static final int PIC_IDX_MAX= 2;
}
