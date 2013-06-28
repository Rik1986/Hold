package com.gs7.domain;

import com.chinaren.framework.model.LongPrimaryObject;

public class Category extends LongPrimaryObject {

	private static final long serialVersionUID = -2623686571549296645L;

	private String title;

	private String descn;

	private long sort;

	private int status;

	private String ext;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
