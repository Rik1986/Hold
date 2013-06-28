package com.gs7.domain;

import com.chinaren.framework.model.LongBaseObject;
import com.chinaren.framework.model.pk.LongPair;

public class SubcategoryUser extends LongBaseObject<LongPair> {

	private static final long serialVersionUID = -2383486171402600400L;

	private long id;

	private long subcategoryId;

	private long userId;

	private int status;

	@Override
	public LongPair getPair() {
		if (this.pair == null) {
			this.pair = new LongPair(id, subcategoryId);
		}
		return this.pair;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
