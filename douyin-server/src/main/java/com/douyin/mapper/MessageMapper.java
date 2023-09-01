package com.douyin.mapper;

import com.douyin.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 发送消息
     * @param message
     */
    @Insert("insert into messages (from_user_id, to_user_id, content, create_time) VALUES " +
            "(#{fromUserId}, #{toUserId}, #{content}, #{createTime})")
    void insert(Message message);

    /**
     * 查询聊天记录
     * @param userId
     * @param toUserId
     * @return
     */
    @Select("select * from messages where from_user_id in (#{userId}, #{toUserId}) " +
            "and to_user_id in (#{userId}, #{toUserId}) and create_time > #{preTime} order by create_time")
    List<Message> select(Integer userId, Integer toUserId, LocalDateTime preTime);
}
