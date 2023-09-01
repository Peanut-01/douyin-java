package com.douyin.controller.douyin;

import com.douyin.result.MessageResult;
import com.douyin.result.Result;
import com.douyin.service.MessageService;
import com.douyin.vo.MessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/douyin/message")
@Api("聊天相关接口")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 发送消息
     * @param toUserId
     * @param actionType
     * @param content
     * @return
     */
    @PostMapping("/action")
    @ApiOperation("发送消息")
    public Result sendMessage(@RequestParam(name = "to_user_id") Integer toUserId,
                              @RequestParam(name = "action_type") String actionType, String content) {
        log.info("发送消息: {}, {}", toUserId, content);
        messageService.sendMessage(toUserId, actionType, content);
        return Result.success();
    }

    /**
     * 获取聊天记录
     * @param toUserId
     * @return
     */
    @GetMapping("/chat")
    @ApiOperation("聊天记录")
    public MessageResult chatRecords(@RequestParam(name = "to_user_id") Integer toUserId, @RequestParam(name = "pre_msg_time") String preMsgTime) {
        log.info("获取聊天记录， toUserId : {}, {}", toUserId, preMsgTime);
        List<MessageVO> messageVOList = messageService.list(toUserId, preMsgTime);
        return MessageResult.success(messageVOList);
    }
}
