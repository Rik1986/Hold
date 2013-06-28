package com.gs7.service;

import java.util.List;

import com.chinaren.framework.model.pagination.Pager;
import com.gs7.domain.User;
import com.gs7.view.UserView;
import com.sohu.zh.model.ThirdUser;

public interface UserService {

	/**
	 * 通过thirdId创建，或者返回已有的用户
	 * 
	 * @param thirdId
	 * @return
	 * @throws Exception
	 */
	public User createByThirdId(String ip, long thirdId) throws Exception;

	/**
	 * 通过thirdId找出已有的用户
	 * 
	 * @param thirdId
	 * @return
	 */
	public User findUserByThirdId(long thirdId);

	/**
	 * 通过uid找出已有的用户
	 * 
	 * @param uid
	 * @return
	 */
	public User getUser(long uid);

	/**
	 * 绑定第三方账号
	 * 
	 * @param thirdId
	 * @throws Exception
	 */
	public void bindByThirdId(long uid, long thirdId) throws Exception;

	/**
	 * 删除绑定账号
	 * 
	 * @param provider
	 * @throws Exception
	 */
	public void deleteBinding(long uid, int provider) throws Exception;

	public List<ThirdUser> loadAllBindingByUser(long uid) throws Exception;

	public List<UserView> getSubcategoryUsers(long subcategoryId, int status,
			long cursor, int size);

	public Pager getSubcategoryUsers(long subcategoryId, int status, Pager pager);

}
