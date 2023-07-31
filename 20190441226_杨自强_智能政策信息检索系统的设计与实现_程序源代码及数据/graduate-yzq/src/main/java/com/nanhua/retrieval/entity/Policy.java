package com.nanhua.retrieval.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * <p>
 * 
 * </p>
 *
 * @author yzq
 * @since 2023-05-15
 */
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long policyid;

    private String policytitle;

    private String policygrade;

    private String pubagencyid;

    private String pubagency;

    private String pubagencyfullname;

    private String pubnumber;

    private LocalDateTime pubtime;

    private String policytype;

    private String policybody;

    private String province;

    private String city;

    private String policysource;

    private LocalDate updatedate;

    private double relevance;

    public Long getPolicyid() {
        return policyid;
    }

    public void setPolicyid(Long policyid) {
        this.policyid = policyid;
    }
    public String getPolicytitle() {
        return policytitle;
    }

    public void setPolicytitle(String policytitle) {
        this.policytitle = policytitle;
    }
    public String getPolicygrade() {
        return policygrade;
    }

    public void setPolicygrade(String policygrade) {
        this.policygrade = policygrade;
    }
    public String getPubagencyid() {
        return pubagencyid;
    }

    public void setPubagencyid(String pubagencyid) {
        this.pubagencyid = pubagencyid;
    }
    public String getPubagency() {
        return pubagency;
    }

    public void setPubagency(String pubagency) {
        this.pubagency = pubagency;
    }
    public String getPubagencyfullname() {
        return pubagencyfullname;
    }

    public void setPubagencyfullname(String pubagencyfullname) {
        this.pubagencyfullname = pubagencyfullname;
    }
    public String getPubnumber() {
        return pubnumber;
    }

    public void setPubnumber(String pubnumber) {
        this.pubnumber = pubnumber;
    }
    public LocalDateTime getPubtime() {
        return pubtime;
    }

    public void setPubtime(LocalDateTime pubtime) {
        this.pubtime = pubtime;
    }
    public String getPolicytype() {
        return policytype;
    }

    public void setPolicytype(String policytype) {
        this.policytype = policytype;
    }
    public String getPolicybody() {
        return policybody;
    }

    public void setPolicybody(String policybody) {
        this.policybody = policybody;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getPolicysource() {
        return policysource;
    }

    public void setPolicysource(String policysource) {
        this.policysource = policysource;
    }
    public LocalDate getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(LocalDate updatedate) {
        this.updatedate = updatedate;
    }
    public double getRelevance() {
        return relevance;
    }

    public synchronized void setRelevance(double relevance) {
        this.relevance = this.relevance + relevance;
    }

    @Override
    public String toString() {
        return "Policy{" +
            "policyid=" + policyid +
            ", policytitle=" + policytitle +
            ", policygrade=" + policygrade +
            ", pubagencyid=" + pubagencyid +
            ", pubagency=" + pubagency +
            ", pubagencyfullname=" + pubagencyfullname +
            ", pubnumber=" + pubnumber +
            ", pubtime=" + pubtime +
            ", policytype=" + policytype +
            ", policybody=" + policybody +
            ", province=" + province +
            ", city=" + city +
            ", policysource=" + policysource +
            ", updatedate=" + updatedate +
            ", relevance=" + relevance +
        "}";
    }
    public static class PolicyComparator implements Comparator<Policy> {
        @Override
        public int compare(Policy p1, Policy p2) {
            return Double.compare(p2.getRelevance(), p1.getRelevance());
        }
    }
}
