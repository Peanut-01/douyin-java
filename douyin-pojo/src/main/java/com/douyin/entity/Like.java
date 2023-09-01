package com.douyin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like implements Serializable {
    private Integer userId;
    private Integer videoId;
    private boolean isFavorite;
}
