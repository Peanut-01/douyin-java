package com.douyin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
    private Integer id;

    private UserInfoVO user;

    private String content;

    private String createDate;
}
