package org.common.model;

import java.util.Date;

/**
 * @author 高灶顺
 * @date 2016/12/21
 */
public class BeginEndTime {
    private String cBeginTime;//创建时间【最小】
    private String cEndTime;//创建时间【最大】
    private String tBeginTime;//更新时间【最小】
    private String tEndTime;//更新时间【最大】

    public String getcBeginTime() {
        return cBeginTime;
    }

    public void setcBeginTime(String cBeginTime) {
        this.cBeginTime = cBeginTime;
    }

    public String getcEndTime() {
        return cEndTime;
    }

    public void setcEndTime(String cEndTime) {
        this.cEndTime = cEndTime;
    }

    public String gettBeginTime() {
        return tBeginTime;
    }

    public void settBeginTime(String tBeginTime) {
        this.tBeginTime = tBeginTime;
    }

    public String gettEndTime() {
        return tEndTime;
    }

    public void settEndTime(String tEndTime) {
        this.tEndTime = tEndTime;
    }
}
