package com.gs7.domain;

import com.chinaren.framework.common.annotation.AutowareInit;
import com.chinaren.framework.common.annotation.AutowareMap;
import com.chinaren.framework.model.LongPrimaryObject;

@AutowareInit
public class UserBinding extends LongPrimaryObject {

    /**
     * 
     */
    private static final long serialVersionUID = 8169042415958896595L;

    @AutowareMap(isDbColumn = false)
    protected long ctime;

    @AutowareMap(isDbColumn = false)
    protected long utime;

    private long uid;

    private long thirdId;

    private long sort;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getThirdId() {
        return thirdId;
    }

    public void setThirdId(long thirdId) {
        this.thirdId = thirdId;
    }

    public long getSort() {
        return sort;
    }

    public void setSort(long sort) {
        this.sort = sort;
    }

}
