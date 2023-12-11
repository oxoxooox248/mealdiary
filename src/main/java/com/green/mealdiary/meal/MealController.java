package com.green.mealdiary.meal;

import com.green.mealdiary.common.*;
import com.green.mealdiary.meal.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meal")
@Tag(name = "오늘 뭐 먹조", description = "식사 일지")
public class MealController {
    private final MealService service;

    @GetMapping
    @Operation(summary ="첫 화면", description = "첫 메인 화면 처리<br>" +
            "(page: 페이지(디폴트 값=1),row_count: 페이지 당 일지 갯수(디폴트 값=4)," +
            "<br>bookmark: 북마크 여부(0:모든 일지, 1:북마크 있는 일지만)(디폴트 값=0), " +
            "<br>search: 검색어(제목, 태그(#으로 시작)))")
    public List<MealSelVo> getMealList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(name="row_count", required = false,
                                               defaultValue = "4") int rowCount,
                                       @RequestParam(required = false, defaultValue = "0") int bookmark,
                                       @RequestParam(required = false) String search){
        MealSelDto dto= new MealSelDto();
        dto.setPage(page);
        dto.setRowCount(rowCount);
        dto.setBookmark(bookmark);
        if(search!=null){
            dto.setSearch(search);
        }
        return service.getMeal(dto);
    }

    @PutMapping
    @Operation(summary = "일지 수정", description = "일지 수정 처리<br>" +
            "(title: 제목(음식 이름), ingredient: 재료, recipe: 레시피, review: 후기)" +
            "<br>(result(-1): 입력받은 제목, 재료, 타이틀이 없습니다, (0): 실패, (1): 성공)")
    public ResVo putMeal(@RequestBody MealUpdDto dto) { return service.putMeal(dto);}

    @PostMapping
    @Operation(summary = "일지 작성", description = "일지 작성 처리<br>" +
            "(title: 제목(음식이름), ingredient: 식재료, recipe: 레시피, review: 후기," +
            "<br>pics(리스트): 일지 사진, tags(리스트): 일지 태그)" +
            "<br>(result(1): 성공, (0): 실패, (-1): 사진 없음, (-2): 사진 갯수 초과, (-3): 태그 갯수 초과)")
    public ResVo postMeal(@RequestBody MealInsDto dto){
        return service.postMeal(dto);
    }

    @DeleteMapping
    @Operation(summary = "일지 삭제", description = "일지 삭제 처리" +
            "<br>(imeal: 일지pk)" +
            "<br>(result(0): 실패, (1): 성공)")
    public ResVo delMeal(int imeal) { return service.delMeal(imeal);}

    @PostMapping("/tag")
    @Operation(summary = "일지 태그 추가", description = "일지 태그 추가 처리" +
            "<br>(imeal: 일지pk, tag: 추가할 태그 내용)" +
            "<br>(result(0): 실패, (1): 성공, (-3): 태그 갯수 초과)")
    public ResVo postMealTag(@RequestBody MealTagInsDto dto){
        return service.postMealTag(dto);
    }

    @DeleteMapping("/tag")
    @Operation(summary = "일지 태그 삭제", description = "일지 태그 삭제 처리" +
            "<br>(itag: 태그pk)" +
            "<br>(result(0): 실패(태그가 없을 때), (1): 성공)")
    public ResVo delMealTag(int itag) {return service.delMealTag(itag);}

    @PatchMapping("/tag")
    @Operation(summary = "일지 태그 수정", description = "일지 태그 수정 처리" +
            "<br>(itag:태그pk, tag:수정할 태그)" +
            "<br>(result(0): 실패, (1): 성공)")
    public ResVo updMealTag(@RequestBody MealTagUpdDto dto){
        return service.updMealTag(dto);
    }

    @PostMapping("/pic")
    @Operation(summary = "일지 사진 추가", description = "일지 사진 추가 처리" +
            "<br>(imeal: 일지pk, pic: 추가할 사진 주소)" +
            "<br>(result(0): 실패, (1): 성공, (-2): 사진 갯수 초과)")
    public ResVo postMealPic(@RequestBody MealPicInsDto dto){
        return service.postMealPic(dto);
    }

    @DeleteMapping("/pic")
    @Operation(summary = "일지 사진 삭제", description = "일지 사진 삭제 처리" +
            "<br>(imeal: 일지pk, ipic: 사진pk)" +
            "<br>(result(0): 실패, (1): 성공, (-1): 사진이 한 장이라서 삭제 불가)")
    public ResVo delMealPic(MealPicDelDto dto) {return service.delMealPic(dto);}

    @PatchMapping("/pic")
    @Operation(summary = "일지 사진 수정", description = "일지 수정 처리" +
            "<br>(ipic: 사진pk, pic: 수정할 사진)" +
            "<br>(result(0): 실패, (1): 성공)")
    public ResVo updMealPic(@RequestBody MealPicUpdDto dto){
        return service.updMealPic(dto);
    }

    @PostMapping("/bookmark")
    @Operation(summary = "책갈피 표시/해제", description = "북마크 on/off 토글로 처리(imeal: 일지pk)" +
            "<br>(result(0): 해제, (1): 표시)")
    public ResVo toggleBookmark(int imeal) {return service.toggleBookmark(imeal);}

    @GetMapping("/{imeal}")
    @Operation(summary ="일지의 상세 정보 페이지", description = "일지 클릭 시 나오는 상세 페이지 처리" +
            "<br>(imeal: 일지 pk)")
    public MealSelDetailVo getDetail(@PathVariable int imeal){
        return service.getDetail(imeal);
    }
}
