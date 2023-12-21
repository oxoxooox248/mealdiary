package com.green.mealdiary.user;

import com.green.mealdiary.common.Const;
import com.green.mealdiary.common.ResVo;
import com.green.mealdiary.user.model.UserSigninDto;
import com.green.mealdiary.user.model.UserSignupDto;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    public ResVo signup(UserSignupDto dto){
        String hashedPw= BCrypt.hashpw(dto.getUpw(),BCrypt.gensalt());
        dto.setHashedPw(hashedPw);
        return new ResVo(mapper.insUser(dto));
    }
    public ResVo signin(UserSigninDto dto){
        String upw = mapper.selUserByUid(dto.getUid());
        if(upw==null){
            return new ResVo(Const.LOGIN_NO_ID);
        } else if(!BCrypt.checkpw(dto.getUpw(), upw)){
            return new ResVo(Const.LOGIN_DIFF_PW);
        }
        return new ResVo(Const.SUCCESS);
    }
}
