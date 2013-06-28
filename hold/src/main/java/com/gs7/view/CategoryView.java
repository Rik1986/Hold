package com.gs7.view;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.gs7.domain.Category;

public class CategoryView extends Category {

	private static final long serialVersionUID = 8036229973000761662L;

	public CategoryView(Category c) {
		try {
			BeanUtils.copyProperties(this, c);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
