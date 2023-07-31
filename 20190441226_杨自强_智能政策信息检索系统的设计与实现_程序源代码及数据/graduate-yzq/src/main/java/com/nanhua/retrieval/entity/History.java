package com.nanhua.retrieval.entity;


import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;


/**
 * <p>
 * 
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
public class History implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer policyId;

    private LocalDateTime viewDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }
    public LocalDateTime getViewDate() {
        return viewDate;
    }

    public void setViewDate(LocalDateTime  viewDate) {
        this.viewDate = viewDate;
    }

    @Override
    public String toString() {
        return "History{" +
            "id=" + id +
            ", userId=" + userId +
            ", policyId=" + policyId +
            ", viewDate=" + viewDate +
        "}";
    }
}
