package com.digiwin.testdap.basic.service;

import com.digiwin.testdap.basic.service.impl.IDemoDocumentIdGeneratorService;
import com.digiwin.util.DocumentIdGenerator;
import com.digiwin.util.DocumentIdSetting;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 測試取號服務實現
 */
public class DemoDocumentIdGeneratorServiceImpl implements IDemoDocumentIdGeneratorService {

    @Autowired
    private DocumentIdGenerator idService;

    /**
     * 取號
     * @return 取號結果
     * @throws Exception 異常
     */
    public Object getId(String prefix, String suffix, String dateFormat, int seqLength) throws Exception {

        DocumentIdSetting setting = new DocumentIdSetting("demo_order", "orderid");
        if (prefix != null && prefix.length() > 0) setting.setPrefix(prefix + "-");
        if (suffix != null && suffix.length() > 0) setting.setSuffix("-" + suffix);
        setting.setPattern(dateFormat);
        setting.setNumber(5);
        setting.setSerialIdLength(seqLength);

        return idService.getIdList(setting);
    }
}
