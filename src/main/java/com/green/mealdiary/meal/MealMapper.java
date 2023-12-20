package com.green.mealdiary.meal;

import com.green.mealdiary.meal.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MealMapper {
    List<MealSelVo> selMeal(MealSelDto dto);//일지 리스트1
    List<MealPicSelVo> selMealPicByImealList(List<Integer> imealList);//일지 리스트2
    List<MealTagSelVo> selMealTagByImealList(List<Integer> imealList);//일지 리스트3
    int updMeal(MealUpdDto dto);//일지 수정(사진, 태그 제외)
    int insMeal(MealInsDto dto);//일지 작성1
    int insMealPics(MealInsDto dto);//일지 작성2
    int insMealTags(MealInsDto dto);//일지 작성3
    Integer selMealByImeal(int imeal);//일지삭제1
    int delMealPicByImeal(int imeal);//일지삭제2
    int delMealTagByImeal(int imeal);//일지삭제3
    int delMeal(int imeal);//일지삭제4
    int insMealTag(MealTagInsDto dto);//일지 태그 추가
    int delMealTag(int itag);//일지 태그 삭제
    int updMealTag(MealTagUpdDto dto);//일지 태그 수정
    int insMealPic(MealPicInsDto dto);//일지 사진 추가
    int delMealPic(int ipic);//일지 사진 삭제
    int updMealPic(MealPicUpdDto dto);//일지 사진 수정
    int toggleBookmark(MealBookmarkDto dto);//북마크 온오프
    MealSelDetailVo selDetail(int imeal);//일지 상세 정보1
    List<String> selMealPics(int imeal);//일지 상세 정보2, 일지 사진  전 체크
    List<String> selMealTags(int imeal);//일지 상세 정보3, 일지 태그 추가 전 체크

}
