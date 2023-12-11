package com.green.mealdiary.meal;

import com.green.mealdiary.common.*;
import com.green.mealdiary.meal.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class MealService{

    private final MealMapper mapper;
    //일지 작성
    public ResVo postMeal(MealInsDto dto){
        mapper.insMeal(dto);
        mapper.insMealPics(dto);
        mapper.insMealTags(dto);
        return new ResVo(Const.SUCCESS);
    }
    //일지 사진 추가
    public ResVo postMealPic(MealPicInsDto dto){
        return new ResVo(mapper.insMealPic(dto));
    }
    //일지 태그 추가
    public ResVo postMealTag(MealTagInsDto dto){
        return new ResVo(mapper.insMealTag(dto));
    }
    //북마크 온오프
    public ResVo toggleBookmark(int imeal){
        int bookmark= mapper.selBookmark(imeal);
        if(bookmark==0){
            mapper.bookmarkOn(imeal);
            return new ResVo(Const.BOOKMARK_ON);
        }
        else {
            mapper.bookmarkOff(imeal);
            return new ResVo(Const.BOOKMARK_OFF);
        }
    }
    //일지 리스트(검색, 북마크 포함)
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
    //일지 상세 정보
    public MealSelDetailVo getDetail(int imeal){
        MealSelDetailVo vo= mapper.selDetail(imeal);
        List<String> picList= mapper.selMealPics(imeal);
        List<String> tagList= mapper.selMealTags(imeal);
        vo.setPics(picList);
        vo.setTags(tagList);
        return vo;
    }
    //일지 수정(사진, 태그 제외)
    public ResVo putMeal(MealUpdDto dto){
        mapper.updMeal(dto);
        return new ResVo(1);
    }
    //일지 사진 수정
    public ResVo updMealPic(MealPicUpdDto dto){
        return new ResVo(mapper.updMealPic(dto));
    }
    //일지 태그 수정
    public ResVo updMealTag(MealTagUpdDto dto){
        return new ResVo(mapper.updMealTag(dto));
    }
    //일지 사진 삭제
    public ResVo delMealPic(int ipic){
        return new ResVo(mapper.delMealPic(ipic));
    }
    //일지 태그 삭제
    public ResVo delMealTag(int itag){
        return new ResVo(mapper.delMealTag(itag));
    }
    //일지 삭제(사진과 태그까지 동시에 전부 삭제)
    public ResVo delMeal(int imeal){
        mapper.delMealPicByImeal(imeal);
        mapper.delMealTagByImeal(imeal);
        return new ResVo(mapper.delMeal(imeal));
    }




}
