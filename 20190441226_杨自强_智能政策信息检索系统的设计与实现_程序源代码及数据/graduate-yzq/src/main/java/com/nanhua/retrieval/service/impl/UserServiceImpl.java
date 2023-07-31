package com.nanhua.retrieval.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanhua.retrieval.entity.User;
import com.nanhua.retrieval.mapper.UserMapper;
import com.nanhua.retrieval.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> login(User user) {
        //根据user里面的用户名和密码查询
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, user.getUsername());
            wrapper.eq(User::getPassword, user.getPassword());
            User loginuser = this.baseMapper.selectOne(wrapper);
            //查询到了，返回一个token,并将用户信息存入redis
            if (loginuser != null) {
                //token暂用uuid
                String key = "user:" + UUID.randomUUID();
                //用户信息存入redis
                loginuser.setPassword(null);
                redisTemplate.opsForValue().set(key, loginuser, 30, TimeUnit.MINUTES);
                //返回数据
                Map<String, Object> data = new HashMap<>();
                data.put("token", key);
                return data;
            }

        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 从redis查询token
        Object obj = redisTemplate.opsForValue().get(token);
        // 反序列化
        User user = JSON.parseObject(JSON.toJSONString(obj),User.class);
        if(user != null){
            Map<String, Object> data =  new HashMap<>();
            data.put("id",user.getId());
            data.put("name",user.getUsername());
            data.put("avatar",user.getAvatar());
            data.put("email", user.getEmail());

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    }


