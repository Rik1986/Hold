package com.gs7.domain;

import com.chinaren.framework.common.annotation.AutowareInit;
import com.chinaren.framework.common.annotation.AutowareMap;
import com.chinaren.framework.model.LongPrimaryObject;

@AutowareInit
public class UserPlan extends LongPrimaryObject {

    /**
     * 
     */
    private static final long serialVersionUID = -4457142462135350949L;

    public static int status_nomorl = 0;

    public static int status_private = 1;

    public static int status_delete = 2;

    @AutowareMap(isDbColumn = false)
    protected long ctime;

    @AutowareMap(isDbColumn = false)
    protected long utime;

    private long uid;

    private long pid;

    private long sort;

    private int status;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getSort() {
        return sort;
    }

    public void setSort(long sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
