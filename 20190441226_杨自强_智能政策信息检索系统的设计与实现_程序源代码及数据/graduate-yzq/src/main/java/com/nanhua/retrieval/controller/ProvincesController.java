package com.nanhua.retrieval.controller;

import com.nanhua.common.vo.Result;
import com.nanhua.retrieval.service.IProvincesService;
import com.nanhua.retrieval.service.IUserService;
import com.nanhua.retrieval.service.impl.ProvincesServiceImpl;
import com.nanhua.retrieval.service.IProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzq
 * @since 2023-05-02
 */
@RestController
@RequestMapping("/retrieval/provinces")
public class ProvincesController {
    @Autowired
    private IProvincesService provincesService;

    @GetMapping("locations")
    public Result<Map<Integer,String>> getProvinces(){
        // 实现获取地点列表的逻辑，返回省份数据
        Map<Integer, String> provinces=new HashMap<>();
        provinces=provincesService.getProvinces();
        return Result.success(provinces);
    }
}
