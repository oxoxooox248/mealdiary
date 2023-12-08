package com.green.mealdiary.meal;

import com.green.mealdiary.common.*;
import com.green.mealdiary.meal.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meal")
@Tag(name = "오늘 뭐 먹조", description = "식사 일지")
public class MealController {
    private final MealService service;

    @PostMapping
    @Operation(summary = "식사 일지 등록", description = "식사 일지 등록 처리<br>" +
            "(title: 제목(음식이름), ingredient: 식재료, recipe: 레시피, review: 후기, " +
            "pics(리스트): 일지 사진, tags(리스트): 일지 태그)<br>(result(1):성공, result(0):실패)")
    public ResVo postMeal(@RequestBody MealInsDto dto){
        return service.postMeal(dto);
    }

    @PostMapping("/search")
    @Operation(summary = "일지 검색", description = "일지 검색 처리<br>(search: 검색내용)<br>(result(0): 실패, (1): 성공)")
    public List<MealSelVo> searchMeal(String search){
        return null;
    }
    @PostMapping("/pic")
    @Operation(summary = "일지 사진 추가", description = "일지 사진 추가 처리<br>(imeal: 일지pk, pic:추가할 사진)<br>(result(0): 실패, (1): 성공)")
    public ResVo postMealPic(@RequestBody MealPicInsDto dto){
        return service.postMealPic(dto);
    }
    @PostMapping("/tag")
    @Operation(summary = "일지 태그 추가", description = "일지 태그 추가 처리<br>(imeal: 일지pk, pic:추가할 태그)<br>(result(0): 실패, (1): 성공)")
    public ResVo postMealTag(@RequestBody MealTagInsDto dto){
        return service.postMealTag(dto);
    }

    @GetMapping
    @Operation(summary ="첫 화면", description = "첫 메인 화면 처리<br>(page: 페이지,rowCount: 페이지 당 일지 갯수, bookmark: 북마크 여부(0:없음, 1:있음), search: 검색어(제목, 태그))")
    public List<MealSelVo> getMealList(MealSelDto dto){
        return service.getMeal(dto);
    }

    @GetMapping("/{imeal}")
    @Operation(summary ="일지의 상세 정보 페이지", description = "일지 클릭 시 나오는 상세 페이지 처리<br>(imeal: 일지 pk)")
    public MealSelDetailVo getDetail(@PathVariable int imeal){
        return service.getDetail(imeal);
    }

    @PutMapping
    @Operation(summary = "일지 수정", description = "일지 수정 처리<br>(title: 제목(음식 이름), ingredient: 재료, " +
            "recipe: 레시피, review: 후기, pics: 사진, tags: 태그)<br>(result(0): 실패, (1): 성공)")
    public ResVo putMeal(@RequestBody MealUpdDto dto) { return service.putMeal(dto);}

    @DeleteMapping
    @Operation(summary = "일지 삭제", description = "일지 삭제 처리<br>(imeal: 일지pk)<br>(result(0): 실패, (1): 성공)")
    public ResVo delMeal(int imeal) { return null;}

    @DeleteMapping("/pic")
    @Operation(summary = "일지 사진 삭제", description = "일지 사진 삭제 처리<br>(ipic: 사진pk)<br>(result(0): 실패, (1): 성공)")
    public ResVo delMealPic(int ipic) {return service.delMealPic(ipic);}

    @PostMapping("/bookmark")
    @Operation(summary = "책갈피 표시/해제", description = "북마크 on/off 토글로 처리(imeal: 일지pk)<br>(result(0): 취소, (1): 표시)")
    public ResVo toggleBookmark(int imeal) {return service.toggleBookmark(imeal);}

    @DeleteMapping("/tag")
    @Operation(summary = "일지 태그 삭제", description = "일지 태그 삭제 처리<br>(itag: 태그pk)<br>(result(0): 실패, (1): 성공)")
    public ResVo delMealTag(int itag) {return service.delMealTag(itag);}
    @PatchMapping("/pic")
    @Operation(summary = "일지 사진 수정", description = "일지 수정 처리<br>(ipic:사진pk, pic:수정할 사진)<br>(result(0): 실패, (1): 성공)")
    public ResVo updMealPic(@RequestBody MealPicUpdDto dto){
        return service.updMealPic(dto);
    }
    @PatchMapping("/tag")
    @Operation(summary = "일지 태그 수정", description = "일지 태그 수정 처리<br>(itag:태그pk, tag:수정할 태그)<br>(result(0): 실패, (1): 성공)")
    public ResVo updMealTag(@RequestBody MealTagUpdDto dto){
        return service.updMealTag(dto);
    }
}
