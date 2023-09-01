package com.douyin.result;

import com.douyin.vo.VideoVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class VideoResult extends Result {

    private List<VideoVO> videoList;

    public static VideoResult success(List<VideoVO> videoVOList) {
        VideoResult videoResult = new VideoResult(videoVOList);
        videoResult.setStatusCode(0);
        return videoResult;
    }

    public VideoResult(List<VideoVO> videoList) {
        this.videoList = videoList;
    }
}
