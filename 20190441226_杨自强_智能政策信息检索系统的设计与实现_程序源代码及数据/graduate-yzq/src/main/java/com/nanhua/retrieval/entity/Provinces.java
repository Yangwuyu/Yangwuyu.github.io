package com.nanhua.retrieval.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yzq
 * @since 2023-05-02
 */
public class Provinces implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer provinceId;

    private String provinceName;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "Provinces{" +
            "provinceId=" + provinceId +
            ", provinceName=" + provinceName +
        "}";
    }
}
