package com.gs7.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaren.framework.model.pagination.Pager;
import com.chinaren.framework.model.pagination.PagerUtil;
import com.gs7.dao.BindingDao;
import com.gs7.dao.SubcategoryUserDao;
import com.gs7.dao.UserBindingDao;
import com.gs7.dao.UserDao;
import com.gs7.domain.Binding;
import com.gs7.domain.SubcategoryUser;
import com.gs7.domain.User;
import com.gs7.domain.UserBinding;
import com.gs7.service.ThirdCloudServer;
import com.gs7.service.UserService;
import com.gs7.view.UserView;
import com.sohu.zh.model.ThirdUser;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	UserDao userDao;

	@Resource(name = "bindingDao")
	BindingDao bindingDao;

	@Resource(name = "userBindingDao")
	UserBindingDao userBindingDao;

	@Resource(name = "subcategoryUserDao")
	SubcategoryUserDao subcategoryUserDao;

	@Override
	public User getUser(long id) {
		return userDao.load(id);
	}

	@Override
	public User createByThirdId(String ip, long thirdId) throws Exception {
		ThirdUser thirdUser = ThirdCloudServer.getThirdUser(thirdId);
		User user = new User();
		user.setDesc(thirdUser.getDescription());
		user.setImg(thirdUser.getIcon());
		user.setNick(thirdUser.getNick());
		user.setSalt(new Random().nextInt(100000));
		user.setIp(ip);
		user.setSex(thirdUser.getGender());
		long uid = userDao.insert(user);
		user.setId(uid);
		System.out.println("================uid:" + uid);
		UserBinding userBinding = new UserBinding();
		userBinding.setThirdId(thirdId);
		userBinding.setUid(uid);
		userBinding.setSort(uid);
		userBindingDao.insert(userBinding);
		Binding binding = new Binding();
		binding.setThirdId(thirdId);
		binding.setUid(uid);
		binding.setProvider(thirdUser.getProvider());
		binding.setIdentity(thirdUser.getIdentity());
		bindingDao.insert(binding);
		return user;
	}

	@Override
	public User findUserByThirdId(long thirdId) {
		Binding binding = bindingDao.findUserByThirdId(thirdId);
		if (binding == null) {
			return null;
		}
		return userDao.load(binding.getUid());
	}

	@Override
	public void bindByThirdId(long uid, long thirdId) throws Exception {
		ThirdUser thirdUser = ThirdCloudServer.getThirdUser(thirdId);
		UserBinding userBinding = new UserBinding();
		userBinding.setThirdId(thirdId);
		userBinding.setUid(uid);
		userBinding.setSort(uid);
		userBindingDao.insert(userBinding);
		Binding binding = new Binding();
		binding.setThirdId(thirdId);
		binding.setUid(uid);
		binding.setProvider(thirdUser.getProvider());
		binding.setIdentity(thirdUser.getIdentity());
		bindingDao.insert(binding);

	}

	@Override
	public void deleteBinding(long uid, int provider) throws Exception {
		List<UserBinding> userBindings = userBindingDao
				.loadAllBindingByUser(uid);
		if (userBindings != null && userBindings.size() > 1) {

			for (UserBinding userBinding : userBindings) {
				ThirdUser thirdUser = ThirdCloudServer.getThirdUser(userBinding
						.getThirdId());
				if (thirdUser.getProvider() == provider) {
					userBindingDao.deleteBinding(uid, thirdUser.getId());
					bindingDao.deleteBinding(uid, thirdUser.getId());
				}
			}
		}
	}

	@Override
	public List<ThirdUser> loadAllBindingByUser(long uid) throws Exception {
		
		
		List<UserBinding> userBindings = userBindingDao
				.loadAllBindingByUser(uid);
		List<ThirdUser> thirdusers = new ArrayList<ThirdUser>();
		if (userBindings != null) {

			for (UserBinding userBinding : userBindings) {
				thirdusers.add(ThirdCloudServer.getThirdUser(userBinding
						.getThirdId()));

			}
		}
		return thirdusers;
	}

	@Override
	public List<UserView> getSubcategoryUsers(long subcategoryId, int status,
			long cursor, int size) {
		List<UserView> result = new ArrayList<UserView>();
		List<SubcategoryUser> list = this.subcategoryUserDao
				.getSubcategoryUsers(subcategoryId, status, cursor, size);
		for (SubcategoryUser one : list) {
			long sort = one.getCtime();
			long uid = one.getUserId();
			User user = this.userDao.load(uid);
			UserView uv = new UserView(user, sort);
			result.add(uv);
		}
		return result;
	}

	@Override
	public Pager getSubcategoryUsers(long subcategoryId, int status, Pager pager) {
		long cursor = pager.getCursor();
		if (cursor == 0) {
			cursor = Long.MAX_VALUE;
		}
		int size = pager.getNewTotalSize();
		List<SubcategoryUser> list = this.subcategoryUserDao
				.getSubcategoryUsers(subcategoryId, status, cursor, size);
		PagerUtil.newResult2Response(list, pager);
		list = pager.getData();
		List<UserView> uvlist = new ArrayList<UserView>();
		for (SubcategoryUser one : list) {
			long sort = one.getCtime();
			long uid = one.getUserId();
			User user = this.userDao.load(uid);
			UserView uv = new UserView(user, sort);
			uvlist.add(uv);
		}
		pager.setData(uvlist);
		return pager;
	}

}
