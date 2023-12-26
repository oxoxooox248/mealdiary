package com.green.mealdiary.common;

public class Const {
    public static final int ZERO= 0; // 0의미
    public static final int FAIL= -1; // 실패
    public static final int SUCCESS= 1; // 성공
    public static final int BOOKMARK_ON= 1; // 북마크 On
    public static final int BOOKMARK_OFF= 0; // 북마크 Off
    public static final int PIC_MIN= 1; // 최소 사진 1장
    public static final int PIC_MAX= 3; // 최대 사진 3장
    public static final int TAG_MAX= 5; // 최대 태그 5개
    public static final int NO_EXIST= -1; // 존재하지 않음
    public static final String REGEXP_PATTERN_CHAR = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$";
    //한글,영어,숫자만 허용(띄어쓰기, 특수문자 사용 불가)
    public static final String REGEXP_PATTERN_SPACE_CHAR = "^[\\s]*$";
    //스페이스와 탭만 존재하는 문자열
    public static final String REGEXP_PATTERN_NO_SPACE_CHAR = "^[\\S]*$";
    //문자열만 허용하는 정규식 - 공백 미 허용
    public static final int BAD_REQUEST= -1; // 잘못된 요청
    public static final int SEARCH_MAX= 10; // 검색어 글자 최대 10개초과
    public static final String HASH_SIGN= "#"; // 검색시 #있는지 확인여부
    public static final int LOGIN_NO_ID= 2; // 로그인 시 아이디 없음
    public static final int LOGIN_DIFF_PW= 3; // 로그인 시 비밀번호 틀림
    public static final int ROW_COUNT1= 1; // 페이지당 일지 수 (크게보기)
    public static final int ROW_COUNT2= 4; // 페이지당 일지 수 (작게보기)
    public static final int PIC_IDX_MIN= 0; // 사진 배열 최소 방번호 0
    public static final int PIC_IDX_MAX= 2; // 사진 배열 최대 방번호 2
}
