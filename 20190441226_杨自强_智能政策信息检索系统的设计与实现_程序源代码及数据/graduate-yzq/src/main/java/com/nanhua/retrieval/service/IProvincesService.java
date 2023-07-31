package com.nanhua.retrieval.service;

import com.nanhua.retrieval.entity.Provinces;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzq
 * @since 2023-05-02
 */
public interface IProvincesService extends IService<Provinces> {

    Map<Integer, String> getProvinces();
}
