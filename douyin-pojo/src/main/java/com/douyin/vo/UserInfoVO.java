package com.douyin.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {
    private Integer id;

    private String name;

    private Integer followCount;

    private Integer followerCount;

    @JsonProperty("is_follow")
    private boolean isFollow;

    private String avatar;

    private String backgroundImage;

    private String signature;

    private Integer totalFavorited;

    private Integer workCount;

    private Integer favoriteCount;
}
