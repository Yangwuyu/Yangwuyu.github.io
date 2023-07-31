package com.nanhua.retrieval.service.impl;

import com.nanhua.retrieval.entity.History;
import com.nanhua.retrieval.mapper.HistoryMapper;
import com.nanhua.retrieval.service.IHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {
@Autowired
private HistoryMapper historyMapper;
    @Override
    public List<History> getbyuserId(int userId) {
        return historyMapper.getbyuserId(userId);
    }

}
