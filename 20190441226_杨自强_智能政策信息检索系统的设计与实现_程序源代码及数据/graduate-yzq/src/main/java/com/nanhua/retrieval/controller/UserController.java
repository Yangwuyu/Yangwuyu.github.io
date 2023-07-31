package com.nanhua.retrieval.controller;

import com.nanhua.common.vo.Result;
import com.nanhua.retrieval.entity.User;
import com.nanhua.retrieval.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
@RestController
@RequestMapping("/retrieval/user")

public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list=userService.list();
        return Result.success(list,"查询成功");
    }


    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data=userService.login(user);
        if (data!=null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或者密码错误");
    }

    @GetMapping("/info")
    public Result<?> getUserInfo(@Param("token") String token){
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003,"用户信息获取失败,请重新登录");
    }

    @PostMapping("logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return  Result.success();
    }

}
