package com.gs7.domain;

import com.chinaren.framework.model.LongPrimaryObject;

public class Subcategory extends LongPrimaryObject {

	private static final long serialVersionUID = 2287430143116721400L;

	private String title;

	private String descn;

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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
