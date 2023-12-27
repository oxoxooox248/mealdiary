package com.green.mealdiary.common;

import com.green.mealdiary.meal.model.*;
import com.green.mealdiary.user.model.UserSigninDto;
import com.green.mealdiary.user.model.UserSignupDto;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    //formCheck는 특수문자나 띄어쓰기가 없으면 true
    public static boolean formCheck(String str) {
        return Pattern.matches(Const.REGEXP_PATTERN_CHAR, str);
    }//한글,영어,숫자 허용& 띄어쓰기, 특수문자 불가

    //isEmpty는 크기 0이거나 공백 문자열이면 true
    public static boolean isEmpty(String str) {
        return str.isEmpty()
                || Pattern.matches(Const.REGEXP_PATTERN_SPACE_CHAR, str);
    }
    //notAllowSpace는 space가 있으면 false
    public static boolean notAllowSpace(String str){
        return Pattern.matches(Const.REGEXP_PATTERN_NO_SPACE_CHAR, str);
    }
    //태그 확인
    public static boolean tagCheck(String tag) {
        return !formCheck(tag) || isEmpty(tag);
    }//태그에 특수문자 혹흔 띄어쓰기가 있거나 공백만 있으면 안된다.
    //특수문자 혹흔 띄어쓰기가 있거나 공백만 있으면 true


    //일지 리스트 bad request
    public static List<MealSelVo> getMealListBadRequest() {
        List<MealSelVo> list = new ArrayList();
        MealSelVo vo = new MealSelVo();
        vo.setResult(Const.BAD_REQUEST);
        list.add(vo);
        return list;
    }

    //일지 리스트 요청 확인
    public static boolean normalValue(int rowCount, int page, int bookmark) {
        return (rowCount == Const.ROW_COUNT1 || rowCount == Const.ROW_COUNT2)
                && page > Const.ZERO
                && (bookmark == Const.ZERO || bookmark == Const.BOOKMARK_ON);
    }//rowCount가 0 혹은 4, page가 양수, bookmark가 0 또는 1이면 true

    //일지 리스트 요청 확인
    public static MealSelDto getMealListCheck(int rowCount, int page, int bookmark, String search) {
        MealSelDto dto = new MealSelDto();
        if (normalValue(rowCount, page, bookmark)) {
            dto.setRowCount(rowCount);//페이지 당 일지 갯수
            dto.setPage(page);//페이지
            dto.setBookmark(bookmark);//북마크 검색(0:모든 일지, 1: 책갈피 설정한 일지만)
        } else {
            return null;//bad request
        }

        if (search != null && !isEmpty(search)) {//검색어를 받았을 때
            if (search.length() > Const.SEARCH_MAX) {//검색어가 10자를 넘어가면
                return null;//bad request
            }
            dto.setSearch(search);//정상적인 검색어
        }
        return dto;
    }

    //태그 리스트 체크
    //태그에 빈 칸이거나 띄어쓰기 혹은 특수문자가 들어가면 true
    public static boolean tagListCheck(List<String> tagList) {
        for (String tag : tagList) {
            if (tagCheck(tag)) {
                return true;
            }
        }
        return false;
    }

    //사진 리스트 체크(사진이 빈 칸이면 안 된다)
    public static boolean picListCheck(List<String> picList) {
        for (String pic : picList) {
            if (isEmpty(pic)) {
                return true;
            }
        }
        return false;
    }


    //일지 수정 요청 확인
    public static ResVo putMealCheck(@Nullable MealUpdDto dto) {
        if (dto == null) {
            return new ResVo(Const.BAD_REQUEST);
            //dto가 null이면 bad request
        } else if (dto.getTitle() == null
                || dto.getIngredient() == null
                || dto.getRecipe() == null
                || dto.getPics() == null) {
            return new ResVo(Const.BAD_REQUEST);
            //제목, 재료, 레시피, 사진이 null이면
        } else if (Utils.isEmpty(dto.getTitle())
                || Utils.isEmpty(dto.getIngredient())
                || Utils.isEmpty(dto.getRecipe())
                || dto.getPics().isEmpty()
                || Utils.picListCheck(dto.getPics())
                ||dto.getPics().size()>Const.PIC_MAX) {
            return new ResVo(Const.BAD_REQUEST);
            //제목, 재료, 레시피는 반드시 입력 받아야 한다
        } else if (!(dto.getTags() == null)) {
            //태그가 있을 때
            if (dto.getTags().isEmpty() || dto.getTags().size()>Const.TAG_MAX) {
                //사이즈가 0이거나 5 초과
                return new ResVo(Const.BAD_REQUEST);
            } else if (Utils.tagListCheck(dto.getTags())) {
                //비정상적인 태그 리스트
                return new ResVo(Const.BAD_REQUEST);
            }
        }
        return new ResVo(Const.SUCCESS);
    }


    //일지 작성 요청 확인
    public static ResVo postMealCheck(@Nullable MealInsDto dto) {
        if (dto == null) {
            return new ResVo(Const.BAD_REQUEST);
        } else if (dto.getPics() == null || dto.getPics().isEmpty()) {//등록된 사진이 없다
            return new ResVo(Const.BAD_REQUEST);
        } else {
            if (Utils.picListCheck(dto.getPics())) {
                return new ResVo(Const.BAD_REQUEST);
            }//사진이 빈 칸이면 안 된다

            if (dto.getPics().size() > Const.PIC_MAX) {//등록된 사진이 최대 갯수를 초과했다
                return new ResVo(Const.BAD_REQUEST);
            }
        }
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            if (dto.getTags().size() > Const.TAG_MAX) {
                return new ResVo(Const.BAD_REQUEST);
            }//등록된 태그가 최대 갯수를 초과했다
            if (Utils.tagListCheck(dto.getTags())) {
                return new ResVo(Const.BAD_REQUEST);
            }//태그에 빈 칸이거나 띄어쓰기 혹은 특수문자가 들어가면 안 된다
        }
        if (dto.getTitle() == null
                || dto.getIngredient() == null
                || dto.getRecipe() == null
                || Utils.isEmpty(dto.getTitle())
                || Utils.isEmpty(dto.getIngredient())
                || Utils.isEmpty(dto.getRecipe())) {
            return new ResVo(Const.BAD_REQUEST);//제목, 재료, 레시피는 반드시 입력 받아야 한다
        }
        return new ResVo(Const.SUCCESS);
    }

    //회원가입 요청 확인
    public static ResVo signupCheck(@Nullable UserSignupDto dto){
        if(dto==null
                ||dto.getUid()==null
                ||dto.getUpw()==null
                ||dto.getNm()==null
                ||!notAllowSpace(dto.getUid())
                ||!notAllowSpace(dto.getUpw())
                ||!notAllowSpace(dto.getNm())){
            return new ResVo(Const.BAD_REQUEST);
        } else if(!(dto.getPic()==null)&&isEmpty(dto.getPic())){
            return new ResVo(Const.BAD_REQUEST);
        }
        return new ResVo(Const.SUCCESS);
    }

    //로그인 요청 확인
    public static ResVo signinCheck(@Nullable UserSigninDto dto){
        if(dto==null
                ||dto.getUid()==null
                ||dto.getUpw()==null
                ||!notAllowSpace(dto.getUid())
                ||!notAllowSpace(dto.getUpw())){
            return new ResVo(Const.BAD_REQUEST);
        }
        return new ResVo(Const.SUCCESS);
    }
}

