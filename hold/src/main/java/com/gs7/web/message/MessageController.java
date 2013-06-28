package com.gs7.web.message;

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
import com.gs7.domain.Message;
import com.gs7.domain.User;
import com.gs7.service.MessageService;
import com.gs7.view.MessageView;
import com.gs7.web.BaseController;
import com.gs7.web.RequestToolKit;

@Controller
@RequestMapping("/m/message")
public class MessageController extends BaseController {
	Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Resource(name = "messageService")
	public MessageService messageService;

	@RequestMapping(value = { "/createMessage" })
	public ModelAndView createSubcategory(
			@RequestParam(value = "subcategoryId", required = true) long subcategoryId,
			@ModelAttribute("_message") Message message,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		User user = RequestToolKit.getCurrentUser(request);
		message.setUserId(user.getId());

		MessageView mv = this.messageService.createMessage(subcategoryId,
				message);

		model.addAllAttributes(ClassUtils.objecttoMap(mv));
		this.successView(model);
		return this.viewNegotiating("", request, response, model);
	}

	@RequestMapping(value = { "/getSubcategoryMessages" })
	public ModelAndView getSubcategoryMessages(
			@RequestParam(value = "subcategoryId", required = true) long subcategoryId,
			@RequestParam(value = "status", required = true) int status,
			@ModelAttribute("_pager") Pager pager, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		pager = this.messageService.getSubcategoryMessages(subcategoryId,
				status, pager);
		model.addAllAttributes(pager.toModelAttribute());
		this.successView(model);
		return this.viewNegotiating("", request, response, model);
	}

}
