package cn.com.yves.service;

import java.sql.SQLException;

import cn.com.yves.bean.UserBean;
import cn.com.yves.dao.UserDaoInf;
import cn.com.yves.dao.impl.UserDao;

public class UserService {

	private UserDaoInf uDao = new UserDao();

	public UserBean showUserInfo(String userBeanId) {
		UserBean uBean = null;
		try {
			uBean = uDao.getUserBeanById(userBeanId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uBean;
	}

	/**
	 * 验证是是否存在User by userName
	 * 
	 * @param userId
	 * @return
	 */
	public boolean validateUserByName(String userName, String userPwd) {
		boolean bool = false;
		try {
			bool = uDao.validateUserBean(userName, userPwd);
		} catch (SQLException e) {
			// 异常处理
		}
		return bool;
	}
}
