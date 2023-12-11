package com.green.mealdiary.meal;

import com.green.mealdiary.meal.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MealMapper {
    int insMeal(MealInsDto dto);//일지 작성1
    int insMealPics(MealInsDto dto);//일지 작성2
    int insMealTags(MealInsDto dto);//일지 작성3
    int insMealPic(MealPicInsDto dto);//일지 사진 추가
    int insMealTag(MealTagInsDto dto);//일지 태그 추가
    int selBookmark(int imeal);//북마크 온오프 전 확인(1)
    int bookmarkOn(int imeal);//북마크 없으면 ON(2)-1
    int bookmarkOff(int imeal);//북마크 있으면 OFF(2)-2
    List<MealSelVo> selMeal(MealSelDto dto);//일지 리스트1
    List<MealPicSelVo> selMealPicByImealList(List<Integer> imealList);//일지 리스트2
    List<MealTagSelVo> selMealTagByImealList(List<Integer> imealList);//일지 리스트3
    MealSelDetailVo selDetail(int imeal);//일지 상세 정보1
    List<String> selMealPics(int imeal);//일지 상세 정보2
    List<String> selMealTags(int imeal);//일지 상세 정보3
    int updMeal(MealUpdDto dto);//일지 수정(사진, 태그 제외)
    int updMealPic(MealPicUpdDto dto);//일지 사진 수정
    int updMealTag(MealTagUpdDto dto);//일지 태그 수정
    int delMealPicByImeal(int imeal);//일지삭제1
    int delMealTagByImeal(int imeal);//일지삭제2
    int delMeal(int imeal);//일지삭제3
    int delMealPic(int ipic);//일지 사진 삭제
    int delMealTag(int itag);//일지 태그 삭제


}
