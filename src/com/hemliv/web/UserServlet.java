package com.hemliv.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.hemliv.domain.MemberInfo;
import com.hemliv.service.MemberInfoService;
import com.hemliv.utils.CommonsUtils;


public class UserServlet extends BaseServlet {
	
	//注册
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取数据
		//设置request的编码--防止出现中文乱码（只适合post方式）
		request.setCharacterEncoding("UTF-8");
		
		//String memusername = request.getParameter("memusername");
		
		//2.将散装的数据封装到JavaBean
		//使用beanutils进行自动映射封装
		//beanutils工作原理 ：将map中数据根据key与实体的属性的对应关系封装
		//只要key的名字与实体的属性的名字一样就自动封装到实体中
		Map<String, String[]> properties = request.getParameterMap();
		MemberInfo memberinfo = new MemberInfo();
		try {
			BeanUtils.populate(memberinfo, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		} 
		//现在这个位置memberinfo对象已经封装好好了
		//手动封装uid---uuid--随机的不重复的字符串32位--java代码生成后为36位
		//memberinfo.setMemId(UUID.randomUUID().toString());
		memberinfo.setMemId(CommonsUtils.getUUID());
		
		//3.将参数传递给Service层
		
		MemberInfoService service = new MemberInfoService();
		boolean isRegisterSuccess = false;
		try {
			isRegisterSuccess = service.register(memberinfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//是否注册成功
		if (isRegisterSuccess) {
			// 跳转到注册成功页面
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} else {
			// 跳转到失败的提示页面
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}
	}
	
	//登录
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		//1.获取用户名密码
		String memusername = request.getParameter("memusername");
		String mempassword = request.getParameter("mempassword");
		
		//2.调用函数
		MemberInfoService service = new MemberInfoService();
		//boolean isLoginSuccess = false;
		MemberInfo memberinfo = null;
		try {
			memberinfo = service.login(memusername,mempassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//通过user是否为null判断用户名和密码是否正确
		if(memberinfo!=null) {
			//用户名和密码正确
			//登陆成功，跳转页面首页(重定向)
			session.setAttribute("memberinfo", memberinfo);
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else {
			//用户名和密码错误
			//跳回当前login.jsp
			//使用转发回到login.jsp，request域中存储错误信息
			request.setAttribute("loginInfo", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
	}
	
	
	//用户名校验
	public void checkMemUserName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得表单提交的数据
		String memUsername = request.getParameter("memUserName");
		System.out.println(memUsername);
		//传递memUsername到service
		MemberInfoService service = new MemberInfoService();
		boolean isExist = false;
		try {
			isExist = service.checkmemUsername(memUsername);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = "{\"isExist\":" + isExist + "}";
		
		response.getWriter().write(json);
	}
	
	//用户退出
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	session.removeAttribute("memberinfo");
	response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
}
