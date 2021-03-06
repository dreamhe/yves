package cn.com.yves.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.yves.bean.UserBean;
import cn.com.yves.service.UserServiceForServletTest;

public class ShowUserServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		UserServiceForServletTest userService = new UserServiceForServletTest();
		UserBean userBean = userService.showUserInfo("1");
		System.out.println(userBean.getUserName());
		request.setAttribute("userBean", userBean);
		// 重定向数据是否丢失？
		String heyu = (String) request.getAttribute("name");
		// response.sendRedirect("pages/testRedirect.jsp");
		request.getRequestDispatcher("pages/testForward.jsp").forward(request,
				response);

	}
}
