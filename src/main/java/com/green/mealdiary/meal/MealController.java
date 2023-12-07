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
@Tag(name = "오늘 뭐 먹조", description = "식사 일지")
public class MealController {
    private final MealService service;

    @PostMapping("/api/meal")
    @Operation(summary = "식사 일지 등록", description = "식사 일지 등록 처리<br>" +
            "(title: 제목(음식이름), ingredient: 식재료, recipe: 레시피, review: 후기, " +
            "pics(리스트): 일지 사진, tags(리스트): 일지 태그)<br>(result(1):성공, result(0):실패)")
    public ResVo postMeal(@RequestBody MealInsDto dto){
        return service.postMeal(dto);
    }
    @PostMapping("/api/meal/search")
    @Operation(summary = "일지 검색", description = "일지 검색 처리<br>(search: 검색내용)")
    public List<MealSelVo> searchMeal(String search){
        return null;
    }
    @GetMapping("/api/meal")
    @Operation(summary ="첫 화면", description = "첫 메인 화면 처리<br>(page: 페이지)")
    public List<MealSelVo> getMeal(int page){
        return null;
    }
    @GetMapping("/api/meal2")
    @Operation(summary ="페이징 처리", description = "메인 화면과 다른 페이징 처리<br>(page: 페이지)")
    public List<MealSelVo> getMeal2(int page){
        return null;
    }
    @GetMapping("/api/meal/book_mark")
    @Operation(summary ="책갈피 검색", description = "책갈피를 등록해놓은 일지 검색")
    public List<MealSelVo> getMealByBookMark(){
        return null;
    }
    @GetMapping("/api/meal/more")
    @Operation(summary ="일지의 상세 정보 페이지", description = "일지 클릭 시 나오는 상세 페이지 처리<br>(imeal: 일지 pk)")
    public MealSelMoreVo getMealMore(int imeal){
        return null;
    }
}