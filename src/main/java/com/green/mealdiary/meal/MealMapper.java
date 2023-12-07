package com.green.mealdiary.meal;

import com.green.mealdiary.meal.model.MealInsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MealMapper {
    int insMeal(MealInsDto dto);
    int insMealPic(MealInsDto dto);
    int insMealTag(MealInsDto dto);
}
