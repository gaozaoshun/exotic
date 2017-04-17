package org.admin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysAutho implements Serializable{
    private Integer id;

    private String name;

    private String icon;

    private String uri;

    private String descp;

    private Integer menuId;

    private Date createTime;

    private Integer createUser;

    private Integer dr;

    private Date ts;

    private SysUser sysUser;

    private SysMenu sysMenu;

    private List<SysAutho> sysAuthos;


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp == null ? null : descp.trim();
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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
        this.createUser = createUser ;
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

    public SysMenu getSysMenu() {
        return sysMenu;
    }

    public void setSysMenu(SysMenu sysMenu) {
        this.sysMenu = sysMenu;
    }

    public List<SysAutho> getSysAuthos() {
        return sysAuthos;
    }

    public void setSysAuthos(List<SysAutho> sysAuthos) {
        this.sysAuthos = sysAuthos;
    }
}