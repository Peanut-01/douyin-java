package com.douyin.service.impl;

import com.douyin.constant.FollowConstant;
import com.douyin.constant.IsFriendConstant;
import com.douyin.context.BaseContext;
import com.douyin.entity.Follow;
import com.douyin.exception.IllegalRequestTypeException;
import com.douyin.mapper.FollowMapper;
import com.douyin.service.FollowService;
import com.douyin.service.UserService;
import com.douyin.vo.UserInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;
    private final UserService userService;

    public FollowServiceImpl(FollowMapper followMapper, UserService userService) {
        this.followMapper = followMapper;
        this.userService = userService;
    }

    /**
     * 关注/取关操作。取关则删除数据，关注则新增数据；但两者都要检查并更新 is_friend 字段
     *
     * @param toUserId   被关注者
     * @param actionType 1-关注，2-取消关注
     */
    @Override
    @Transactional
    public void follow(Integer toUserId, String actionType) {
        Integer userId = BaseContext.getCurrentId();
        if (FollowConstant.FOLLOW.equals(actionType)) {
            // 关注操作，先查看对方 toUserId 是否关注了自己 userId
            List<Follow> followList = followMapper.select(new Follow(toUserId, userId));
            // 关注后双方是否为好友（是否互相关注）
            String isFriend = IsFriendConstant.NOT_FRIEND;
            if (followList != null && followList.size() > 0) {
                // 对方也关注了自己，则更新对方的 is_friend 为 1
                Follow follow = followList.get(0);
                follow.setIsFriend(IsFriendConstant.IS_FRIEND);
                followMapper.update(follow);
                isFriend = IsFriendConstant.IS_FRIEND;
            }
            // 新增关注记录
            Follow follow = Follow.builder()
                    .userId(userId)
                    .followedId(toUserId)
                    .build();
            List<Follow> list = followMapper.select(follow);
            if (list == null || list.size() == 0) {
                follow.setIsFriend(isFriend);
                followMapper.insert(follow);
            }
        } else if (FollowConstant.CANCEL_FOLLOW.equals(actionType)) {
            // 取消关注
            followMapper.delete(userId, toUserId);
            // 查询对方 toUserId 之前是否关注了自己 userId
            List<Follow> followList = followMapper.select(new Follow(toUserId, userId));
            if (followList != null && followList.size() > 0) {
                // 若对方之前关注了自己，则取关后 isFriend 为 false
                Follow follow = followList.get(0);
                follow.setIsFriend(IsFriendConstant.NOT_FRIEND);
                followMapper.update(follow);
            }
        } else {
            throw new IllegalRequestTypeException("关注操作参数非法");
        }
    }

    /**
     * 获取关注列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserInfoVO> getFollowList(Integer userId) {
        // 查询关注者
        List<Follow> followList = followMapper.select(Follow.builder().userId(userId).build());
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        if (followList != null && followList.size() > 0) {
            // 遍历所有关注者
            for (Follow follow : followList) {
                UserInfoVO userInfoVO = userService.getUserInfoById(follow.getFollowedId());
                userInfoVOList.add(userInfoVO);
            }
        }
        return userInfoVOList;
    }

    /**
     * 获取粉丝列表
     *
     * @param followedId
     * @return
     */
    @Override
    public List<UserInfoVO> getFollowerList(Integer followedId) {
        // 查询关注者
        List<Follow> followList = followMapper.select(Follow.builder().followedId(followedId).build());
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        if (followList != null && followList.size() > 0) {
            // 遍历所有粉丝
            for (Follow follow : followList) {
                UserInfoVO userInfoVO = userService.getUserInfoById(follow.getUserId());
                userInfoVOList.add(userInfoVO);
            }
        }
        return userInfoVOList;
    }

    @Override
    public List<UserInfoVO> getFriendList(Integer userId) {
        // 查询所有好友
        Follow follow = Follow.builder()
                .userId(userId)
                .isFriend(IsFriendConstant.IS_FRIEND)
                .build();
        List<Follow> friendList = followMapper.select(follow);
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        if (friendList != null && friendList.size() > 0) {
            // 遍历所有好友
            for (Follow friend : friendList) {
                UserInfoVO userInfoVO = userService.getUserInfoById(friend.getFollowedId());
                userInfoVOList.add(userInfoVO);
            }
        }
        return userInfoVOList;
    }
}
