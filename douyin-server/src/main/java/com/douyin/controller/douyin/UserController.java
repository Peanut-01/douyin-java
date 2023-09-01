package com.douyin.controller.douyin;

import com.douyin.context.BaseContext;
import com.douyin.dto.LoginDTO;
import com.douyin.result.LoginResult;
import com.douyin.result.UserInfoResult;
import com.douyin.service.UserService;
import com.douyin.vo.LoginVO;
import com.douyin.vo.UserInfoVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 */
@RequestMapping("/douyin/user")
@Slf4j
@RestController
@Api("用户相关接口")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     *
     * @param loginDTO 接收数据请求
     * @return 返回体
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public LoginResult register(LoginDTO loginDTO) {
        log.info("用户注册");
        LoginVO loginVO = userService.register(loginDTO);
        return LoginResult.success(loginVO);
    }

    /**
     * 用户登录
     *
     * @param loginDTO 接收数据请求
     * @return 返回体
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public LoginResult login(LoginDTO loginDTO) {
        log.info("用户登录: {}", loginDTO);
        LoginVO loginVO = userService.login(loginDTO);
        return LoginResult.success(loginVO);
    }

    /**
     * 用户信息
     * @return 用户信息返回体
     */
    @ApiOperation("用户信息")
    @GetMapping
    public UserInfoResult userInfo(@RequestParam(name = "user_id") Integer userId) {
        log.info("获取用户信息: 用户id: {}; 当前用户: {}", userId, BaseContext.getCurrentId());
        UserInfoVO userInfoVO = userService.getUserInfoById(userId);
        return UserInfoResult.success(userInfoVO);
    }
}
