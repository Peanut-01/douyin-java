package com.douyin.service;

import com.douyin.dto.LoginDTO;
import com.douyin.vo.LoginVO;
import com.douyin.vo.UserInfoVO;

public interface UserService {

    /**
     * 用户登录
     * @param loginDTO 接收数据请求
     * @return 返回体
     */
    LoginVO register(LoginDTO loginDTO);

    /**
     * 用户登录
     * @param loginDTO 接收数据请求
     * @return 返回体
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 根据 userId 获取用户信息
     * @param userId 用户id
     * @return 用户信息返回体
     */
    UserInfoVO getUserInfoById(Integer userId);
}
