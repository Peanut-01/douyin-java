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
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer videoId;

    private String title;

    private Integer authorId;

    private String playUrl;

    private String coverUrl;

    private LocalDateTime createTime;
}
