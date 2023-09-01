package com.douyin.result;

import com.douyin.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UserInfoListResult extends Result {
    private List<UserInfoVO> userList;

    public static UserInfoListResult success(List<UserInfoVO> userList) {
        UserInfoListResult userInfoListResult = new UserInfoListResult(userList);
        userInfoListResult.setStatusCode(0);
        return userInfoListResult;
    }
}
