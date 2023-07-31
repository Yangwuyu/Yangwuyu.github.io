package com.nanhua.retrieval.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanhua.common.vo.Result;
import com.nanhua.retrieval.entity.Policy;
import com.nanhua.retrieval.mapper.PolicyMapper;
import com.nanhua.retrieval.service.impl.PolicyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzq
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/retrieval/policy")
public class PolicyController {
    @Autowired
    private PolicyServiceImpl policyService;
    @Autowired
    private PolicyMapper policyMapper;

    /**
     * 所有的信息录入和查询功能
     * @param policy_id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("allinfo")
    public Result<Map<String,Object>> getInformationList(@RequestParam(value = "policy_id",required = false)Long policy_id,
                                                         //@RequestParam(value = "keyinfo",required = false)String keyinfo,
                                                         @RequestParam(value = "pageNo")Integer pageNo,
                                                         @RequestParam(value = "pageSize")Integer pageSize){
        //使用分页查询,baomidu里面的Page类和page方法
        //wrapper条件构造器
        LambdaQueryWrapper<Policy> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(policy_id!=null,Policy::getPolicyid,policy_id);
        //wrapper.like(StringUtils.hasLength(keyinfo),Policy::getPolicybody,keyinfo);

        Page<Policy> page = new Page<>(pageNo,pageSize);
        policyService.page(page,wrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    //传给前端政策主体信息
    @GetMapping("policytitle")
    public Result<String> getPolicytitle(@RequestParam(value = "policyid") Integer policyid){

        String data= policyService.getPolicytitle(policyid);
        return Result.success(data,"");

    }
    @GetMapping("policybody")
    public Result<String> getPolicybody(@RequestParam(value = "policyid") Integer policyid){

        String data= policyService.getPolicybody(policyid);
        return Result.success(data,"");

    }
    @GetMapping("search")
    public Result<?> intelligentSearch(@RequestParam(value = "keyinfo",required = false)String keyinfo,
                                       @RequestParam(value = "province",required = false)String province,
                                       @RequestParam(value = "date",required = false)String date,
                                       @RequestParam(value = "pageNo")Integer pageNo,
                                       @RequestParam(value = "pageSize")Integer pageSize){
        LocalDate selectdate= LocalDate.of(2030, 1, 1);
        if(!date.equals("")){
            Instant instant = Instant.parse(date);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            selectdate = localDateTime.toLocalDate();}

        String keyword=keyinfo;
            char c='，';
            if ( keyinfo.contains(String.valueOf(c))) {
                String[] txtArr = keyword.split("，"); // 关键字数组
                int threadNum = txtArr.length; // 子线程数量
                CountDownLatch doneSignal = new CountDownLatch(threadNum); // 创建CountDownLatch
                for (String txt : txtArr) {
                    if (!txt.equals("")) { // 判断关键字是否为空
                        Thread thread = new Thread() { // 创建一个子线程
                            @Override
                            public void run() {
                                // 处理关键字的检索请求
                                policyService.searchPolicies(txt);
                                doneSignal.countDown(); // 子线程执行完毕后，将doneSignal的计数器减1
                            }
                        };
                        thread.start(); // 启动子线程
                    } else {
                        doneSignal.countDown(); // 如果关键字为空，直接将doneSignal的计数器减1
                    }
                }
                try {
                    doneSignal.await(); // 等待所有子线程执行完毕
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 所有子线程都执行完毕后，继续执行主线程的代码
                Map<String,Object> data=new HashMap<>();
                List<Policy> filteredPolicies=new ArrayList<>();
                List<Policy> policies =policyMapper.getall();
                for (Policy policy : policies) {
                    double relevance1=policy.getRelevance();
                    if(relevance1>=1){
                        String policyprovince = policy.getProvince();
                        if(policyprovince.equals(province)){
                            Long policyid=policy.getPolicyid();
                            double relevance = relevance1+2.0;
                            policyMapper.updatePolicyRelevance(policyid,relevance);
                        }
                        LocalDate update = policy.getUpdatedate();
                        //计算选择的日期与政策日期相差的天数
                        long days = ChronoUnit.DAYS.between(update, selectdate);
                        if (days >= 0 && days <= 181) {
                            // 处理 半年以内的逻辑
                            Long policyid=policy.getPolicyid();
                            double relevance = relevance1+2.0;
                            policyMapper.updatePolicyRelevance(policyid,relevance);
                        } else if (days < 365 && days >= 182) {
                            // 处理 半年到 1 年之间的逻辑
                            Long policyid=policy.getPolicyid();
                            double relevance = relevance1+1.0;
                            policyMapper.updatePolicyRelevance(policyid,relevance);
                        }
                    }
                }
                List<Policy> policies01 =policyMapper.getall();
                for (Policy policy : policies01) {
                    double relevance1=policy.getRelevance();
                    if(relevance1>=1){
                        filteredPolicies.add(policy);
                    }
                }
                //降序排列
                Collections.sort(filteredPolicies, new Policy.PolicyComparator());
                data.put("total",filteredPolicies.size());
                data.put("rows",filteredPolicies);
               for (Policy policy : policies) {
                  policyMapper.updatePolicyRelevance(policy.getPolicyid(),0);
                }
                return Result.success(data);}
            else {
                LambdaQueryWrapper<Policy> wrapper = new LambdaQueryWrapper<>();
                wrapper.like(StringUtils.hasLength(keyinfo), Policy::getPolicybody, keyinfo);

                Page<Policy> page = new Page<>(pageNo, pageSize);
                policyService.page(page, wrapper);

                Map<String, Object> data = new HashMap<>();
                data.put("total", page.getTotal());
                data.put("rows", page.getRecords());

                return Result.success(data);

        }
    }
}




