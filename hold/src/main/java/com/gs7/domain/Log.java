package com.gs7.domain;

import com.chinaren.framework.common.annotation.AutowareInit;
import com.chinaren.framework.model.LongPrimaryObject;

@AutowareInit
public class Log extends LongPrimaryObject {

	public static final int success_fail=0;
	public static final int success_ok=1;
	public static final int status_nomorl=0;
	public static final int status_delete=1;
	
	
    /**
     * 
     */
    private static final long serialVersionUID = 2147498963136459526L;

    private long pid;// plan id

    private long date;

    private String content;

    private long uid;

    private int success;

    private int status;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

}
