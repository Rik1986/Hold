package com.gs7.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaren.framework.model.pagination.Pager;
import com.chinaren.framework.model.pagination.PagerUtil;
import com.gs7.dao.CategoryDao;
import com.gs7.dao.CategorySubcategoryDao;
import com.gs7.dao.SubcategoryDao;
import com.gs7.domain.Category;
import com.gs7.domain.CategorySubcategory;
import com.gs7.domain.Subcategory;
import com.gs7.service.CategoryService;
import com.gs7.view.CategoryView;
import com.gs7.view.SubcategoryView;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Resource(name = "categoryDao")
	CategoryDao categoryDao;

	@Resource(name = "subcategoryDao")
	SubcategoryDao subcategoryDao;

	@Resource(name = "categorySubcategoryDao")
	CategorySubcategoryDao categorySubcategoryDao;

	@Override
	public List<CategoryView> getCategories(int status) {
		List<CategoryView> result = new ArrayList<CategoryView>();
		List<Category> list = this.categoryDao.getCategories(status);
		for (Category one : list) {
			CategoryView cv = new CategoryView(one);
			result.add(cv);
		}
		return result;
	}

	@Override
	public CategoryView loadCategory(long id) {
		return new CategoryView(this.categoryDao.load(id));
	}

	@Override
	public List<SubcategoryView> getCategorySubcategoriesByCategoryId(
			long categoryId, int status, long cursor, int size) {
		List<SubcategoryView> result = new ArrayList<SubcategoryView>();
		List<CategorySubcategory> list = this.categorySubcategoryDao
				.getCategorySubcategoriesByCategoryId(categoryId, status,
						cursor, size);
		for (CategorySubcategory one : list) {
			long sort = one.getSort();
			long subcategoryId = one.getSubcategoryId();
			Subcategory subcategory = this.subcategoryDao.load(subcategoryId);
			SubcategoryView sv = new SubcategoryView(subcategory, sort);
			result.add(sv);
		}
		return result;
	}

	@Override
	public SubcategoryView loadSubcategoryByTitle(String title) {
		return new SubcategoryView(this.subcategoryDao.loadByTitle(title));
	}

	@Override
	public Pager getCategorySubcategoriesByCategoryId(long categoryId,
			int status, Pager pager) {
		long cursor = pager.getCursor();
		if (cursor == 0) {
			cursor = Long.MAX_VALUE;
		}
		int size = pager.getNewTotalSize();
		List<CategorySubcategory> list = this.categorySubcategoryDao
				.getCategorySubcategoriesByCategoryId(categoryId, status,
						cursor, size);
		PagerUtil.newResult2Response(list, pager);
		list = pager.getData();
		List<SubcategoryView> svList = new ArrayList<SubcategoryView>();
		for (CategorySubcategory one : list) {
			long sort = one.getSort();
			long subcategoryId = one.getSubcategoryId();
			Subcategory subcategory = this.subcategoryDao.load(subcategoryId);
			SubcategoryView sv = new SubcategoryView(subcategory, sort);
			svList.add(sv);
		}
		pager.setData(svList);
		return pager;
	}

	@Override
	public SubcategoryView createSubcategory(long categoryId,
			Subcategory subcategory) {
		long id = this.subcategoryDao.insert(subcategory);
		CategorySubcategory cs = new CategorySubcategory();
		cs.setCategoryId(categoryId);
		cs.setSubcategoryId(id);
		this.categorySubcategoryDao.insert(cs);
		return new SubcategoryView(this.subcategoryDao.load(id));
	}

}
