package com.digiwin.testdap.basic.service.impl;

import com.digiwin.app.service.DWService;
import com.digiwin.app.service.DWServiceResult;

public interface IDemoCoreComponent extends DWService {

    DWServiceResult postDWDao(String sql, boolean disableTenantByOption);

    DWServiceResult postDWDataSet(Boolean disableTenant) throws Exception;

    DWServiceResult postDWQueryInfo(Boolean disableTenant);
}
