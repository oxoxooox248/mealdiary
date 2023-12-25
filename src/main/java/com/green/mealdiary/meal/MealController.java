package com.green.mealdiary.meal;

import com.green.mealdiary.common.*;
import com.green.mealdiary.meal.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meal")
@Tag(name = "오늘 뭐 먹조", description = "식사 일지")
public class MealController {
    private final MealService service;

    @GetMapping
    @Operation(summary = "일지 리스트", description = "일지 리스트 처리" +
            "<br>page: 페이지(디폴트 값=1),row_count: 페이지 당 일지 갯수(디폴트 값=4)," +
            "<br>bookmark: 북마크 여부(0:모든 일지, 1:북마크 있는 일지만)(디폴트 값=0), " +
            "<br>search: 검색어(제목, 태그(#으로 시작))" +
            "<br>(imeal: 일지pk, title: 제목(음식이름), review: 후기," +
            "<br>createdAt: 일지 작성일자, pics: 해당 일지의 사진들," +
            "<br>tags: 해당 일지의 태그들, result(-1): Bad Request" +
            "<br>(1): 정상)")
    public List<MealSelVo> getMealList(@RequestParam(required = false, defaultValue = "1") int page,
                                       @RequestParam(name = "row_count", required = false,
                                               defaultValue = "4") int rowCount,
                                       @RequestParam(required = false, defaultValue = "0") int bookmark,
                                       @RequestParam(required = false) String search) {
        try{
            MealSelDto dto= Utils.getMealListCheck(rowCount, page, bookmark, search);

            if(dto==null) {return Utils.getMealListBadRequest();}

            return service.getMeal(dto);

        }catch(Exception e){
            return Utils.getMealListBadRequest();
        }

    }
    @PutMapping
    @Operation(summary = "일지 수정", description = "일지 수정 처리" +
            "<br>일지의 상세 정보들을 보내주시되 변경된 부분이 있으면 그 부분만 변경해서 보내주세요" +
            "<br>imeal: 일지pk, title: 제목(음식 이름), ingredient: 재료, recipe: 레시피, review: 후기" +
            "<br>picIdx: 사진 인덱스번호(List), pics: 바꾸고 싶은 사진(List)," +
            "<br>tagIdx: 태그 인덱스번호(List), tags: 바꾸고 싶은 태그(List)" +
            "<br>(result(-1): Bad Request, (0): 실패, (1): 성공)")
    public ResVo putMeal(@RequestBody @Nullable MealUpdDto dto) {
        try{
            ResVo vo= Utils.putMealCheck(dto);
            if(vo.getResult()!=Const.SUCCESS){return vo;}
            return service.putMeal(dto);
        }catch(Exception e){
            return new ResVo(Const.BAD_REQUEST);
        }

    }

    @PostMapping
    @Operation(summary = "일지 작성", description = "일지 작성 처리" +
            "<br>title: 제목(음식이름), ingredient: 식재료, recipe: 레시피, review: 후기," +
            "<br>pics(리스트): 일지 사진, tags(리스트): 일지 태그" +
            "<br>(result(-1): Bad Request, (0): 실패, (1): 성공)")
    public ResVo postMeal(@RequestBody @Nullable MealInsDto dto) {
        try{
            ResVo vo= Utils.postMealCheck(dto);
            //check
            if(vo.getResult()!=Const.SUCCESS){
                return vo;
            }//bad request

            return service.postMeal(dto);//실행
        }catch(Exception e){
            return new ResVo(Const.BAD_REQUEST);
        }

    }

    @DeleteMapping
    @Operation(summary = "일지 삭제", description = "일지 삭제 처리" +
            "<br>imeal: 일지pk" +
            "<br>(result(-1): Bad Request, (0): 실패, (1): 성공)")
    public ResVo delMeal(int imeal) {
        try{
            return service.delMeal(imeal);
        }catch(Exception e){
            return new ResVo(Const.BAD_REQUEST);
        }

    }

    @PostMapping("/tag")
    @Operation(summary = "일지 태그 추가", description = "일지 태그 추가 처리" +
            "<br>imeal: 일지pk, tag: 추가할 태그 내용" +
            "<br>(result(-1): Bad Request, (0): 실패, (1): 성공)")
    public ResVo postMealTag(@RequestBody @Nullable MealTagInsDto dto) {
        try{
            ResVo vo= Utils.postMealTagCheck(dto);
            //check

            if(vo.getResult()!=Const.SUCCESS){
                return vo;
            }//bad request

            return service.postMealTag(dto);//실행
        }catch(Exception e){
            return new ResVo(Const.BAD_REQUEST);
        }

    }

    @DeleteMapping("/tag")
    @Operation(summary = "일지 태그 삭제", description = "일지 태그 삭제 처리" +
            "<br>itag: 태그pk" +
            "<br>(result(0): 실패(태그가 없을 때), (1): 성공)")
    public ResVo delMealTag(int itag) {
        try{
            return service.delMealTag(itag);
        }catch(Exception e){
            return new ResVo(Const.BAD_REQUEST);
        }

    }

    @PostMapping("/pic")
    @Operation(summary = "일지 사진 추가", description = "일지 사진 추가 처리" +
            "<br>imeal: 일지pk, pic: 추가할 사진 주소" +
            "<br>(result(-1): Bad Request, (0): 실패, (1): 성공)")
    public ResVo postMealPic(@RequestBody @Nullable MealPicInsDto dto) {
        try{
            ResVo vo= Utils.postMealPicCheck(dto);
            //check

            if(vo.getResult()!=Const.SUCCESS){
                return vo;
            }//bad request

            return service.postMealPic(dto);//실행
        }catch(Exception e){
            return new ResVo(Const.BAD_REQUEST);
        }

    }

    @DeleteMapping("/pic")
    @Operation(summary = "일지 사진 삭제", description = "일지 사진 삭제 처리" +
            "<br>imeal: 일지pk, ipic: 사진pk" +
            "<br>(result(-1): Bad Request,(0): 실패, (1): 성공)")
    public ResVo delMealPic(MealPicDelDto dto) {
        try{
            ResVo vo= Utils.delMealPicCheck(dto);
            //check

            if(vo.getResult()!=Const.SUCCESS){
                return vo;
            }//bad request

            return service.delMealPic(dto);//실행
        }catch(Exception e){
            return new ResVo(Const.BAD_REQUEST);
        }

    }

    @PostMapping("/bookmark")
    @Operation(summary = "책갈피 표시/해제", description = "북마크 on/off 토글로 처리" +
            "<br>imeal: 일지pk" +
            "<br>(result(-1): 해당 일지는 없는 일지입니다, (0): 해제, (1): 표시)")
    public ResVo toggleBookmark(int imeal) {
        try{
            return service.toggleBookmark(imeal);
        }catch(Exception e){
            return new ResVo(Const.BAD_REQUEST);
        }

    }

    @GetMapping("/{imeal}")
    @Operation(summary = "일지의 상세 정보", description = "일지 상세 정보 처리" +
            "<br>imeal: 일지 pk" +
            "<br>(result(-1):Bad Request, (1): 정상" +
            "<br>imeal: 일지 pk, title: 제목, ingredient: 재료, recipe: 레시피" +
            "<br>bookmark: 북마크 여부, createdAt: 작성일자, pics: 일지의 사진들(List: 1~3장)," +
            "<br>tags: 일지의 태그들(List: 0~5개))")
    public MealSelDetailVo getDetail(@PathVariable(required = false) int imeal) {
        try{
            return service.getDetail(imeal);
        }catch(Exception e){
            MealSelDetailVo vo= new MealSelDetailVo();
            vo.setResult(Const.BAD_REQUEST);
            return vo;
        }
    }
}
