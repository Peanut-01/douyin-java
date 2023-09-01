package com.douyin.mapper;

import com.douyin.entity.Follow;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FollowMapper {

    /**
     * 取关，删除记录
     * @param userId
     * @param followedId
     */
    @Delete("delete from follows where user_id = #{userId} and followed_id = #{followedId}")
    void delete(Integer userId, Integer followedId);

    /**
     * 插入记录
     * @param follow
     */
    @Insert("insert into follows (user_id, followed_id, is_friend) " +
            "VALUES (#{userId}, #{followedId}, #{isFriend})")
    void insert(Follow follow);

    /**
     * 修改是否为好友属性
     * @param follow
     */
    @Update("update follows set is_friend = #{isFriend} " +
            "where user_id = #{userId} and followed_id = #{followedId}")
    void update(Follow follow);

    /**
     * 查询记录
     * @param follow
     * @return
     */
    List<Follow> select(Follow follow);
}
