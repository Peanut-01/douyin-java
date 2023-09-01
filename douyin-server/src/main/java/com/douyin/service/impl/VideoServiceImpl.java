package com.douyin.service.impl;

import com.douyin.constant.JwtClaimsConstant;
import com.douyin.constant.LikeConstant;
import com.douyin.constant.MessageConstant;
import com.douyin.context.BaseContext;
import com.douyin.entity.Like;
import com.douyin.entity.Video;
import com.douyin.exception.IllegalRequestTypeException;
import com.douyin.mapper.CommentMapper;
import com.douyin.mapper.LikeMapper;
import com.douyin.mapper.VideoMapper;
import com.douyin.properties.JwtProperties;
import com.douyin.result.FeedResult;
import com.douyin.service.UserService;
import com.douyin.service.VideoService;
import com.douyin.utils.JwtUtil;
import com.douyin.vo.UserInfoVO;
import com.douyin.vo.VideoVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    private final VideoMapper videoMapper;
    private final UserService userService;
    private final LikeMapper likeMapper;
    private final CommentMapper commentMapper;
    private final JwtProperties jwtProperties;

    public VideoServiceImpl(VideoMapper videoMapper, UserService userService, LikeMapper likeMapper, CommentMapper commentMapper, JwtProperties jwtProperties) {
        this.videoMapper = videoMapper;
        this.userService = userService;
        this.likeMapper = likeMapper;
        this.commentMapper = commentMapper;
        this.jwtProperties = jwtProperties;
    }

    /**
     * 保存视频相关数据到数据库
     *
     * @param title    标题
     * @param videoUrl 视频访问路径
     * @param coverUrl 视频封面访问路径
     */
    @Override
    public void publish(String title, String videoUrl, String coverUrl) {
        Integer authorId = BaseContext.getCurrentId();
        Video video = Video.builder()
                .title(title)
                .authorId(authorId)
                .playUrl(videoUrl)
                .coverUrl(coverUrl)
                .createTime(LocalDateTime.now())
                .build();
        videoMapper.insert(video);
    }

    /**
     * 通过作者 id 获取发布列表
     *
     * @param authorId 作者id
     * @return 发布列表返回体
     */
    @Override
    public List<VideoVO> getByAuthorId(Integer authorId) {
        List<Video> videoList = videoMapper.getVideosByAuthorId(authorId);
        List<VideoVO> videoVOList = new ArrayList<>();
        if (videoList.size() == 0) {
            // 没有视频，返回空集合
            return videoVOList;
        }
        // 封装 videoVOList
        wrapVideoVO(videoList, videoVOList, BaseContext.getCurrentId());
        return videoVOList;
    }

    /**
     * 赞操作
     *
     * @param videoId    视频 id
     * @param actionType 1-点赞，2-取消点赞
     */
    @Override
    public void favoriteAction(Integer videoId, String actionType) {
        // 判断点赞类型
        boolean isFavorite;
        if (LikeConstant.FAVORITE_ACTION.equals(actionType)) {
            isFavorite = true;
        } else if (LikeConstant.CANCEL_FAVORITE_ACTION.equals(actionType)) {
            isFavorite = false;
        } else {
            throw new IllegalRequestTypeException(MessageConstant.FAVORITE_TYPE_ILLEGAL);
        }
        Integer userId = BaseContext.getCurrentId();
        // 判断数据库中是否存在该数据
        Like like = likeMapper.selectByUserIdAndVideoId(userId, videoId);
        if (like == null) {
            // 不存在，直接插入数据
            like = Like.builder()
                    .userId(userId)
                    .videoId(videoId)
                    .isFavorite(isFavorite)
                    .build();
            likeMapper.insert(like);
        } else {
            // 存在，更新数据
            like.setFavorite(isFavorite);
            likeMapper.updateStatus(like);
        }
    }

    @Override
    public List<VideoVO> getFavoriteList(Integer userId) {
        // 获取点赞过的视频
        List<Video> videoList = videoMapper.getFavoriteList(userId);
        List<VideoVO> videoVOList = new ArrayList<>();
        // 封装 videoVOList
        wrapVideoVO(videoList, videoVOList, BaseContext.getCurrentId());
        return videoVOList;
    }

    /**
     * 视频流
     *
     * @param latestTime 可选参数，限制返回视频的最新投稿时间戳，精确到秒，不填表示当前时间
     * @return 视频流返回体
     */
    @Override
    public FeedResult getFeed(String latestTime, String token) {
        // 设置最新时间
        LocalDateTime time = LocalDateTime.now();
        if (latestTime != null) {
            long timestamp = Long.parseLong(latestTime);
            Instant instant = Instant.ofEpochMilli(timestamp);
            time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        // 查询视频
        List<Video> videoList = videoMapper.getFeed(time);
        // 如果没有查询到视频，时间更新为当前时间，重新查询
        if (videoList.size() == 0) {
            videoList = videoMapper.getFeed(LocalDateTime.now());
        }
        List<VideoVO> videoVOList = new ArrayList<>();
        // 如果 token 不为空，解析 token
        int userId = -1;
        if (token != null) {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            userId = Integer.parseInt(claims.get(JwtClaimsConstant.USER_ID).toString());
            BaseContext.setCurrentId(userId);
        }
        // 封装 videoVOList，填充剩余数据
        wrapVideoVO(videoList, videoVOList, userId);
        return new FeedResult(System.currentTimeMillis(), videoVOList);
    }

    private void wrapVideoVO(List<Video> videoList, List<VideoVO> videoVOList, Integer userId) {
        for (Video video : videoList) {
            // 查询当前视频获赞数
            int favoriteCount = likeMapper.getFavoriteCount(video.getVideoId());
            log.info("获赞总数: {}", favoriteCount);
            // 查询是否点赞，未登录默认是false
            boolean isFavorite = false;
            if (userId != -1) {
                Like like = likeMapper.selectByUserIdAndVideoId(userId, video.getVideoId());
                if (like != null) {
                    isFavorite = like.isFavorite();
                }
            }
            // 查询评论数
            int commentCount = commentMapper.list(video.getVideoId()).size();
            // 创建videoVO
            VideoVO videoVO = VideoVO.builder()
                    .id(video.getVideoId())
                    .isFavorite(isFavorite)
                    .favoriteCount(favoriteCount)
                    .commentCount(commentCount)
                    .build();
            BeanUtils.copyProperties(video, videoVO);
            // 查询并封装 authorInfo
            UserInfoVO authorInfo = userService.getUserInfoById(video.getAuthorId());
            videoVO.setAuthor(authorInfo);
            videoVOList.add(videoVO);
        }
    }
}
