package com.green.mealdiary.user;

import com.green.mealdiary.common.ResVo;
import com.green.mealdiary.user.model.UserSigninDto;
import com.green.mealdiary.user.model.UserSignupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 API", description = "유저 정보 처리")
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    @Operation(summary ="회원가입", description = "회원가입 처리" +
            "<br>uid: 아이디, upw: 비밀번호, nm: 이름, pic: 프로필 사진" +
            "<br>(result(0): 실패, (1) 성공)" )
    public ResVo signup(@RequestBody @Valid UserSignupDto dto){
        return service.signup(dto);
    }
    @PostMapping("/signin")
    @Operation(summary ="로그인", description = "로그인 처리" +
            "<br>uid: 아이디, upw: 비밀번호" +
            "<br>(result(1): 로그인 성공, (2): 아이디 없음, (3): 비밀번호 틀림)" )
    public ResVo signin(@RequestBody @Valid UserSigninDto dto){
        return service.signin(dto);
    }
}
