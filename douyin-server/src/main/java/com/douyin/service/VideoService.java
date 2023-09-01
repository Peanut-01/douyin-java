package com.douyin.service;

import com.douyin.result.FeedResult;
import com.douyin.vo.VideoVO;

import java.util.List;

public interface VideoService {

    /**
     * 视频流
     *
     * @param latestTime 可选参数，限制返回视频的最新投稿时间戳，精确到秒，不填表示当前时间
     * @param token jwt
     * @return 视频流返回体
     */
    FeedResult getFeed(String latestTime, String token);

    /**
     * 上传视频
     *
     * @param title    标题
     * @param videoUrl 视频访问路径
     * @param coverUrl 视频封面访问路径
     */
    void publish(String title, String videoUrl, String coverUrl);

    /**
     * 通过作者 id 获取发布列表
     *
     * @param authorId 作者Id
     * @return 投稿列表返回体
     */
    List<VideoVO> getByAuthorId(Integer authorId);

    /**
     * 赞操作
     * @param videoId    视频 id
     * @param actionType 1-点赞，2-取消点赞
     */
    void favoriteAction(Integer videoId, String actionType);

    /**
     * 获取喜欢列表
     * @param userId
     * @return
     */
    List<VideoVO> getFavoriteList(Integer userId);
}
