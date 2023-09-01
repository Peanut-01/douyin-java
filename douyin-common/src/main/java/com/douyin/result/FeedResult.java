package com.douyin.result;

import com.douyin.vo.VideoVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FeedResult extends Result {

    private Long nextTime;

    private List<VideoVO> videoList;

    public static FeedResult success(Long nextTime, List<VideoVO> videoVOList) {
        FeedResult feedResult = new FeedResult(nextTime, videoVOList);
        feedResult.setStatusCode(0);
        return feedResult;
    }

    public FeedResult(Long nextTime, List<VideoVO> videoList) {
        this.nextTime = nextTime;
        this.videoList = videoList;
    }
}
