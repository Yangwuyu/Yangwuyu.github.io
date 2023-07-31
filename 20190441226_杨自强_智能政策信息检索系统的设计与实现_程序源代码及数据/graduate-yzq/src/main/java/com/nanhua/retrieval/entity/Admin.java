package com.nanhua.retrieval.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yzq
 * @since 2023-05-22
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String adminname;

    private String adminavatar;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }
    public String getAdminavatar() {
        return adminavatar;
    }

    public void setAdminavatar(String adminavatar) {
        this.adminavatar = adminavatar;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
            "id=" + id +
            ", adminname=" + adminname +
            ", adminavatar=" + adminavatar +
            ", password=" + password +
        "}";
    }
}
