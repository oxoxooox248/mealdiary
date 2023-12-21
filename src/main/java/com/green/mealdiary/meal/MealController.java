package com.green.mealdiary.meal;

import com.green.mealdiary.common.*;
import com.green.mealdiary.meal.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(summary ="일지 리스트", description = "일지 리스트 처리" +
            "<br>page: 페이지(디폴트 값=1),row_count: 페이지 당 일지 갯수(디폴트 값=4)," +
            "<br>bookmark: 북마크 여부(0:모든 일지, 1:북마크 있는 일지만)(디폴트 값=0), " +
            "<br>search: 검색어(제목, 태그(#으로 시작))" +
            "<br>(imeal: 일지pk, title: 제목(음식이름), review: 후기," +
            "<br>createdAt: 일지 작성일자, pics: 해당 일지의 사진들," +
            "<br>tags: 해당 일지의 태그들, result(-1): 비정상적인 검색어," +
            "<br>(0): page, row_count, bookmark 중 하나 이상이 비정상적입니다.(1): 정상)")
    public List<MealSelVo> getMealList(@RequestParam(required = false, defaultValue = "1") int page,
                                       @RequestParam(name="row_count", required = false,
                                               defaultValue = "4") int rowCount,
                                       @RequestParam(required = false, defaultValue = "0") int bookmark,
                                       @RequestParam(required = false) String search) {
        MealSelDto dto = new MealSelDto();

        dto.setRowCount(rowCount);//페이지 당 일지 갯수
        dto.setPage(page);//페이지
        dto.setBookmark(bookmark);//북마크 검색(0:모든 일지, 1: 책갈피 설정한 일지만)
        if (search!=null
                &&!Utils.isEmpty(search)){//검색어를 받았을 때
            if(dto.getSearch().length()>Const.SEARCH_MAX){//검색어가 10자를 넘어가면
                return Utils.abnormalSearchForm();
            }
            dto.setSearch(search);
        }
        return service.getMeal(dto);
    }
    @PutMapping
    @Operation(summary = "일지 수정", description = "일지 수정 처리" +
            "<br>imeal: 일지pk, title: 제목(음식 이름), ingredient: 재료, recipe: 레시피, review: 후기" +
            "<br>picIdx: 사진 인덱스번호(List), pics: 바꾸고 싶은 사진(List)," +
            "<br>tagIdx: 태그 인덱스번호(List), tags: 바꾸고 싶은 태그(List)" +
            "<br>(result(-4): 입력받은 제목, 재료, 타이틀이 없습니다, (0): 실패, (1): 성공)")
    public ResVo putMeal(@RequestBody MealUpdDto dto) {
            return service.putMeal(dto);
    }
    @PostMapping
    @Operation(summary = "일지 작성", description = "일지 작성 처리" +
            "<br>title: 제목(음식이름), ingredient: 식재료, recipe: 레시피, review: 후기," +
            "<br>pics(리스트): 일지 사진, tags(리스트): 일지 태그" +
            "<br>(result(1): 성공, (0): 실패, (-1): 사진 없음, (-2): 사진 갯수 초과," +
            "<br>(-3): 태그 갯수 초과, (-4): 입력받은 제목, 재료, 타이틀이 없습니다," +
            "<br>(-5): 태그에 띄워쓰기와 특수문자가 있습니다, (-6): 비정상적인 사진 등록" +
            "<br>(-7): 비정상적인 태그 등록)")
    public ResVo postMeal(@RequestBody MealInsDto dto){
        return service.postMeal(dto);
    }
    @DeleteMapping
    @Operation(summary = "일지 삭제", description = "일지 삭제 처리" +
            "<br>imeal: 일지pk" +
            "<br>(result(-1): 해당 일지는 없는 일지입니다, (0): 실패, (1): 성공)")
    public ResVo delMeal(int imeal) {
        return service.delMeal(imeal);
    }
    @PostMapping("/tag")
    @Operation(summary = "일지 태그 추가", description = "일지 태그 추가 처리" +
            "<br>imeal: 일지pk, tag: 추가할 태그 내용" +
            "<br>(result(0): 실패, (1): 성공, (-3): 태그 갯수 초과," +
            "<br>(-4): 입력된 태그가 없습니다, (-5): 태그에 띄워쓰기와 특수문자가 있습니다.)")
    public ResVo postMealTag(@RequestBody MealTagInsDto dto){
        return service.postMealTag(dto);
    }
    @DeleteMapping("/tag")
    @Operation(summary = "일지 태그 삭제", description = "일지 태그 삭제 처리" +
            "<br>itag: 태그pk" +
            "<br>(result(0): 실패(태그가 없을 때), (1): 성공)")
    public ResVo delMealTag(int itag){
        return service.delMealTag(itag);
    }
    @PostMapping("/pic")
    @Operation(summary = "일지 사진 추가", description = "일지 사진 추가 처리" +
            "<br>imeal: 일지pk, pic: 추가할 사진 주소" +
            "<br>(result(0): 실패, (1): 성공, (-1): 해당 일지는 없는 일지 or 입력받은 사진이 존재하지 않음, " +
            "<br>(-2): 사진 갯수 초과로 더 추가 불가)")
    public ResVo postMealPic(@RequestBody MealPicInsDto dto){
        return service.postMealPic(dto);
    }
    @DeleteMapping("/pic")
    @Operation(summary = "일지 사진 삭제", description = "일지 사진 삭제 처리" +
            "<br>imeal: 일지pk, ipic: 사진pk" +
            "<br>(result(0): 실패, (1): 성공, (-1): 사진이 한 장이라서 삭제 불가, " +
            "<br>해당 일지는 없는 일지 입니다)")
    public ResVo delMealPic(MealPicDelDto dto){
        return service.delMealPic(dto);
    }
    @PostMapping("/bookmark")
    @Operation(summary = "책갈피 표시/해제", description = "북마크 on/off 토글로 처리" +
            "<br>imeal: 일지pk" +
            "<br>(result(-1): 해당 일지는 없는 일지입니다, (0): 해제, (1): 표시)")
    public ResVo toggleBookmark(int imeal){
        return service.toggleBookmark(imeal);
    }
    @GetMapping("/{imeal}")
    @Operation(summary ="일지의 상세 정보", description = "일지 상세 정보 처리" +
            "<br>imeal: 일지 pk" +
            "<br>(해당 일지가 없는 경우는 null- 제대로 imeal값이 넘어오지 않았을 때" +
            "<br>imeal: 일지 pk, title: 제목, ingredient: 재료, recipe: 레시피" +
            "<br>bookmark: 북마크 여부, createdAt: 작성일자, pics: 일지의 사진들(List: 1~3장)," +
            "<br>tags: 일지의 태그들(List: 0~5개))")
    public MealSelDetailVo getDetail(@PathVariable int imeal){
        return service.getDetail(imeal);
    }
}
