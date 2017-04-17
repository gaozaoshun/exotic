package org.admin.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016/12/29
 */
public class TempSong implements Serializable {
    private List<TempSong> list ;
    private Integer id;
    private Integer lv;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public List<TempSong> getList() {
        return list;
    }

    public void setList(List<TempSong> list) {
        this.list = list;
    }
}
