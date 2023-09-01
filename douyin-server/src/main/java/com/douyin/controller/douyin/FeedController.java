package com.douyin.controller.douyin;

import com.douyin.result.FeedResult;
import com.douyin.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api("视频流接口")
@Slf4j
@RestController
@RequestMapping("/douyin/feed")
public class FeedController {

    private final VideoService videoService;

    public FeedController(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * 视频流
     * @param latestTime 可选参数，限制返回视频的最新投稿时间戳，精确到秒，不填表示当前时间
     * @return 视频流返回体
     */
    @GetMapping
    @ApiOperation("feed流")
    public FeedResult feed(String token, @RequestParam(name = "latest_time", required = false) String latestTime) {
        log.info("feed流: {}", latestTime);
        FeedResult feedResult = videoService.getFeed(latestTime, token);
        return FeedResult.success(feedResult.getNextTime(), feedResult.getVideoList());
    }
}
