package com.nanhua.retrieval.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nanhua.retrieval.entity.Admin;
import com.nanhua.retrieval.mapper.AdminMapper;
import com.nanhua.retrieval.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2023-05-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> adminlogin(Admin admin) {
        //根据user里面的用户名和密码查询
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getAdminname, admin.getAdminname());
        wrapper.eq(Admin::getPassword, admin.getPassword());
        Admin loginadmin = this.baseMapper.selectOne(wrapper);
        //查询到了，返回一个token,并将用户信息存入redis
        if (loginadmin != null) {
            //token暂用uuid
            String key = "admin:" + UUID.randomUUID();
            //用户信息存入redis
            loginadmin.setPassword(null);
            redisTemplate.opsForValue().set(key, loginadmin, 30, TimeUnit.MINUTES);
            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("admintoken", key);
            return data;
        }

        return null;
    }

    @Override
    public Map<String, Object> getadminInfo(String admintoken) {
        // 从redis查询token
        Object obj = redisTemplate.opsForValue().get(admintoken);
        // 反序列化
        Admin admin = JSON.parseObject(JSON.toJSONString(obj),Admin.class);
        if(admin != null){
            Map<String, Object> data =  new HashMap<>();
            data.put("adminid",admin.getId());
            data.put("adminname",admin.getAdminname());
            data.put("adminavatar",admin.getAdminavatar());


            return data;
        }
        return null;
    }

    @Override
    public void adminlogout(String token) {
        redisTemplate.delete(token);
    }

}
