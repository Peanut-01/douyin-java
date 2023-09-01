package com.douyin.controller.douyin;

import com.douyin.constant.MessageConstant;
import com.douyin.result.VideoResult;
import com.douyin.result.Result;
import com.douyin.service.VideoService;
import com.douyin.utils.AliOssUtil;
import com.douyin.utils.FileUtil;
import com.douyin.vo.VideoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/douyin/publish")
@Slf4j
@RestController
@Api("投稿接口")
public class PublishController {

    private final VideoService videoService;
    private final AliOssUtil aliOssUtil;

    public PublishController(VideoService videoService, AliOssUtil aliOssUtil) {
        this.videoService = videoService;
        this.aliOssUtil = aliOssUtil;
    }

    /**
     * 投稿列表
     * @param userId 作者Id
     * @return 投稿列表返回体
     */
    @GetMapping("/list")
    @ApiOperation("发布列表")
    public VideoResult publishList(@RequestParam(name = "user_id") Integer userId) {
        log.info("获取发布列表, userId: {}", userId);
        List<VideoVO> videoVOList = videoService.getByAuthorId(userId);
        return VideoResult.success(videoVOList);
    }

    /**
     * 投稿视频
     *
     * @param title 视频标题
     * @param data  视频数据
     * @return 返回响应
     */
    @PostMapping("/action")
    @ApiOperation("视频上传")
    public Result publish(MultipartFile data, String title) {
        log.info("文件上传，视频标题为: {}", title);
        try {
            // 上传视频
            String originalFilename = data.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            // 生成唯一文件名
            String videoName = UUID.randomUUID() + extension;
            String videoUrl = aliOssUtil.upload(data.getBytes(), videoName);

            // 生成视频封面
            File tempVideoFile = File.createTempFile("temp-video", extension);
            data.transferTo(tempVideoFile);

            File coverTempFile = File.createTempFile("temp-cover", ".jpg");
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(tempVideoFile.getAbsolutePath())
                    .addOutput(coverTempFile.getAbsolutePath())
                    .setFrames(1)
                    .done();
            new FFmpeg().run(builder);
            // 上传封面
            String coverName = UUID.randomUUID() + ".jpg";
            String coverUrl = aliOssUtil.upload(FileUtil.convertFileToByteArray(coverTempFile), coverName);

            videoService.publish(title, videoUrl, coverUrl);
            return Result.success();
        } catch (IOException e) {
            log.error("视频上传失败: {}", e.toString());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
