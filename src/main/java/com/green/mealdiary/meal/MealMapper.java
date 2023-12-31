package com.green.mealdiary.meal;

import com.green.mealdiary.meal.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MealMapper {
    //일지 리스트
    List<MealSelVo> selMeal(MealSelDto dto);
    List<MealPicSelVo> selMealPicByImealList(List<Integer> imealList);
    List<MealTagSelVo> selMealTagByImealList(List<Integer> imealList);

    //일지 수정
    int updMeal(MealUpdDto dto);//일지 수정(사진, 태그 제외)

    //일지 작성
    int insMeal(MealInsDto dto);
    int insMealPics(MealInsDto dto);
    int insMealTags(MealInsDto dto);

    //일지삭제
    Integer selMealByImeal(int imeal);
    int picCntByImeal(int imeal);
    int delMealPicByImeal(int imeal);
    int tagCntByImeal(int imeal);
    int delMealTagByImeal(int imeal);
    int delMeal(int imeal);


    //북마크 온오프
    int toggleBookmark(MealBookmarkDto dto);

    //일지 상세 정보
    MealSelDetailVo selDetail(int imeal);//일지 상세 정보
    List<String> selMealPics(int imeal);//일지 사진 정보
    List<String> selMealTags(int imeal);//일지 태그 정보
}
