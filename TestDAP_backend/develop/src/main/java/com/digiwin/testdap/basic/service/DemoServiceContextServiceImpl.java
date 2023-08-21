package com.digiwin.testdap.basic.service;

import com.digiwin.app.service.DWServiceContext;
import com.digiwin.test.basic.service.IDemoServiceContextService;

import java.util.Map;
import java.util.Objects;

public class DemoServiceContextServiceImpl implements IDemoServiceContextService {

    @Override
    public Object post(String key) {

        Objects.requireNonNull(key);

        return DWServiceContext.getContext().getProfile().get(key);
    }

    @Override
    public String getCurrentGroupName() {//获取群组名称

        return DWServiceContext.getContext().getGroupName();
    }

    @Override
    public Object test(Map<String, Object> params) {
        return params;
    }
}
