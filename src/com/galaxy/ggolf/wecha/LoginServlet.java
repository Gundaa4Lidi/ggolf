package com.galaxy.ggolf.wecha;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxy.ggolf.jdbc.CommonConfig;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取当前完整路径
		String requestUrl = request.getRequestURL().toString();
		//获取当前路径中的方法
		String requestUri = request.getRequestURI().toString();
		//得到根路径
		String rootPath = requestUrl.replaceAll(requestUri, "");
		String backUri = rootPath+"/Authorize/callback.do";
		backUri = URLEncoder.encode(backUri,"");
		
		String url = "https://open.weixin.qq.com/connect/qrconnect?"+
				"appid="+CommonConfig.WECHA_APPID+
				"&redirect_uri="+backUri+
				"&response_type=code"+
				"&scope=snsapi_login"+
				"&state=123456#wechat_redirect";
		System.out.println("url:"+url);
		response.sendRedirect(url);
	}
}
