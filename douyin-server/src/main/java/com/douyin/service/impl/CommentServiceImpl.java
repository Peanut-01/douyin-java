package com.douyin.service.impl;

import com.douyin.constant.CommentConstant;
import com.douyin.context.BaseContext;
import com.douyin.entity.Comment;
import com.douyin.exception.IllegalRequestTypeException;
import com.douyin.mapper.CommentMapper;
import com.douyin.service.CommentService;
import com.douyin.service.UserService;
import com.douyin.vo.CommentVO;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final UserService userService;

    public CommentServiceImpl(CommentMapper commentMapper, UserService userService) {
        this.commentMapper = commentMapper;
        this.userService = userService;
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
    @Override
    public CommentVO comment(Integer videoId, String actionType, String commentText, Integer commentId) {
        if (CommentConstant.PUBLISH_COMMENT.equals(actionType)) {
            // 评论操作
            Integer userId = BaseContext.getCurrentId();
            Comment comment = Comment.builder()
                    .userId(userId)
                    .videoId(videoId)
                    .content(commentText)
                    .createTime(LocalDateTime.now()).build();
            commentMapper.insert(comment);
            // 获取日期
            LocalDateTime createTime = comment.getCreateTime();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
            String createDate = createTime.format(dateFormatter);
            // 封装 CommentVO 对象
            return CommentVO.builder()
                    .id(comment.getCommentId())
                    .content(comment.getContent())
                    .createDate(createDate)
                    .user(userService.getUserInfoById(userId))
                    .build();

        } else if (CommentConstant.DELETE_COMMENT.equals(actionType)) {
            // 删除评论操作
            commentMapper.delete(commentId);
            return null;
        } else {
            throw new IllegalRequestTypeException("评论请求参数非法");
        }
    }

    /**
     * 获取评论列表
     *
     * @param videoId
     * @return
     */
    @Override
    public List<CommentVO> list(Integer videoId) {
        List<Comment> commentList = commentMapper.list(videoId);
        List<CommentVO> commentVOList = new ArrayList<>();
        if (commentList == null || commentList.size() == 0) {
            return commentVOList;
        }
        for (Comment comment : commentList) {
            // 获取评论日期
            LocalDateTime createTime = comment.getCreateTime();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
            String createDate = createTime.format(dateFormatter);
            // 封装 CommentVO
            CommentVO commentVO = CommentVO.builder()
                    .id(comment.getCommentId())
                    .content(comment.getContent())
                    .createDate(createDate)
                    .user(userService.getUserInfoById(comment.getUserId()))
                    .build();
            commentVOList.add(commentVO);
        }
        return commentVOList;
    }
}
