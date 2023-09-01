package com.douyin.service.impl;

import com.douyin.constant.MessageConstant;
import com.douyin.context.BaseContext;
import com.douyin.dto.LoginDTO;
import com.douyin.entity.Follow;
import com.douyin.entity.Like;
import com.douyin.entity.User;
import com.douyin.exception.AccountNotFoundException;
import com.douyin.exception.PasswordErrorException;
import com.douyin.exception.RegisterException;
import com.douyin.mapper.FollowMapper;
import com.douyin.mapper.LikeMapper;
import com.douyin.mapper.UserMapper;
import com.douyin.mapper.VideoMapper;
import com.douyin.properties.JwtProperties;
import com.douyin.service.UserService;
import com.douyin.utils.JwtUtil;
import com.douyin.vo.LoginVO;
import com.douyin.vo.UserInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    private final JwtProperties jwtProperties;
    private final UserMapper userMapper;
    private final VideoMapper videoMapper;
    private final LikeMapper likeMapper;
    private final FollowMapper followMapper;

    public UserServiceImpl(UserMapper userMapper, JwtProperties jwtProperties, VideoMapper videoMapper, LikeMapper likeMapper, FollowMapper followMapper) {
        this.userMapper = userMapper;
        this.jwtProperties = jwtProperties;
        this.videoMapper = videoMapper;
        this.likeMapper = likeMapper;
        this.followMapper = followMapper;
    }

    /**
     * 用户登录
     *
     * @param loginDTO 接收数据请求
     * @return 返回体
     */
    @Override
    public LoginVO register(LoginDTO loginDTO) {
        // 从数据库查询用户，用户名不允许重复
        User user = userMapper.getByUsername(loginDTO.getUsername());
        if (user != null) {
            throw new RegisterException(MessageConstant.USERNAME_HAS_EXISTED);
        }
        String password = DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        // 允许注册
        user = new User(loginDTO.getUsername(), password);
        userMapper.register(user);
        Integer userId = user.getUserId();
        // 生成对应 jwt 令牌
        String token = getToken(userId);

        return new LoginVO(userId, token);
    }

    /**
     * @param loginDTO 接收请求返回体
     * @return 返回体
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        User user = userMapper.getByUsername(loginDTO.getUsername());
        if (user == null) {
            // 用户名不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        String password = DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        if (!password.equals(user.getPassword())) {
            // 密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        Integer userId = user.getUserId();
        // 生成对应 jwt 令牌
        String token = getToken(userId);

        return new LoginVO(userId, token);
    }

    /**
     * 根据 userId 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息返回体
     */
    @Override
    public UserInfoVO getUserInfoById(Integer userId) {
        User user = userMapper.getByUserId(userId);
        // 查询作品数
        int workCount = videoMapper.getVideosByAuthorId(userId).size();
        // 查询点赞数
        Like like = Like.builder()
                .userId(userId)
                .isFavorite(true)
                .build();
        int favoriteCount = likeMapper.select(like).size();
        // 查询用户的获赞总数
        int totalFavorited = videoMapper.getTotalFavorited(userId);
        // 查询粉丝数
        int followerCount = followMapper.select(Follow.builder().followedId(userId).build()).size();
        // 查询关注数
        int followCount = followMapper.select(Follow.builder().userId(userId).build()).size();
        // 查询当前是否关注该用户
        Integer currentUserId = BaseContext.getCurrentId();
        boolean isFollow = false;
        if (currentUserId != null) {
            List<Follow> followList = followMapper.select(Follow.builder().userId(currentUserId).followedId(userId).build());
            isFollow = followList != null && followList.size() > 0;
        }

        UserInfoVO userInfoVO = UserInfoVO.builder()
                .name(user.getUsername())
                .id(userId)
                .followerCount(followerCount)
                .followCount(followCount)
                .favoriteCount(favoriteCount)
                .isFollow(isFollow)
                .signature("test")
                .avatar(null)
                .backgroundImage(null)
                .totalFavorited(totalFavorited)
                .workCount(workCount)
                .build();
        return userInfoVO;
    }

    /**
     * 根据 userId 生成 jwt令牌
     *
     * @param userId 用户id
     * @return token
     */
    private String getToken(Integer userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);
    }
}
