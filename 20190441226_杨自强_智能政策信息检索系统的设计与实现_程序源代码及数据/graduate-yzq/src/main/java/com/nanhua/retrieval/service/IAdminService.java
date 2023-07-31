package com.nanhua.retrieval.service;

import com.nanhua.retrieval.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzq
 * @since 2023-05-22
 */
public interface IAdminService extends IService<Admin> {
    Map<String, Object> adminlogin(Admin admin);

    Map<String, Object> getadminInfo(String admintoken);

    void adminlogout(String token);

}
