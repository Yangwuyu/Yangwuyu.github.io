package com.digiwin.testdap.basic.service;

import com.digiwin.app.dao.*;
import com.digiwin.app.data.DWDataSet;
import com.digiwin.app.data.DWDataSetOperationOption;
import com.digiwin.app.data.DWDataTable;
import com.digiwin.app.data.DWSQLOptionsBuilder;
import com.digiwin.app.service.DWServiceResult;
import com.digiwin.test.basic.service.IDemoCoreComponent;
import com.digiwin.utils.DWTenantUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class DemoCoreComponentImpl implements IDemoCoreComponent {

    private static Log log = LogFactory.getLog(DemoCoreComponentImpl.class);

    @Autowired()
    @Qualifier("dw-dao")
    private DWDao dao;

    @Override
    public DWServiceResult postDWDao(String sql, boolean disableTenantByOption) {

        Object result;
        if (disableTenantByOption) {//disableTenantByOption 是否开启多租户
            DWDataSetOperationOption option = new DWDataSetOperationOption();
            option.setTenantEnabled(false);

            result = dao.select(option, sql);//dao,执行sql语句的主要组件
            // 注意 - 錯誤的寫法如下
            // dwDao.select(sql, option);
        }
        else {

           sql += DWTenantUtils.getTenantIgnoreTagByColumnName();

           result = dao.select(sql);
        }

        log.info("DemoCoreComponentImpl postDWDao sql = " + sql);
        return DWServiceResultBuilder.build(result);
    }

    @Override
    public DWServiceResult postDWDataSet(Boolean disableTenant) throws Exception {

        DWDataSetOperationOption option = this.createOption(disableTenant);

        String id = "test-core-dataset";//目标数据的id
        log.info("DemoCoreComponentImpl postDWDataSet random id = " + id);

        DWDataSet ds = new DWDataSet();
        DWDataTable dt = ds.newTable("demo_order");//描述目标表
        dt.newRow().set("orderid", id).delete();//指定列
        dt.newRow().set("orderid", id);//报错，记得问

        option.setSqlOrderTypeOfExecution(DWSQLOptionsBuilder.SQL_ORDER_TYPE_DELETE_HIGH_PRIORITY);//设置执行顺序
        DWDataSetSqlInfo sqlInfo = ((DWDaoImpl)dao).getDialect().parse(ds, option);

        DWSQLExecutionResult executeResult = dao.execute(ds, option);

        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("sqlInfo", sqlInfo.stream().map(s -> s.getSql()).toArray());
        result.put("execute result", executeResult);

        return DWServiceResultBuilder.build(result);
    }

    @Override
    public DWServiceResult postDWQueryInfo(Boolean disableTenant) {

        DWDataSetOperationOption option = this.createOption(disableTenant);

        // 1. 一般查詢
        DWQueryInfo queryInfo = new DWQueryInfo("demo_order");//表名
        queryInfo.addSelectField("orderid", "status");
        Object generalQueryResult = this.dao.select(queryInfo, option);

        // 2. 條件查詢
        queryInfo.getCondition().ORJoin()//加条件
            .addFieldInfo("status", DWQueryValueOperator.Equals, "Y")
            .addFieldInfo("status", DWQueryValueOperator.Equals, "N");
        Object conditionalQueryResult = this.dao.select(queryInfo, option);

        DWQueryCondition testCondition = new DWQueryCondition().ORJoin();//用or链接条件
        DWQueryCondition subCondition1 = new DWQueryCondition();//默认用and链接条件
        subCondition1.addEqualInfo("a", 2);
        subCondition1.addEqualInfo("b", 3);
        subCondition1.addEqualInfo("c", 1);

        DWQueryCondition subCondition2 = new DWQueryCondition();
        subCondition2.addEqualInfo("a", 1);
        subCondition2.addEqualInfo("b", 2);

        testCondition.addCondition(subCondition1);
        testCondition.addCondition(subCondition2);

        // 3. 分頁查詢
        DWPagableQueryInfo pagableQueryInfo = new DWPagableQueryInfo("demo_order");
        pagableQueryInfo.setPageSize(2);
        Object pagableQueryResult = this.dao.selectWithPage(pagableQueryInfo, option);

        Map<String, Object> result = new HashMap<>();
        result.put("一般查詢", generalQueryResult);

        result.put("條件查詢", conditionalQueryResult);
        result.put("分頁查詢", pagableQueryResult);

        return DWServiceResultBuilder.build(result);
    }

    private DWDataSetOperationOption createOption(Boolean disableTenant) {

        DWDataSetOperationOption option = new DWDataSetOperationOption();
        option.setTenantEnabled(!Boolean.TRUE.equals(disableTenant));

        return option;
    }
}
