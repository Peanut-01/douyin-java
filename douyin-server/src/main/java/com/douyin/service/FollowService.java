package com.douyin.service;

import com.douyin.vo.UserInfoVO;

import java.util.List;

public interface FollowService {

    /**
     * 关注操作
     * @param toUserId
     * @param actionType
     */
    void follow(Integer toUserId, String actionType);

    /**
     * 获取关注列表
     * @return
     */
    List<UserInfoVO> getFollowList(Integer userId);

    /**
     * 获取粉丝列表
     * @param followedId
     * @return
     */
    List<UserInfoVO> getFollowerList(Integer followedId);

    List<UserInfoVO> getFriendList(Integer userId);
}
