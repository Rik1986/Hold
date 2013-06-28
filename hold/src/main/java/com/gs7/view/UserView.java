package com.gs7.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.gs7.domain.User;
import com.sohu.zh.model.ThirdUser;

public class UserView extends User {

	/**
     * 
     */
	private static final long serialVersionUID = 2281670825059669115L;

	private List<ThirdUser> bindings = new ArrayList<ThirdUser>();

	private long sort;

	public List<ThirdUser> getBindings() {
		return bindings;
	}

	public void setBindings(List<ThirdUser> bindings) {
		this.bindings = bindings;
	}

	public UserView(User user, long sort) {
		this(user);
		this.sort = sort;
	}

	public UserView(User user) {
		try {
			BeanUtils.copyProperties(this, user);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public UserView(User user, List<ThirdUser> bindings) {
		this(user);
		this.bindings = bindings;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

}
