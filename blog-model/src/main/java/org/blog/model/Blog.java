package org.blog.model;

import org.admin.model.SysUser;

import java.io.Serializable;
import java.util.Date;

public class Blog implements Serializable {

    private Integer id;

    private String title;

    private String cover;

    private String summary;

    private String content;

    private Integer likeNum;

    private Integer lookNum;

    private Integer talkNum;

    private String keyWords;

    private Integer typeId;

    private Integer createUser;

    private Date createTime;

    private Integer dr;

    private Date ts;

    private Type type;

    private SysUser sysUser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getLookNum() {
        return lookNum;
    }

    public void setLookNum(Integer lookNum) {
        this.lookNum = lookNum;
    }

    public Integer getTalkNum() {
        return talkNum;
    }

    public void setTalkNum(Integer talkNum) {
        this.talkNum = talkNum;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords == null ? null : keyWords.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", likeNum=" + likeNum +
                ", lookNum=" + lookNum +
                ", talkNum=" + talkNum +
                ", keyWords='" + keyWords + '\'' +
                ", typeId=" + typeId +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", dr=" + dr +
                ", ts=" + ts +
                ", type=" + type +
                ", sysUser=" + sysUser +
                '}';
    }
}