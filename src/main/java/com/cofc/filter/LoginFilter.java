//package com.cofc.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class LoginFilter implements Filter {
//	protected FilterConfig filterConfig;
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		this.filterConfig = filterConfig;
//	}
//
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) resp;
//		String requestUrl = request.getRequestURI();
//		if (requestUrl.indexOf("/login.do") > -1) {
//			if(null == request.getSession().getAttribute("loginer")){
//				response.sendRedirect(request.getContextPath() + "/login.do");
//			}else{
//				chain.doFilter(request, response);
//			}
//		} else {
//			chain.doFilter(request, response);
//		}
//	}
//
//	@Override
//	public void destroy() {
//		this.filterConfig = null;
//	}
//
//	public void setFilterConfig(FilterConfig filterConfig) {
//		this.filterConfig = filterConfig;
//	}
//}
