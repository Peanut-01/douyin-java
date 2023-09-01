package com.douyin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {

    private Integer id;
    private Integer fromUserId;
    private Integer toUserId;
    private String content;
    private Long createTime;

}
