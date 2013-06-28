package com.gs7.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaren.framework.model.pagination.Pager;
import com.chinaren.framework.model.pagination.PagerUtil;
import com.gs7.dao.MessageDao;
import com.gs7.dao.SubcategoryMessageDao;
import com.gs7.dao.UserDao;
import com.gs7.domain.Message;
import com.gs7.domain.SubcategoryMessage;
import com.gs7.domain.User;
import com.gs7.service.MessageService;
import com.gs7.view.MessageView;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource(name = "subcategoryMessageDao")
	SubcategoryMessageDao subcategoryMessageDao;

	@Resource(name = "messageDao")
	MessageDao messageDao;

	@Resource(name = "userDao")
	UserDao userDao;

	@Override
	public List<MessageView> getSubcategoryMessages(long subcategoryId,
			int status, long cursor, int size) {
		List<MessageView> result = new ArrayList<MessageView>();
		List<SubcategoryMessage> list = this.subcategoryMessageDao
				.getSubcategoryMessages(subcategoryId, status, cursor, size);
		for (SubcategoryMessage one : list) {
			long mid = one.getMessageId();
			long sort = one.getCtime();
			Message m = this.messageDao.load(mid);
			long uid = m.getUserId();
			User u = this.userDao.load(uid);
			MessageView mv = new MessageView(m, sort, u);
			result.add(mv);
		}
		return result;
	}

	@Override
	public Pager getSubcategoryMessages(long subcategoryId, int status,
			Pager pager) {
		long cursor = pager.getCursor();
		if (cursor == 0) {
			cursor = Long.MAX_VALUE;
		}
		int size = pager.getNewTotalSize();
		List<SubcategoryMessage> list = this.subcategoryMessageDao
				.getSubcategoryMessages(subcategoryId, status, cursor, size);
		PagerUtil.newResult2Response(list, pager);
		list = pager.getData();
		List<MessageView> mvlist = new ArrayList<MessageView>();
		for (SubcategoryMessage one : list) {
			long mid = one.getMessageId();
			long sort = one.getCtime();
			Message m = this.messageDao.load(mid);
			long uid = m.getUserId();
			User u = this.userDao.load(uid);
			MessageView mv = new MessageView(m, sort, u);
			mvlist.add(mv);
		}
		pager.setData(mvlist);
		return pager;
	}

	public MessageView createMessage(long subcategoryId, Message message) {
		long id = this.messageDao.insert(message);
		SubcategoryMessage sm = new SubcategoryMessage();
		sm.setSubcategoryId(subcategoryId);
		sm.setMessageId(id);
		this.subcategoryMessageDao.insert(sm);
		return new MessageView(this.messageDao.load(id));
	}

}
