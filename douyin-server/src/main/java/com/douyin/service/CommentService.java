package com.douyin.service;
import com.douyin.vo.CommentVO;
import java.util.List;

public interface CommentService {


    /**
     * 获取评论列表
     * @param videoId
     * @return
     */
    List<CommentVO> list(Integer videoId);

    /**
     * 评论操作
     * @param videoId
     * @param actionType
     * @param commentText
     * @param commentId
     * @return
     */
    CommentVO comment(Integer videoId, String actionType, String commentText, Integer commentId);
}
