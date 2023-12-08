package com.green.mealdiary.meal;

import com.green.mealdiary.common.*;
import com.green.mealdiary.meal.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MealService{

    private final MealMapper mapper;

    public ResVo postMeal(MealInsDto dto){
        mapper.insMeal(dto);
        mapper.insMealPic(dto);
        mapper.insMealTag(dto);
        return new ResVo(Const.SUCCESS);
    }
    public List<MealSelVo> getMeal(MealSelDto dto){
        List<MealSelVo> mealList= mapper.selMeal(dto);
        List<Integer> imealList= new ArrayList();
        Map<Integer, MealSelVo> mealMap= new HashMap();
        for(MealSelVo vo: mealList){
            imealList.add(vo.getImeal());
            mealMap.put(vo.getImeal(), vo);
            vo.setPics(new ArrayList());
            vo.setTags(new ArrayList());
        }
        List<MealPicSelVo> picList= mapper.selMealPicByImealList(imealList);
        for(MealPicSelVo vo: picList){
            mealMap.get(vo.getImeal()).getPics().add(vo.getPic());
        }
        List<MealTagSelVo> tagList= mapper.selMealTagByImealList(imealList);
        for(MealTagSelVo vo: tagList){
            mealMap.get(vo.getImeal()).getTags().add(vo.getTag());
        }
        return mealList;
    }
    public MealSelDetailVo getDetail(int imeal){
        MealSelDetailVo vo= mapper.selDetail(imeal);
        List<String> picList= mapper.selMealPics(imeal);
        List<String> tagList= mapper.selMealTags(imeal);
        vo.setPics(picList);
        vo.setTags(tagList);
        return vo;
    }
    public ResVo toggleBookmark(int imeal){
        int bookmark= mapper.selBookmark(imeal);
        if(bookmark==0){
            mapper.bookmarkOn(imeal);
            return new ResVo(1);
        }
        else {
            mapper.bookmarkOff(imeal);
            return new ResVo(0);
        }
    }
    public ResVo delMealPic(int ipic){
        return new ResVo(mapper.delMealPic(ipic));
    }
    public ResVo delMealTag(int itag){
        return new ResVo(mapper.delMealTag(itag));
    }
    public ResVo delMeal(int imeal){
        mapper.delMealPicByImeal(imeal);
        mapper.delMealTagByImeal(imeal);
        mapper.delMeal(imeal);
        return new ResVo(1);
    }
    public ResVo putMeal(MealUpdDto dto){
        mapper.updMeal(dto);

        return new ResVo(1);
    }
    public ResVo updMealPic(MealPicUpdDto dto){
        return new ResVo(mapper.updMealPic(dto));
    }
    public ResVo updMealTag(MealTagUpdDto dto){
        return new ResVo(mapper.updMealTag(dto));
    }

}
