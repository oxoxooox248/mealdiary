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
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MealService {
    private final MealMapper mapper;

    //일지 리스트(검색, 북마크 포함)
    public List<MealSelVo> getMeal(MealSelDto dto) {
        List<MealSelVo> mealList = mapper.selMeal(dto);//일지 리스트1 (1,2,3,4)
        if (!mealList.isEmpty()) {
            List<Integer> imealList = new ArrayList();//일지pk 리스트 생성
            Map<Integer, MealSelVo> mealMap = new HashMap();//<일지pk, 일지 객체> 맵 생성
            for (MealSelVo vo : mealList) {
                imealList.add(vo.getImeal());//한 페이지의 일지pk를 imealList에 add
                mealMap.put(vo.getImeal(), vo);//맵에 <key, value>: <일지pk, 일지 객체 주소값>을 put
                vo.setPics(new ArrayList());//일지 객체의 pics에 사진 주소값 넣을 리스트 생성
                vo.setTags(new ArrayList());//일지 객체의 tags에 태그 넣을 리스트 생성
            }//imealList: 1,2,3,4
            //mealMap: (1,vo), (2,vo), (3,vo), (4,vo)
            //반복문 돌면서 vo안에 있는 pics랑 tags에 새로운 리스트를 만든다

            List<MealPicSelVo> picList = mapper.selMealPicByImealList(imealList);
            //만약 1번 일지에 사진이 3장 있으면  (1,사진1),(1,사진2),(1,사진3)이 있는 MealPicSelVo 객체 3개
            //만약 2번 일지에 사진이 1장만 있으면 (2,사진1)이 있는 MealPicSelVo 객체 1개
            //만약 3번 일지에 사진이 2장 있으면 (3,사진1), (3,사진2)이 있는 MealPicSelVo 객체 2개
            //만약 4번 일지에 사진이 1장만 있으면 (4,사진1)이 있는 MealPicSelVo 객체 1개
            for (MealPicSelVo vo : picList) {
                mealMap.get(vo.getImeal()).getPics().add(vo.getPic());
            }//MealPicSelVo에 있는 사진들을 MealSelVo에 있는 pics(List<String>)에 추가한다
            List<MealTagSelVo> tagList = mapper.selMealTagByImealList(imealList);
            //위의 사진들을 MealSelVo에 있는 pics(List<String>)에 넣는 방법과 동일하지만
            //일지의 태그는 0개일 수 있다는 차이가 있다
            for (MealTagSelVo vo : tagList) {
                mealMap.get(vo.getImeal()).getTags().add(vo.getTag());
            }
        }
        return mealList;//사진과 태그 담는 것까지 완료한 mealList
    }

    //일지 수정(사진, 태그 제외)
    public ResVo putMeal(MealUpdDto dto) {
        Integer check = mapper.selMealByImeal(dto.getImeal());
        if (check == null) {
            return new ResVo(Const.NO_EXIST);
        }
        int affectedRow = mapper.updMeal(dto);
        if (affectedRow == Const.ZERO) {
            return new ResVo(Const.ZERO);
        }
        if (dto.getPics() != null && !dto.getPics().isEmpty()) {
            List<Integer> ipicList = mapper.selIpics(dto.getImeal());
            //ipicList= {10, 15, 39}
            List<MealPicUpdDto> picDtoList = new ArrayList();
            for (int i = 0; i < dto.getPics().size(); i++) {
                MealPicUpdDto pDto = new MealPicUpdDto();
                int ipic = ipicList.get(dto.getPicIdx().get(i));
                pDto.setIpic(ipic);
                pDto.setPic(dto.getPics().get(i));
                picDtoList.add(pDto);
            }
            for (MealPicUpdDto pDto : picDtoList) {
                mapper.updMealPics(pDto);
            }
        }
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            List<Integer> itagList = mapper.selItags(dto.getImeal());
            List<MealTagUpdDto> tagDtoList = new ArrayList();
            for (int i = 0; i < dto.getTags().size(); i++) {
                MealTagUpdDto tDto = new MealTagUpdDto();
                int itag = itagList.get(dto.getTagIdx().get(i));
                tDto.setItag(itag);
                tDto.setTag(dto.getTags().get(i));
                tagDtoList.add(tDto);
            }
            for (MealTagUpdDto tDto : tagDtoList) {
                mapper.updMealTags(tDto);
            }
        }
        return new ResVo(Const.SUCCESS);
    }

    //일지 작성
    public ResVo postMeal(MealInsDto dto) {
        if (dto.getPics() == null) {//등록된 사진이 없다
            return new ResVo(Const.NO_PIC);
        } else {
            if (Utils.picListCheck(dto.getPics())) {
                return new ResVo(Const.ABNORMAL_PIC_FORM);
            }//사진이 빈 칸이면 안 된다

            if (dto.getPics().size() > Const.PIC_MAX) {//등록된 사진이 최대 갯수를 초과했다
                return new ResVo(Const.MANY_PIC);
            }
        }
        if (dto.getTags() != null) {
            if (dto.getTags().size() > Const.TAG_MAX) {
                return new ResVo(Const.MANY_TAG);
            }//등록된 태그가 최대 갯수를 초과했다
            if (Utils.tagListCheck(dto.getTags())) {
                return new ResVo(Const.ABNORMAL_TAG_FORM);
            }//태그에 빈 칸이거나 띄어쓰기 혹은 특수문자가 들어가면 안 된다
        }
        if (dto.getTitle() == null
                || dto.getIngredient() == null
                || dto.getRecipe() == null
                || Utils.isEmpty(dto.getTitle())
                || Utils.isEmpty(dto.getIngredient())
                || Utils.isEmpty(dto.getRecipe())) {
            return new ResVo(Const.CANT_NULL);//제목, 재료, 레시피는 반드시 입력 받아야 한다
        }
        int affectedMeal = mapper.insMeal(dto);//일지 등록 실행
        if (affectedMeal == Const.ZERO) {
            return new ResVo(Const.ZERO);
        }//일지 등록이 안 됐을 때
        int affectedMealPic = mapper.insMealPics(dto);//일지 사진 등록 실행
        if (affectedMealPic != dto.getPics().size()) {
            return new ResVo(Const.PIC_FAIL);
        }//일지 사진 등록이 제대로 안 됐을 때
        if (dto.getTags() == null) {
            return new ResVo(Const.SUCCESS);
        }//태그가 없을 때
        //태그는 없어도 된다
        int affectedMealTag = mapper.insMealTags(dto);//일지 태그 등록 실행
        if (affectedMealTag != dto.getTags().size()) {
            return new ResVo(Const.TAG_FAIL);
        }//일지 태그 등록이 제대로 안 됐을 때
        return new ResVo(Const.SUCCESS);//일지 작성의 모든 과정 완료
    }

    //일지 삭제(사진과 태그까지 동시에 전부 삭제)
    public ResVo delMeal(int imeal) {
        Integer targetImeal = mapper.selMealByImeal(imeal);//해당 일지가 있는지 확인
        if (targetImeal == null) {
            return new ResVo(Const.NO_EXIST);
        }//없으면 바로 리턴
        mapper.delMealPicByImeal(imeal);//해당 일지의 사진들 삭제
        mapper.delMealTagByImeal(imeal);//해당 일지의 태그들 삭제
        return new ResVo(mapper.delMeal(imeal));//해당 일지 삭제(0: 실행 안됨, 1: 실행 완료)
    }

    //일지 태그 추가
    public ResVo postMealTag(MealTagInsDto dto) {
        Integer check = mapper.selMealByImeal(dto.getImeal());
        if (check == null) {
            return new ResVo(Const.NO_EXIST);
        }
        if (dto.getTag() == null
                || Utils.isEmpty(dto.getTag())) {
            return new ResVo(Const.CANT_NULL);
            //태그 추가할 때 null이거나 빈 칸일 때는 수정하면 안된다.
        } else if (mapper.selMealTags(dto.getImeal()).size() >= Const.TAG_MAX) {
            //해당 일지의 태그가 최대 갯수(5)만큼 있을 경우 추가 불가
            return new ResVo(Const.MANY_TAG);
        } else if (!Utils.formCheck(dto.getTag())) {
            //태그에 띄어쓰기나 특수문자가 들어가면 안 된다
            return new ResVo(Const.ABNORMAL_TAG_FORM);
        }
        return new ResVo(mapper.insMealTag(dto));//태그 추가 실행
    }

    //일지 태그 삭제
    public ResVo delMealTag(int itag) {
        return new ResVo(mapper.delMealTag(itag));
    }

    //일지 사진 추가
    public ResVo postMealPic(MealPicInsDto dto) {
        Integer targetImeal = mapper.selMealByImeal(dto.getImeal());//해당 일지가 있는지 확인
        if (targetImeal == null
                || dto.getPic() == null
                || Utils.isEmpty(dto.getPic())) {
            //해당 일지가 존재하지 않거나 입력받은 사진이 null이거나 빈 칸일 때
            return new ResVo(Const.NO_EXIST);
        } else if (mapper.selMealPics(dto.getImeal()).size() >= Const.PIC_MAX) {
            //해당 일지의 사진이 최대 갯수(3)만큼 있을 경우 추가 불가
            return new ResVo(Const.MANY_PIC);
        }
        return new ResVo(mapper.insMealPic(dto));//사진 추가 실행
    }

    //일지 사진 삭제
    public ResVo delMealPic(MealPicDelDto dto) {
        if (dto.getImeal() == Const.ZERO
                || mapper.selMealPics(dto.getImeal()).size() <= Const.PIC_MIN) {
            return new ResVo(Const.FAIL);
        }
        //해당 일지가 존재하지 않거나 사진이 최소 갯수(1)만큼 있을 경우 삭제 불가
        return new ResVo(mapper.delMealPic(dto.getIpic()));//사진 삭제 실행
    }

    //북마크 온오프
    public ResVo toggleBookmark(int imeal) {
        Integer check = mapper.selMealByImeal(imeal);
        if (check == null) {
            return new ResVo(Const.NO_EXIST);
        }
        MealBookmarkDto dto = new MealBookmarkDto();
        dto.setImeal(imeal);
        mapper.toggleBookmark(dto);
        if (dto.getBookmark() == Const.BOOKMARK_ON) {
            return new ResVo(Const.BOOKMARK_OFF);
        }
        return new ResVo(Const.BOOKMARK_ON);
    }

    //일지 상세 정보
    public MealSelDetailVo getDetail(int imeal) {
        MealSelDetailVo vo = mapper.selDetail(imeal);//해당 일지의 상세 정보(재료, 레시피 포함)
        if (vo == null) {
            return new MealSelDetailVo();
        }//비어있는 객체 리턴
        List<String> picList = mapper.selMealPics(imeal);
        List<String> tagList = mapper.selMealTags(imeal);
        vo.setPics(picList);
        vo.setTags(tagList);
        return vo;
    }
}
