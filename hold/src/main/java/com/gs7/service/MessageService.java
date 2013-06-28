package com.gs7.service;

import java.util.List;

import com.chinaren.framework.model.pagination.Pager;
import com.gs7.domain.Message;
import com.gs7.view.MessageView;

public interface MessageService {

	public List<MessageView> getSubcategoryMessages(long subcategoryId,
			int status, long cursor, int size);

	public Pager getSubcategoryMessages(long subcategoryId, int status,
			Pager pager);

	public MessageView createMessage(long subcategoryId, Message message);
}
