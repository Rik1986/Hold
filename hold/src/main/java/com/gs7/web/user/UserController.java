package com.gs7.web.user;

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
import com.chinaren.framework.common.util.InnerCache;
import com.chinaren.framework.model.pagination.Pager;
import com.gs7.domain.User;
import com.gs7.service.UserService;
import com.gs7.view.UserView;
import com.gs7.web.BaseController;
import com.gs7.web.RequestToolKit;
import com.sohu.zh.model.ThirdUser;

@Controller
@RequestMapping("/m/user")
public class UserController extends BaseController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "userService")
	public UserService userService;

	protected InnerCache cache = InnerCache.getInnerCache();

	@RequestMapping(value = { "/userInfo" })
	public ModelAndView getUserInfo(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		User user = RequestToolKit.getCurrentUser(request);
		List<ThirdUser> bindings = userService.loadAllBindingByUser(user
				.getId());
		model.addAllAttributes(ClassUtils.objecttoMap(new UserView(user,
				bindings)));
		this.successView(model);
		return this.viewNegotiating("", request, response, model);
	}

	@RequestMapping(value = { "/getSubcategoryUsers" })
	public ModelAndView getSubcategoryUsers(
			@RequestParam(value = "subcategoryId", required = true) long subcategoryId,
			@RequestParam(value = "status", required = true) int status,
			@ModelAttribute("_pager") Pager pager, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		pager = this.userService.getSubcategoryUsers(subcategoryId, status,
				pager);
		model.addAllAttributes(pager.toModelAttribute());
		this.successView(model);
		return this.viewNegotiating("", request, response, model);
	}

}
