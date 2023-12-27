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
        MealInsDto iDto= new MealInsDto();
        iDto.setImeal(dto.getImeal());
        if (dto.getPics() != null && !dto.getPics().isEmpty()) {
            iDto.setPics(dto.getPics());
            int affectedDelPic= mapper.delMealPicByImeal(dto.getImeal());
            int affectedInsPic= mapper.insMealPics(iDto);
        }
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            iDto.setTags(dto.getTags());
            int affectedDelTag= mapper.delMealPicByImeal(dto.getImeal());
            int affectedInsTag= mapper.insMealPics(iDto);
        }
        return new ResVo(Const.SUCCESS);
    }

    //일지 작성
    public ResVo postMeal(MealInsDto dto) {
        int affectedMeal = mapper.insMeal(dto);//일지 등록 실행

        if (affectedMeal == Const.ZERO) {
            return new ResVo(Const.ZERO);
        }
        //일지 등록이 안 됐을 때

        int affectedMealPic = mapper.insMealPics(dto);
        //일지 사진 등록 실행

        if (affectedMealPic != dto.getPics().size()) {
            return new ResVo(Const.ZERO);
        }
        //일지 사진 등록이 제대로 안 됐을 때

        if (dto.getTags() == null) {
            return new ResVo(Const.SUCCESS);
        }//태그가 없을 때(태그는 없어도 된다)

        int affectedMealTag = mapper.insMealTags(dto);
        //일지 태그 등록 실행

        if (affectedMealTag != dto.getTags().size()) {
            return new ResVo(Const.ZERO);
        }//일지 태그 등록이 제대로 안 됐을 때

        return new ResVo(Const.SUCCESS);//일지 작성의 모든 과정 완료
    }

    //일지 삭제(사진과 태그까지 동시에 전부 삭제)
    public ResVo delMeal(int imeal) {
        Integer targetImeal = mapper.selMealByImeal(imeal);
        //해당 일지가 있는지 확인
        if (targetImeal == null) {
            return new ResVo(Const.NO_EXIST);
        }//없으면 바로 리턴

        int picCnt = mapper.picCntByImeal(imeal);//해당 일지의 사진 개수 확인
        int delPicCnt = mapper.delMealPicByImeal(imeal);//해당 일지의 사진들 삭제

        if (picCnt != delPicCnt) {
            return new ResVo(Const.ZERO);
        }
        //원래 사진 개수랑 삭제된 사진 개수가 불일치

        int tagCnt = mapper.tagCntByImeal(imeal);//해당 일지의 태그 개수 확인
        int delTagCnt = mapper.delMealTagByImeal(imeal);//해당 일지의 태그들 삭제

        if (tagCnt != delTagCnt) {
            return new ResVo(Const.ZERO);
        }
        //원래 태그 개수랑 삭제된 태그 개수가 불일치

        return new ResVo(mapper.delMeal(imeal));
        //해당 일지 삭제(0: 실행 안됨, 1: 실행 완료)
    }

    //일지 태그 추가
    public ResVo postMealTag(MealTagInsDto dto) {
        Integer targetImeal = mapper.selMealByImeal(dto.getImeal());
        //해당 일지가 있는지 확인
        if (targetImeal == null) {
            return new ResVo(Const.NO_EXIST);//없으면 바로 리턴
        } else if (mapper.selMealTags(dto.getImeal()).size() >= Const.TAG_MAX) {
            return new ResVo(Const.BAD_REQUEST);
        }//해당 일지의 태그가 최대 갯수(5)만큼 있을 경우 추가 불가

        return new ResVo(mapper.insMealTag(dto));//태그 추가 실행
    }

    //일지 태그 삭제
    public ResVo delMealTag(int itag) {
        return new ResVo(mapper.delMealTag(itag));
    }

    //일지 사진 추가
    public ResVo postMealPic(MealPicInsDto dto) {
        Integer targetImeal = mapper.selMealByImeal(dto.getImeal());//해당 일지가 있는지 확인
        if (targetImeal == null) {//해당 일지가 존재하지 않을 때
            return new ResVo(Const.NO_EXIST);

        } else if (mapper.selMealPics(dto.getImeal()).size() >= Const.PIC_MAX) {
            //해당 일지의 사진이 최대 갯수(3)만큼 있을 경우 추가 불가
            return new ResVo(Const.BAD_REQUEST);
        }
        return new ResVo(mapper.insMealPic(dto));//사진 추가 실행
    }

    //일지 사진 삭제
    public ResVo delMealPic(MealPicDelDto dto) {
        if (mapper.selMealPics(dto.getImeal()).size() <= Const.PIC_MIN) {
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
            MealSelDetailVo fail = new MealSelDetailVo();
            fail.setResult(Const.NO_EXIST);
            return fail;//비어있는 객체 리턴(result= -1)
        }
        vo.setPics(mapper.selMealPics(imeal));
        vo.setTags(mapper.selMealTags(imeal));
        return vo;
    }
}
