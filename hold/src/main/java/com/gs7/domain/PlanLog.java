package com.gs7.domain;

import com.chinaren.framework.common.annotation.AutowareInit;
import com.chinaren.framework.common.annotation.AutowareMap;
import com.chinaren.framework.common.annotation.PageCursor;
import com.chinaren.framework.model.LongPrimaryObject;

@AutowareInit
public class PlanLog extends LongPrimaryObject {

    /**
     * 
     */
    private static final long serialVersionUID = -9039991118265741533L;

    public static int status_nomorl = 0;

    public static int status_delete = 1;

    @AutowareMap(isDbColumn = false)
    protected long ctime;

    @AutowareMap(isDbColumn = false)
    protected long utime;

    private long pid;

    private long lid;

    @PageCursor
    private long sort;

    private int status;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getLid() {
        return lid;
    }

    public void setLid(long lid) {
        this.lid = lid;
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
