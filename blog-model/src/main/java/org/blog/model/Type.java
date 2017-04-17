package org.blog.model;

import org.admin.model.SysUser;

import java.io.Serializable;
import java.util.Date;

public class Type implements Serializable {
    private Integer id;

    private String name;

    private Integer lv;

    private Date createTime;

    private Integer createUser;

    private Integer dr;

    private Date ts;

    private SysUser sysUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lv=" + lv +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", dr=" + dr +
                ", ts=" + ts +
                ", sysUser=" + sysUser +
                '}';
    }
}