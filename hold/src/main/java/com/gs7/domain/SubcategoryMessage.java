package com.gs7.domain;

import com.chinaren.framework.model.LongBaseObject;
import com.chinaren.framework.model.pk.LongPair;

public class SubcategoryMessage extends LongBaseObject<LongPair> {

	private static final long serialVersionUID = -8591449501413496297L;

	private long id;

	private long subcategoryId;

	private long messageId;

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

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
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
