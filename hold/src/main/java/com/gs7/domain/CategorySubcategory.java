package com.gs7.domain;

import com.chinaren.framework.common.annotation.PageCursor;
import com.chinaren.framework.model.LongBaseObject;
import com.chinaren.framework.model.pk.LongPair;

public class CategorySubcategory extends LongBaseObject<LongPair> {

	private static final long serialVersionUID = 2147613703991506854L;

	private long id;

	private long categoryId;

	private long subcategoryId;

	@PageCursor
	private long sort;

	private int status;

	@Override
	public LongPair getPair() {
		if (this.pair == null) {
			this.pair = new LongPair(id, categoryId);
		}
		return this.pair;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(long subcategoryId) {
		this.subcategoryId = subcategoryId;
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

	public void setId(long id) {
		this.id = id;
	}

}
