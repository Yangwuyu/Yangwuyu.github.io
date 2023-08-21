package com.digiwin.testdap.basic.service;

import com.digiwin.app.dao.*;
import com.digiwin.app.data.*;
import com.digiwin.test.basic.service.IDemoOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoOrderServiceIImpl implements IDemoOrderService {
//把用例都测试一遍
    private static Log log = LogFactory.getLog(DemoOrderServiceIImpl.class);

//    @Autowired
//    private DemoOrderServiceIImpl proxy;
//
//    public Object batchInsert(List<Object> orders) {
//
//        for (Object o : orders) {
//
//            proxy.processSingleInsert(o);
//        }
//
//        return "done";
//    }
//
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public Object processSingleInsert(Object order) {
//
//        return "done";
//    }


    @Autowired()
    @Qualifier("dw-dao")
    private DWDao dao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Object post(DWDataSet dataset) throws Exception {

        DWDataSetOperationOption option = new DWDataSetOperationOption();

        Object result = this.dao.execute(dataset, option);

        return DWServiceResultBuilder.build(result);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Object put(DWDataSet dataset) throws Exception {

        DWDataSetOperationOption option = new DWDataSetOperationOption();

        Object result = this.dao.execute(dataset, option);

        return DWServiceResultBuilder.build(result);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Object delete(List<Object> oids) throws Exception {

        DWDataSetBuilder builder = new DWDataSetBuilder();
        DWDataSet dataset = builder.addTable("demo_order").setDeletedOids(oids).createDataSet();

        // 設定連動刪除信息
        DWDataSetOperationOption option = new DWDataSetOperationOption();
        option.addCascadeDeleting("demo_order", "demo_orderdetail");

        Object result = this.dao.execute(dataset, option);
        return DWServiceResultBuilder.build(result);
    }

    @Override
    public Object getList(DWPagableQueryInfo queryInfo) throws Exception {

        queryInfo.setTableName("demo_order");

        DWDataSetOperationOption option = new DWDataSetOperationOption();
        Object result = this.dao.selectWithPage(queryInfo, option);
        return DWServiceResultBuilder.build(result);
    }

    @Override
    public Object get(List<Object> oids) throws Exception {

        DWQueryInfoBuilder queryInfoBuilder = new DWQueryInfoBuilder();
        DWQueryInfo queryInfo = queryInfoBuilder.setOids(oids).setPrimaryKeyName("orderid").create();

        queryInfo.setTableName("demo_order");

        // 設定連動查詢信息
        DWDataSetOperationOption option = new DWDataSetOperationOption();
        option.addCascadeQuery("demo_order", "demo_orderdetail").getQueryInfo().addOrderBy("seq");

        // 設定自動加入版本字段
        option.setSelectVersionFieldEnabled(true);

        // 設定自動計算單身序號最大值
        option.setCalculateMaxSeqEnabled(true);
        option.setCalculateMaxSeqFieldName("seq");
        DWDataSet dataset = this.dao.select(queryInfo, option);

        // 取出查詢主表 (demo_order) 把對應的 customername 放入主表 row 中
        String customerId;
        String customerName;
        DWDataTable demoOrderTable = dataset.getTables().getPrimaryTable();
        Map<String, String> customerNameList = new HashMap<>();
        for (DWDataRow orderRow : demoOrderTable.getRows()) {

            customerId = orderRow.get("customerid");
            customerName = this.getCustomerName(customerId, customerNameList);

            orderRow.set("customername", customerName);
        }

        return DWServiceResultBuilder.build(dataset);
    }

    public Object putFromRawMap(Map<String, Object> updateInfo) throws Exception {

        DWDataTableBuilder builder = new DWDataTableBuilder()
                .setName("demo_order")
                .addRowOrgDatas(Arrays.asList(updateInfo), DWDataRowState.UPDATE_OPERATION);

        DWDataSet dataset = builder.create().getDataSet();

        DWSQLExecutionResult result = this.dao.execute(dataset);
        int updateCount = result.getUpdateCount();

        return DWServiceResultBuilder.build("update count = " + updateCount);
    }

    /**
     * 取得客戶名稱 (有緩存)
     * @param customerId 客戶編號
     * @param customerNameCaches 客戶名稱緩存
     * @return 客戶名稱
     * @throws Exception 異常
     */
    private String getCustomerName(String customerId, Map<String, String> customerNameCaches) throws Exception {

        if (customerId == null) return null;
        if (customerNameCaches.containsKey(customerId)) return customerNameCaches.get(customerId);

        String customerName = this.invokeCustomerService(customerId);

        return customerName;
    }

    private String invokeCustomerService(String customerId) {

        String name = customerId;

        DWQueryInfo queryCustomer = new DWQueryInfo("demo_customer");
        queryCustomer.getCondition().addEqualInfo("customerid", customerId);

        DWDataRow customer = this.dao.selectOne(queryCustomer);
        if (customer != null) {

            name = customer.get("customername");
        }

        return name;
    }
}
