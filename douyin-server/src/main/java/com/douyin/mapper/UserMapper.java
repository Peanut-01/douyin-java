package com.douyin.mapper;

import com.douyin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 用户注册
     *
     * @return
     */
    void register(User user);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from users where username = #{username}")
    User getByUsername(String username);

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    @Select("select * from users where user_id = #{userId}")
    User getByUserId(Integer userId);
}
