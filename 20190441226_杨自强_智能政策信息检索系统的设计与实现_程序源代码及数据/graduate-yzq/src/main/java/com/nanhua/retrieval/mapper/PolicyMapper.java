package com.nanhua.retrieval.mapper;

import com.nanhua.retrieval.entity.Policy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzq
 * @since 2023-05-15
 */
@Mapper
public interface PolicyMapper extends BaseMapper<Policy> {
    String selecttById(int policyid);
    String selectbById(int policyid);
    List<Policy> getall();
    void updatePolicyRelevance(long policyid, double relevance);
}
