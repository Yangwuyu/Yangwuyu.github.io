package com.nanhua.retrieval.controller;

import com.nanhua.common.vo.Result;
import com.nanhua.retrieval.entity.History;
import com.nanhua.retrieval.mapper.HistoryMapper;
import com.nanhua.retrieval.service.impl.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
@RestController
@RequestMapping("/retrieval/history")
public class HistoryController {
@Autowired
private HistoryServiceImpl historyService;
@Autowired
private HistoryMapper historyMapper;



//保存方法
    @PostMapping("/saveh")
    public Result<?> saveh(@RequestBody History history){
        historyMapper.save(history);
        return Result.success();
    }
    //获取方法
    @GetMapping("/geth")
    public Result<List<History>> geth(@RequestParam int userId){
        List<History> data=historyService.getbyuserId(userId);
        return Result.success(data);
    }
}
