package com.nanhua.retrieval.service;

import com.nanhua.retrieval.entity.Policy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzq
 * @since 2023-05-15
 */
public interface IPolicyService extends IService<Policy> {
    String getPolicytitle(Integer policyid);

    String getPolicybody(Integer policyid);

    void searchPolicies(String txt);
}
