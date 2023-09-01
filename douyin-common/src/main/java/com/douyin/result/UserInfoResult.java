package com.douyin.result;

import com.douyin.vo.UserInfoVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoResult extends Result {
    private UserInfoVO user;

    public static UserInfoResult success(UserInfoVO userInfoVO) {
        UserInfoResult userInfoResult = new UserInfoResult();
        userInfoResult.setStatusCode(0);
        userInfoResult.user = userInfoVO;
        return userInfoResult;
    }
}
