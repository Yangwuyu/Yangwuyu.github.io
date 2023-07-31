package com.nanhua.retrieval.controller;

import com.nanhua.common.vo.Result;
import com.nanhua.retrieval.entity.Admin;
import com.nanhua.retrieval.service.IAdminService;
import com.nanhua.retrieval.service.impl.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzq
 * @since 2023-05-22
 */
@RestController
@RequestMapping("/retrieval/admin")
@Slf4j
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @GetMapping("/alladmin")
    public Result<List<Admin>> getAllAdmin(){
        List<Admin> list=adminService.list();
        return Result.success(list,"查询成功");
    }
    @PostMapping("/adminlogin")
    public Result<Map<String,Object>> login(@RequestBody Admin admin){
        log.info("接收到的参数为：{}", admin);
        Map<String,Object> data=adminService.adminlogin(admin);
        if (data!=null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或者密码错误");
    }

    @GetMapping("/admininfo")
    public Result<?> getUserInfo(@Param("admintoken") String admintoken){
        Map<String,Object> data = adminService.getadminInfo(admintoken);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003,"用户信息获取失败,请重新登录");
    }

    @PostMapping("adminlogout")
    public Result<?> logout(@RequestHeader("Y-Token") String token){
        adminService.adminlogout(token);
        return  Result.success();
    }
}
