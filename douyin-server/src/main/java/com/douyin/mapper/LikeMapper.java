package com.douyin.mapper;

import com.douyin.entity.Like;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LikeMapper {

    /**
     * 查询单条记录
     * @param like
     * @return
     */
    List<Like> select(Like like);

    /**
     * 插入数据
     * @param like
     */
    @Insert("insert into likes (user_id, video_id, is_favorite) VALUES " +
            "(#{userId}, #{videoId}, #{isFavorite})")
    void insert(Like like);

    /**
     * 更新点赞状态
     * @param like
     */
    @Update("update likes set is_favorite = #{isFavorite} " +
            "where user_id = #{userId} and video_id = #{videoId}")
    void updateStatus(Like like);

    /**
     * 查询是否存在这条记录
     * @param userId
     * @param videoId
     * @return
     */
    @Select("select * from likes where user_id = #{userId} and video_id = #{videoId} limit 1")
    Like selectByUserIdAndVideoId(Integer userId, Integer videoId);

    /**
     * 查询该视频的获赞数
     * @param videoId
     * @return
     */
    @Select("select count(*) from likes where video_id = #{videoId} and is_favorite = true")
    int getFavoriteCount(Integer videoId);
}
