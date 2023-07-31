package com.nanhua.retrieval.service.impl;

import com.nanhua.retrieval.entity.Policy;
import com.nanhua.retrieval.mapper.PolicyMapper;
import com.nanhua.retrieval.service.IPolicyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzq
 * @since 2023-05-15
 */
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements IPolicyService {

    @Autowired
    private PolicyMapper policyMapper;

    @Override
    public String getPolicytitle(Integer policyid) {
        return policyMapper.selecttById(policyid);
    }

    @Override
    public String getPolicybody(Integer policyid) {
        return policyMapper.selectbById(policyid);
    }

    @Override
    public synchronized void searchPolicies(String txt) {
        List<Policy> policies =policyMapper.getall();

        for (Policy policy : policies) {
            double relevance1 = calculateRelevance(txt, policy.getPolicybody());
            if (relevance1 > 0.5) {
                Long policyid=policy.getPolicyid();
                double relevance=policy.getRelevance()+relevance1;
             policyMapper.updatePolicyRelevance(policyid,relevance); }
        }
    }

    private double calculateRelevance(String keyword, String content) {
        int count = 0;
        int index = 0;
        while ((index = content.indexOf(keyword, index)) != -1) {
            count++;
            index += keyword.length();
        }
        double relevance = count*0.25;
        return relevance;
    }

}

