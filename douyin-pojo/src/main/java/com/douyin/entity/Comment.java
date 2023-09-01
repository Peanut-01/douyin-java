package com.douyin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private Integer commentId;
    private Integer videoId;
    private Integer userId;
    private String content;
    private LocalDateTime createTime;
}
