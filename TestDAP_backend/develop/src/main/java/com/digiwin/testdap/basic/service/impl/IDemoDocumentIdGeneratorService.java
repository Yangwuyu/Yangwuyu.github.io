package com.digiwin.testdap.basic.service.impl;

import com.digiwin.app.service.DWService;

public interface IDemoDocumentIdGeneratorService extends DWService {

    /**
     * 取號
     * @return 取號結果
     * @throws Exception 異常
     */
    Object getId(String prefix, String suffix, String dateFormat, int seqLength) throws Exception;
}
