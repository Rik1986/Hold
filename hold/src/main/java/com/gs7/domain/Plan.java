package com.gs7.domain;

import com.chinaren.framework.common.annotation.AutowareInit;
import com.chinaren.framework.model.LongPrimaryObject;

@AutowareInit
public class Plan extends LongPrimaryObject {

    /**
     * 
     */
    private static final long serialVersionUID = 7263488040204021549L;

    public static final int status_nomorl = 0;

    public static final int status_private = 1;

    public static final int status_delete = 2;

    private long uid;

    private String title;

    private String desc;

    private long begin;

    private long end;

    private int frequency;// 频率

    private long subcategoryId;

    private int goodNum;

    private int badNum;

    private int status;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    

    public long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public int getBadNum() {
        return badNum;
    }

    public void setBadNum(int badNum) {
        this.badNum = badNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
