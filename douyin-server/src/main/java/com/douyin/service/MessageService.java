package com.douyin.service;

import com.douyin.vo.MessageVO;

import java.util.List;

public interface MessageService {

    /**
     * 发送消息
     * @param toUserId
     * @param actionType
     * @param content
     */
    void sendMessage(Integer toUserId, String actionType, String content);

    /**
     * 获取聊天记录
     *
     * @param toUserId
     * @param preMsgTime
     * @return
     */
    List<MessageVO> list(Integer toUserId, String preMsgTime);
}
