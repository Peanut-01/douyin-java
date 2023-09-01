package com.douyin.service.impl;

import com.douyin.constant.ChatConstant;
import com.douyin.context.BaseContext;
import com.douyin.entity.Message;
import com.douyin.exception.IllegalRequestTypeException;
import com.douyin.mapper.MessageMapper;
import com.douyin.service.MessageService;
import com.douyin.vo.MessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    /**
     * 发送消息
     *
     * @param toUserId
     * @param actionType
     * @param content
     */
    @Override
    public void sendMessage(Integer toUserId, String actionType, String content) {
        if (!ChatConstant.SEND_MESSAGE.equals(actionType)) {
            throw new IllegalRequestTypeException("发送消息请求参数有误");
        }
        // 构造消息对象
        Message message = Message.builder()
                .fromUserId(BaseContext.getCurrentId())
                .toUserId(toUserId)
                .content(content)
                .createTime(LocalDateTime.now())
                .build();
        // 插入数据
        messageMapper.insert(message);
    }

    @Override
    public List<MessageVO> list(Integer toUserId, String preMsgTime) {
        // 获取上次获取的时间
        Instant instant = Instant.ofEpochMilli(Long.parseLong(preMsgTime));
        LocalDateTime preTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // 获取当前用户 userId
        Integer userId = BaseContext.getCurrentId();
        // 查询聊天记录
        List<Message> messageList = messageMapper.select(userId, toUserId, preTime);
        // 封装 List<MessageVO>
        List<MessageVO> messageVOList = new ArrayList<>();
        for (Message message : messageList) {
            MessageVO messageVO = new MessageVO();
            // 复制属性
            BeanUtils.copyProperties(message, messageVO);
            // 补充、修改属性
            messageVO.setId(message.getMessageId());
            LocalDateTime createTime = message.getCreateTime();
            long timestamp = createTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            messageVO.setCreateTime(timestamp);
            // 添加到集合
            messageVOList.add(messageVO);
        }
        return messageVOList;
    }
}
