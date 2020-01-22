package com.cofc.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.BackUser;
public class LoginHandlerIntercepter implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 说明处在编辑的页面
		BackUser currUser = (BackUser)request.getSession().getAttribute("loginer");
		if (currUser != null) {
			// 登陆成功的用户
			return true;
		} else {
			// 没有登陆，转向登陆界面
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return false;
		}
	}
}
