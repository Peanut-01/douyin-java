package com.douyin.mapper;

import com.douyin.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 添加评论
     * @param comment
     */
    void insert(Comment comment);

    /**
     * 删除评论
     * @param commentId
     */
    @Delete("delete from comments where comment_id = #{commentId}")
    void delete(Integer commentId);

    @Select("select * from comments where video_id = #{videoId} order by create_time desc")
    List<Comment> list(Integer videoId);
}
