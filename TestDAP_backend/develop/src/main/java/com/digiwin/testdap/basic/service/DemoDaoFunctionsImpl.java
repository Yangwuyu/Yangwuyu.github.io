package com.digiwin.testdap.basic.service;

import com.digiwin.app.dao.*;
import com.digiwin.app.dao.filter.DWSQLManagementFieldFilter;
import com.digiwin.app.data.*;
import com.digiwin.testdap.basic.service.impl.IDemoDaoFunctions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class DemoDaoFunctionsImpl implements IDemoDaoFunctions {

    private static Log log = LogFactory.getLog(DemoDaoFunctionsImpl.class);

    @Autowired()
    @Qualifier("dw-dao")
    private DWDao dao;

    @Override
    public Object cascadeQuery() {

        DWPagableQueryInfo queryInfo = new DWPagableQueryInfo("demo_order");
        queryInfo.setPageSize(5);

        DWDataSetOperationOption option = new DWDataSetOperationOption();
        option.addCascadeQuery("demo_order", "demo_orderdetail");
        option.addCascadeQuery("demo_order", "demo_customer");

        return DWServiceResultBuilder.build(dao.selectWithPage(queryInfo, option));
    }

    @Override
    public Object cascadeDelete(String orderid) {

        DWPagableQueryInfo queryInfo = new DWPagableQueryInfo("demo_order");
        queryInfo.setPageSize(5);

        DWDataSetOperationOption option = new DWDataSetOperationOption();
        option.addCascadeDeleting("demo_order", "demo_orderdetail");

        DWDataSet dataSet = new DWDataSet();
        dataSet.newTable("demo_order").newRow().set("orderid", orderid).delete();

        return DWServiceResultBuilder.build(dao.execute(dataSet, option));
    }

    @Override
    public Object managementField(Boolean insertMode, Boolean mgmtFieldEnabled, Boolean ignoreCustomValueInOptions) {

        Map<String, Object> result = new LinkedHashMap<>();

        String id = "test-mgmtField-001";

        DWDataSet dataSet = new DWDataSet();
        DWDataTable dt = dataSet.newTable("demo_order");

        if (!Boolean.FALSE.equals(insertMode)) {

            dt.newRow().set("orderid", id).delete();
            dt.newRow().set("orderid", id).set("createuser", "java").set("modifyuser", "java");
        }
        else {

            DWDataRow row = dt.newRow().set("orderid", id).set("totalcount", 999);
            DWDataRowState.setRowState(row, DWDataRowState.UPDATE_OPERATION);
        }

        DWDataSetOperationOption option = new DWDataSetOperationOption();
        option.setSqlOrderTypeOfExecution(DWSQLOptionsBuilder.SQL_ORDER_TYPE_DELETE_HIGH_PRIORITY);

        result.put("default-mgmtFieldEnabled", option.isManagementFieldEnabled());
        result.put("default-ignoreCustomValueInOptions", DWSQLManagementFieldFilter.isIgnoreCustomValue(option));

        option.setManagementFieldEnabled(!Boolean.FALSE.equals(mgmtFieldEnabled));
        DWSQLManagementFieldFilter.setIgnoreCustomValueInOptions(option, !Boolean.FALSE.equals(ignoreCustomValueInOptions));

        result.put("id", id);
        result.put("mgmtFieldEnabled", option.isManagementFieldEnabled());
        result.put("ignoreCustomValueInOptions", DWSQLManagementFieldFilter.isIgnoreCustomValue(option));
        result.put("executeResult", this.dao.execute(dataSet, option));

        DWQueryInfo queryInfo = new DWQueryInfo("demo_order");
        queryInfo.getCondition().addEqualInfo("orderid", id);
        result.put("queryResult", this.dao.selectOne(queryInfo));

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public Object autoincrementValue(DWDataSet dataset) throws Exception {

        DWDataSetOperationOption option = new DWDataSetOperationOption();

        // 第一層帶到第二層測試
        option.getInsertOption().getAutoIncrementOption().addSource("test_ai_a", "test_ai_b", "aid");

        // 第二層帶到第三層測試 (b auto increment 到 c.bid)
        option.getInsertOption().getAutoIncrementOption().addSource("test_ai_b", "test_ai_c", "bid")
                // 第二層帶到第三層測試 (b.aid 到 c.aid)
                .addColumnMapping("aid", "aid");

        Object result = dao.execute(dataset, option);

        return result;
    }

    @Override
    public Object batchSubmit(boolean insertMode, boolean returnGeneratedKeys) {

        int count = 50;

        // 建立一千筆數據
        DWDataRow row;
        DWDataSet dataSet = new DWDataSet();
        DWDataTable table = dataSet.newTable("test_ai_a");
        for (int i = 0; i < count; i++) {

            row = table.newRow().set("data", "test data_" + i);

            if (!insertMode) {

                row.set("id", i + 1).delete();
            }
        }

        DWDataSetOperationOption option = new DWDataSetOperationOption();

        // 指定 test_ai_a 表啟用批次模式且需要返回產生的鍵值
        if (returnGeneratedKeys) option.getTableStatementOption().enableBatchModeAndReturnGeneratedKeys();
        else option.getTableStatementOption().enableBatchMode();

        DWSQLExecutionResult result = this.dao.execute(dataSet, option);

        return result;
    }

    @Override
    public Object rowCondition(boolean updateMode) {

        DWDataSet dataset = new DWDataSet();
        DWDataTable dt = dataset.newTable("demo_order");

        DWBatchCondition batchCondition = new DWBatchCondition();
        batchCondition.addFieldInfo("orderid", DWQueryValueOperator.In, "1", "2");

        DWDataSetOperationOption option = new DWDataSetOperationOption();
        if (updateMode) {
            dt.newRow().set("orderid", "1");
            dt.newRow().set("orderid", "2");

            dt.update(batchCondition)
                    .set("status", "F")
                    .set("orderdate", new Date());

            option.getUpdateOption().enableBatchCondition();
        }
        else {

            dt.delete(batchCondition);

            option.getDeleteOption().enableBatchCondition();
        }

        DWDataSetSqlInfo dataSetSqlInfo = ((DWDaoImpl)dao).getDialect().parse(dataset, option);

        Map<String, Object> result = new HashMap<>();
        result.put("sqlInfo", dataSetSqlInfo.stream().collect(
                                    LinkedHashMap::new,
                                    (map, item) -> map.put(item.getParametersAsList(), item.getSql()),
                                    Map::putAll));
        result.put("executeResult", this.dao.execute(dataset, option));

        return DWServiceResultBuilder.build(result);
    }
}
