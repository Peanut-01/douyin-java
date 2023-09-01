package com.douyin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follow {
    private Integer userId;
    private Integer followedId;
    private String isFriend;

    public Follow(Integer userId, Integer followId) {
        this.userId = userId;
        this.followedId = followId;
    }
}
