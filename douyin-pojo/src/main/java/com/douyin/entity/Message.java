package com.douyin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Integer messageId;
    private Integer fromUserId;
    private Integer toUserId;
    private String content;
    private LocalDateTime createTime;

}
