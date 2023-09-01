package com.douyin.mapper;

import com.douyin.entity.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface VideoMapper {

    /**
     * 插入视频相关数据
     * @param video 视频实体类
     */
    @Insert("insert into videos (title, author_id, play_url, cover_url, create_time) " +
            "values (#{title}, #{authorId}, #{playUrl}, #{coverUrl}, #{createTime})")
    void insert(Video video);

    /**
     * 限制最新时间，查询最多 30 条视频记录，按照时间降序排序
     * @param now 限制最新时间
     * @return 视频列表
     */
    @Select("select * from videos where create_time < #{now} order by create_time desc limit 30")
    List<Video> getFeed(LocalDateTime now);

    /**
     * 通过作者 id 获取发布列表
     * @param authorId 作者 id
     * @return 视频列表
     */
    @Select("select * from videos where author_id = #{authorId} order by create_time desc")
    List<Video> getVideosByAuthorId(Integer authorId);

    /**
     * 根据作者 id 查询作者获赞总数
     * @param authorId
     * @return
     */
    @Select("select count(*) from videos v left join likes l on v.video_id = l.video_id " +
            "where v.author_id = #{authorId} and l.is_favorite = true")
    int getTotalFavorited(Integer authorId);

    /**
     * 获取点赞列表
     * @param userId
     * @return
     */
    @Select("Select * from videos v left join likes l on v.video_id = l.video_id " +
            "where l.user_id = #{userId} and l.is_favorite = true;")
    List<Video> getFavoriteList(Integer userId);
}
