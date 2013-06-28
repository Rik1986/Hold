package com.gs7.web.category;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinaren.framework.common.util.ClassUtils;
import com.chinaren.framework.model.pagination.Pager;
import com.gs7.domain.Subcategory;
import com.gs7.service.CategoryService;
import com.gs7.view.CategoryView;
import com.gs7.view.SubcategoryView;
import com.gs7.web.BaseController;

@Controller
@RequestMapping("/m/category")
public class CategoryController extends BaseController {

	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Resource(name = "categoryService")
	public CategoryService categoryService;

	@RequestMapping(value = { "/createSubcategory" })
	public ModelAndView createSubcategory(
			@RequestParam(value = "categoryId", required = true) long categoryId,
			@ModelAttribute("_subcategory") Subcategory subcategory,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		String title = subcategory.getTitle();
		if (this.categoryService.loadSubcategoryByTitle(title) == null) {
			SubcategoryView sv = this.categoryService.createSubcategory(
					categoryId, subcategory);
			model.addAllAttributes(ClassUtils.objecttoMap(sv));
			this.successView(model);
			return this.viewNegotiating("", request, response, model);
		} else {
			this.successView(model);
			return this.viewNegotiating("", request, response, model);
		}
	}

	@RequestMapping(value = { "/getCategories" })
	public ModelAndView getCategories(
			@RequestParam(value = "status", required = true) int status,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		List<CategoryView> list = this.categoryService.getCategories(status);
		model.addAttribute("result", list);
		this.successView(model);
		return this.viewNegotiating("", request, response, model);
	}

	@RequestMapping(value = { "/getCategorySubcategories" })
	public ModelAndView getCategorySubcategoriesByCategoryId(
			@RequestParam(value = "categoryId", required = true) long categoryId,
			@RequestParam(value = "status", required = true) int status,
			@ModelAttribute("_pager") Pager pager, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		pager = this.categoryService.getCategorySubcategoriesByCategoryId(
				categoryId, status, pager);
		model.addAllAttributes(pager.toModelAttribute());
		this.successView(model);
		return this.viewNegotiating("", request, response, model);
	}

}
