package com.digiwin.testdap.basic.service.impl;

import com.digiwin.app.service.DWService;

import java.util.Map;

/**
 * 演示 使用上下文 服務
 */
public interface IDemoServiceContextService extends DWService {

    Object post(String key);

    String getCurrentGroupName();

    Object test(Map<String, Object> params);
}
