package com.gs7.domain;

import com.chinaren.framework.model.LongPrimaryObject;

/**
 * 私信
 * 
 * @author Administrator
 * 
 */
public class Message extends LongPrimaryObject {

	private static final long serialVersionUID = -4851440407419603635L;

	private long userId;

	private String content;

	private int status;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
