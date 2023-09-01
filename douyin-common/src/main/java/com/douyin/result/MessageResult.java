package com.douyin.result;

import com.douyin.vo.LoginVO;
import com.douyin.vo.MessageVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageResult extends Result {

    @JsonProperty("message_list")
    private List<MessageVO> messageList;

    public static MessageResult success(List<MessageVO> messageList) {
        MessageResult messageResult = new MessageResult(messageList);
        messageResult.setStatusCode(0);
        return messageResult;
    }
}
