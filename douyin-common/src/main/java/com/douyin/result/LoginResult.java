package com.douyin.result;

import com.douyin.vo.LoginVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResult extends Result {

    private Integer userId;
    private String token;

    public static LoginResult success(LoginVO loginVO) {
        LoginResult resultOfLOR = new LoginResult();
        resultOfLOR.userId = loginVO.getUserId();
        resultOfLOR.token = loginVO.getToken();
        resultOfLOR.setStatusCode(0);
        return resultOfLOR;
    }
}
