package com.digiwin.testdap.basic.service.impl;

import com.digiwin.app.data.DWDataSet;
import com.digiwin.app.service.DWService;

public interface IDemoDaoFunctions extends DWService {

    Object cascadeQuery();

    Object cascadeDelete(String orderid);

    Object managementField(Boolean insertMode, Boolean mgmtFieldEnabled, Boolean ignoreCustomValueInOptions);

    Object autoincrementValue(DWDataSet dataset) throws Exception;

    Object batchSubmit(boolean insertMode, boolean returnGeneratedKeys);

    Object rowCondition(boolean updateMode);
}
