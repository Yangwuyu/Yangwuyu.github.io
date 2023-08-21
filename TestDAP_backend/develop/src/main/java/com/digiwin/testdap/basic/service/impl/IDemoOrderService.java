package com.digiwin.testdap.basic.service.impl;

import com.digiwin.app.dao.DWPagableQueryInfo;
import com.digiwin.app.data.DWDataRowState;
import com.digiwin.app.data.DWDataSet;
import com.digiwin.app.data.validation.DWVDTable;
import com.digiwin.app.service.DWService;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IDemoOrderService extends DWService {

    /**
     * 新增 (C)
     * @param dataset 數據集(單頭和單身)
     * @return 結果
     */
    Object post(@DWVDTable(name = "demo_order", operations = DWDataRowState.CREATE_OPERATION)
                @DWVDTable(name = "a", operations = DWDataRowState.CREATE_OPERATION)
                @DWVDTable(name = "demo_orderdetail", operations = DWDataRowState.CREATE_OPERATION)
                        DWDataSet dataset) throws Exception;

    /**
     * 更新 (U)
     * @param dataset 數據集(單頭和單身)
     * @return 結果
     */
    Object put(@DWVDTable(name = "demo_order", operations = DWDataRowState.UPDATE_OPERATION)
               @DWVDTable(name = "demo_orderdetail", operations = {DWDataRowState.CREATE_OPERATION,
                       DWDataRowState.UPDATE_OPERATION, DWDataRowState.DELETE_OPERATION})
                       DWDataSet dataset) throws Exception;

    /**
     * 刪除 (D)
     * @param oids 單頭主鍵清單
     * @return 結果
     */
    Object delete(List<Object> oids) throws Exception;

    /**
     * 取得清單(只有單頭/分頁查詢) (R)
     * @param queryInfo 查詢信息
     * @return 結果
     */
    Object getList(DWPagableQueryInfo queryInfo) throws Exception;

    /**
     * 取單頭詳情(包含單頭和對應單身的數據, 單身不分頁) (R)
     * @param oids 單頭主鍵清單
     * @return 數據集
     * @throws Exception 異常
     */
    Object get(List<Object> oids) throws Exception;
}
