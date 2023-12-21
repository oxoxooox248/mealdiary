package com.green.mealdiary.user;

import com.green.mealdiary.user.model.UserSignupDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserSignupDto dto);
    String selUserByUid(String uid);
}
