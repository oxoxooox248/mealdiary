package com.green.mealdiary.meal;

import com.green.mealdiary.meal.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MealMapper {
    int insMeal(MealInsDto dto);
    int insMealPic(MealInsDto dto);
    int insMealTag(MealInsDto dto);
    List<MealSelVo> selMeal(MealSelDto dto);
    List<MealPicSelVo> selMealPicByImealList(List<Integer> imealList);
    List<MealTagSelVo> selMealTagByImealList(List<Integer> imealList);
    MealSelDetailVo selDetail(int imeal);
    List<String> selMealPics(int imeal);
    List<String> selMealTags(int imeal);
    int delMeal(int imeal);
    int delMealPicByImeal(int imeal);
    int delMealTagByImeal(int imeal);
    int selBookmark(int imeal);
    int bookmarkOn(int imeal);
    int bookmarkOff(int imeal);

}
