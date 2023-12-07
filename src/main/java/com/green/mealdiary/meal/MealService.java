package com.green.mealdiary.meal;

import com.green.mealdiary.common.*;
import com.green.mealdiary.meal.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealMapper mapper;

    public ResVo postMeal(MealInsDto dto){
        mapper.insMeal(dto);
        mapper.insMealPic(dto);
        mapper.insMealTag(dto);
        return new ResVo(Const.SUCCESS);
    }
    public List<MealSelVo> getMeal(int page){
        return null;
    }

}
