package com.nanhua.retrieval.mapper;

import com.nanhua.retrieval.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanhua.retrieval.entity.Policy;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
@Mapper
public interface HistoryMapper extends BaseMapper<History>  {
    List<History> getbyuserId(int userId);
    @Insert("INSERT INTO history(userId, policyId, viewDate) VALUES(#{userId}, #{policyId}, #{viewDate})")
    void save(History history);
}
