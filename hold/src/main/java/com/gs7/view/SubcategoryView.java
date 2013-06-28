package com.gs7.view;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.gs7.domain.Subcategory;

public class SubcategoryView extends Subcategory {

	private static final long serialVersionUID = -7778730221779445782L;

	private long sort;

	public SubcategoryView(Subcategory s, long sort) {
		this(s);
		this.sort = sort;
	}

	public SubcategoryView(Subcategory s) {
		try {
			BeanUtils.copyProperties(this, s);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

}
