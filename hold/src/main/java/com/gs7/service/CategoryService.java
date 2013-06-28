package com.gs7.service;

import java.util.List;

import com.chinaren.framework.model.pagination.Pager;
import com.gs7.domain.Subcategory;
import com.gs7.view.CategoryView;
import com.gs7.view.SubcategoryView;

public interface CategoryService {

	public CategoryView loadCategory(long id);

	public SubcategoryView createSubcategory(long categoryId,
			Subcategory subcategory);

	public List<CategoryView> getCategories(int status);

	public List<SubcategoryView> getCategorySubcategoriesByCategoryId(
			long categoryId, int status, long cursor, int size);

	public Pager getCategorySubcategoriesByCategoryId(long categoryId,
			int status, Pager pager);

	public SubcategoryView loadSubcategoryByTitle(String title);

}
