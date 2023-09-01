package com.douyin.controller.douyin;

import com.douyin.result.Result;
import com.douyin.result.UserInfoListResult;
import com.douyin.service.FollowService;
import com.douyin.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/douyin/relation")
@Api("关注相关操作")
public class RelationController {

    private final FollowService followService;

    public RelationController(FollowService followService) {
        this.followService = followService;
    }

    /**
     * 关注操作
     *
     * @param toUserId
     * @param actionType
     * @return
     */
    @PostMapping("/action")
    @ApiOperation("关注操作")
    public Result follow(@RequestParam(name = "to_user_id") Integer toUserId, @RequestParam(name = "action_type") String actionType) {
        log.info("关注操作, toUserId: {},actionType: {}", toUserId, actionType);
        followService.follow(toUserId, actionType);
        return Result.success();
    }

    /**
     * 关注列表
     *
     * @param userId
     * @return
     */
    @GetMapping("/follow/list")
    @ApiOperation("关注列表")
    public UserInfoListResult followList(@RequestParam(name = "user_id") Integer userId) {
        log.info("获取关注列表, userId: {}", userId);
        List<UserInfoVO> userInfoVOList = followService.getFollowList(userId);
        return UserInfoListResult.success(userInfoVOList);
    }

    /**
     * 粉丝列表
     * @param userId
     * @return
     */
    @GetMapping("/follower/list")
    @ApiOperation("粉丝列表")
    public UserInfoListResult fansList(@RequestParam(name = "user_id") Integer userId) {
        log.info("获取粉丝列表, userId: {}", userId);
        List<UserInfoVO> userInfoVOList = followService.getFollowerList(userId);
        return UserInfoListResult.success(userInfoVOList);
    }

    /**
     * 获取好友列表
     * @param userId
     * @return
     */
    @GetMapping("/friend/list")
    @ApiOperation("好友列表")
    public UserInfoListResult friendList(@RequestParam(name = "user_id") Integer userId) {
        log.info("获取好友列表, userId: {}", userId);
        List<UserInfoVO> userInfoVOList = followService.getFriendList(userId);
        return UserInfoListResult.success(userInfoVOList);
    }
}
