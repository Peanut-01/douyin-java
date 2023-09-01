package com.douyin.controller.douyin;

import com.douyin.result.CommentActionResult;
import com.douyin.result.CommentListResult;
import com.douyin.service.CommentService;
import com.douyin.vo.CommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/douyin/comment")
@Slf4j
@Api("评论相关接口")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 评论操作
     *
     * @param videoId
     * @param actionType
     * @param commentText
     * @param commentId
     * @return
     */
    @PostMapping("/action")
    @ApiOperation("评论操作")
    public CommentActionResult comment(@RequestParam(name = "video_id") Integer videoId, @RequestParam(name = "action_type") String actionType,
                                       @RequestParam(name = "comment_text", required = false) String commentText, @RequestParam(name = "comment_id", required = false) Integer commentId) {
        log.info("评论操作, videoId: {},actionType: {},commentText: {},commentId: {}", videoId, actionType, commentText, commentId);
        CommentVO commentVO = commentService.comment(videoId, actionType, commentText, commentId);
        return CommentActionResult.success(commentVO);
    }

    /**
     * 获取评论列表
     * @param videoId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取评论列表")
    public CommentListResult commentList(@RequestParam("video_id") Integer videoId) {
        log.info("获取评论列表: {}", videoId);
        List<CommentVO> commentVOList = commentService.list(videoId);
        return CommentListResult.success(commentVOList);
    }
}
