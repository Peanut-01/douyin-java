package com.douyin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoVO implements Serializable {
    private Integer id;

    private UserInfoVO author;

    private String playUrl;

    private String coverUrl;

    private Integer favoriteCount;

    private Integer commentCount;

    private boolean isFavorite;

    private String title;
}
