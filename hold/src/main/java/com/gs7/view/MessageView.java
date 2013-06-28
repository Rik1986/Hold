package com.gs7.view;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.gs7.domain.Message;
import com.gs7.domain.User;

public class MessageView extends Message {

	private static final long serialVersionUID = -8465342255714257336L;

	private UserView userView;

	private long sort;

	public MessageView(Message m, long sort) {
		this(m);
		this.sort = sort;
	}

	public MessageView(Message m) {
		try {
			BeanUtils.copyProperties(this, m);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public MessageView(Message m, long sort, User u) {
		this(m, sort);
		this.userView = new UserView(u);
	}

	public UserView getUserView() {
		return userView;
	}

	public void setUserView(UserView userView) {
		this.userView = userView;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

}
