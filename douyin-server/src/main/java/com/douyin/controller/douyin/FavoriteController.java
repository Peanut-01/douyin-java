package com.douyin.controller.douyin;

import com.douyin.constant.LikeConstant;
import com.douyin.result.Result;
import com.douyin.result.VideoResult;
import com.douyin.service.VideoService;
import com.douyin.vo.VideoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/douyin/favorite")
@Api("点赞相关接口")
public class FavoriteController {

    private final VideoService videoService;

    public FavoriteController(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     *
     * @param videoId 视频 id
     * @param actionType 1-点赞，2-取消点赞
     * @return 赞操作成功返回体
     */
    @ApiOperation("赞操作")
    @PostMapping("/action")
    public Result favoriteAction(@RequestParam(name = "video_id") Integer videoId, @RequestParam(name = "action_type") String actionType) {
        log.info("赞操作: videoId:{}, action_type: {}", videoId,
                LikeConstant.FAVORITE_ACTION.equals(actionType) ? "点赞" : "取消点赞");
        videoService.favoriteAction(videoId, actionType);
        return Result.success();
    }

    @ApiOperation("喜欢列表")
    @GetMapping("/list")
    public VideoResult list(@RequestParam(name = "user_id") Integer userId) {
        log.info("喜欢列表, userId: {}", userId);
        List<VideoVO> videoVOList = videoService.getFavoriteList(userId);
        return VideoResult.success(videoVOList);
    }
}