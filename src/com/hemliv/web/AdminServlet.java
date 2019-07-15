package com.hemliv.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.naming.factory.BeanFactory;

import com.google.gson.Gson;
import com.hemliv.domain.MemberInfo;
import com.hemliv.domain.Order;
import com.hemliv.domain.Product;
import com.hemliv.domain.ProductSort;
import com.hemliv.service.AdminMemeberInfoService;
import com.hemliv.service.AdminOrderService;
import com.hemliv.service.AdminProductService;
import com.hemliv.service.AdminProductSortService;
import com.hemliv.vo.Condition;

public class AdminServlet extends BaseServlet {
	
	// 管理员登录
	public void checkAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String userPw = request.getParameter("userPw");
		
		//System.out.print(userPw);
		
		if(userName.equals("admin")&&userPw.equals("admin")) {
			System.out.println(userName);
			response.sendRedirect(request.getContextPath() + "/admin/home.jsp");
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
		}
		//response.sendRedirect(request.getContextPath() + "/admin?method=memberinfoList");

	}
	// 删除会员
	public void delMemberInfo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String memId = request.getParameter("memId");
		
		AdminMemeberInfoService service = new AdminMemeberInfoService();
		service.delMemberInfo(memId);

		
		response.sendRedirect(request.getContextPath() + "/admin?method=memberinfoList");
		
	}
	
	//会员列表
	public void memberinfoList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		AdminMemeberInfoService service = new AdminMemeberInfoService();
		List<MemberInfo> memberinfoList = service.findAllMemberInfoList();

		request.setAttribute("memberinfoList", memberinfoList);

		request.getRequestDispatcher("/admin/memberinfo/list.jsp").forward(request, response);
	}
	//根据订单id查询订单项和商品信息
	public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String oid = request.getParameter("oid");
		AdminOrderService service = new AdminOrderService();
		List<Map<String,Object>> mapList = service.findOrderInfoByOid(oid);
		
		
		Gson gson = new Gson();
		String json = gson.toJson(mapList);
		System.out.println(json);
		
		response.setContentType("text/html;charset=UTF-8");
		
		response.getWriter().write(json);
		
	}
	//订单列表
	public void orderList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		AdminOrderService service = new AdminOrderService();
		List<Order> orderList= null;
		try {
			orderList = service.findAllOrderList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("orderList", orderList);

		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
	}
	//商品查询
	public void searchProduct(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//获取表单数据
		Map<String, String[]> properties = request.getParameterMap();
		//封装散装的数据到 vo实体
		Condition condition = new Condition();
		try {
			BeanUtils.populate(condition, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//传递实体到service层
		AdminProductService service = new AdminProductService();
		List<Product> productList = null;
		try {
			productList = service.findProductListByCondition(condition);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取所有类别信息
		List<ProductSort> productsortList = null;
		try {
			productsortList = service.findAllProductSort();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("productsortList", productsortList);
		request.setAttribute("condition", condition);
		request.setAttribute("productList", productList);

		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	// 商品分类添加
	public void addProductSort(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//获取数据
		Map<String, String[]> properties = request.getParameterMap();
		
		//封装数据
		ProductSort productsort = new ProductSort();
		try {
			BeanUtils.populate(productsort, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//手动封装proSid
		productsort.setProSid(UUID.randomUUID().toString());
		
		//传递数据给service层
		
		AdminProductSortService service = new AdminProductSortService();
		try {
			service.addProductSort(productsort);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath()+"/admin?method=productsortList");
	}

	// 商品分类删除
	public void delProductSort(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// 获取proSid
		String proSid = request.getParameter("proSid");

		// 传递proSid到service
		AdminProductSortService service = new AdminProductSortService();
		try {
			service.delProductSort(proSid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/admin?method=productsortList");
	}

	// 商品分类编辑
	public void updateProductSort(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//获取数据
		Map<String, String[]> properties = request.getParameterMap();
		
		//封装数据
		ProductSort productsort = new ProductSort();
		try {
			BeanUtils.populate(productsort, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		//传递数据给service层
		
		AdminProductSortService service = new AdminProductSortService();
		try {
			service.updateProductSort(productsort);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath()+"/admin?method=productsortList");
	}
	
	// 商品分类编辑---数据回显
	public void updateProductSortUI(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// 获取proSid
		String proSid = request.getParameter("proSid");

		// 根据proSid查询类别信息
		AdminProductSortService service = new AdminProductSortService();
		ProductSort productsort = null;
		try {
			productsort = service.findProductSortById(proSid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("productsort", productsort);

		request.getRequestDispatcher("/admin/productsort/edit.jsp").forward(request, response);
	}
	//商品分类信息
	public void productsortList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminProductSortService service = new AdminProductSortService();
		List productsortList = null;
		
			productsortList = service.findAllProductSort();
		
		request.setAttribute("productsortList", productsortList);
		
		request.getRequestDispatcher("/admin/productsort/list.jsp").forward(request, response);
		
		
	}
	//删除商品
	public void delProduct(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//获取proId
		String proId = request.getParameter("proId");

		// 传递proId到service
		AdminProductService service = new AdminProductService();
		try {
			service.delProductByid(proId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/admin?method=productList");

	}
	//显示所有商品列表
	public void productList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// 传递请求到service层
		AdminProductService service = new AdminProductService();
		List productList = null;
		try {
			productList = service.findAllProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 将productList放入request域
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}
	//编辑商品信息----回显
	public void updateProductUI(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//获取proId
		String proId = request.getParameter("proId");

		//传递proId查询商品信息
		AdminProductService service = new AdminProductService();
		Product product = null;
		try {
			product = service.findProductByproId(proId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取所有类别信息
		List<ProductSort> productsortList = null;
		try {
			productsortList = service.findAllProductSort();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("productsortList", productsortList);
		request.setAttribute("product", product);
		
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}
	
	// 查询所有类别
	public void findAllProductSort(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//提供一个List<Category> 转成json字符串
		//AdminProductService service = (AdminProductService) BeanFactory.getBean("adminProductService");
		AdminProductSortService service = new AdminProductSortService();
		List<ProductSort> productSortList = service.findAllProductSort();

		Gson gson = new Gson();
		String json = gson.toJson(productSortList);

		response.setContentType("text/html;charset=UTF-8");

		response.getWriter().write(json);
	}
	

	
}
