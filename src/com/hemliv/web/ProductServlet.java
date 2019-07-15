package com.hemliv.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.hemliv.domain.MemberInfo;
import com.hemliv.domain.Order;
import com.hemliv.domain.OrderItem;
import com.hemliv.domain.Product;
import com.hemliv.domain.ProductSort;
import com.hemliv.service.ProductService;
import com.hemliv.utils.CommonsUtils;
import com.hemliv.vo.Cart;
import com.hemliv.vo.CartItem;
import com.hemliv.vo.PageBean;


public class ProductServlet extends BaseServlet {
	
	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		if("index".equals(methodName)) {
			index(request,response);
		}else if("productList".equals(methodName)) {
			productList(request,response);
		}else if("productSortList".equals(methodName)) {
			productSortList(request,response);
		}else if("productInfo".equals(methodName)) {
			productInfo(request,response);
		}else if("searchProduct".equals(methodName)) {
			searchProduct(request,response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	*/
	
	//获得我的订单
	public void myOrders(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		HttpSession session = request.getSession();

		MemberInfo memberinfo = (MemberInfo) session.getAttribute("memberinfo");
		if(memberinfo==null) {
			//没有登录
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		ProductService service = new ProductService();
		// 查询该用户的所有的订单信息(单表查询orders表)
		// 集合中的每一个Order对象的数据是不完整的 缺少List<OrderItem> orderItems数据
		List<Order> orderList = service.findAllOrders(memberinfo.getMemId());
		// 循环所有的订单 为每个订单填充订单项集合信息
		if (orderList != null) {
			for (Order order : orderList) {
				// 获得每一个订单的oid
				String oid = order.getOid();
				// 查询该订单的所有的订单项---mapList封装的是多个订单项和该订单项中的商品的信息
				List<Map<String, Object>> mapList = service.findAllOrderItemByOid(oid);
				// 将mapList转换成List<OrderItem> orderItems
				for (Map<String, Object> map : mapList) {

					try {
						// 从map中取出count subtotal 封装到OrderItem中
						OrderItem item = new OrderItem();
						// item.setCount(Integer.parseInt(map.get("count").toString()));
						BeanUtils.populate(item, map);
						// 从map中取出pimage pname shop_price 封装到Product中
						Product product = new Product();
						BeanUtils.populate(product, map);
						// 将product封装到OrderItem
						item.setProduct(product);
						// 将orderitem封装到order中的orderItemList中
						order.getOrderItems().add(item);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}

				}

			}
		}

		// orderList封装完整了
		request.setAttribute("orderList", orderList);

		request.getRequestDispatcher("/order_list.jsp").forward(request, response);

	}
	
	//确认订单---更新收获人信息+在线支付
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1、更新收货人信息
		Map<String, String[]> properties = request.getParameterMap();
		Order order = new Order();
		try {
			BeanUtils.populate(order, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		ProductService service = new ProductService();
		service.updateOrderAdrr(order);
		
		response.sendRedirect(request.getContextPath()+"/ordersucess.jsp");
	}	
	//提交订单
	public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得session
		HttpSession session = request.getSession();
		
		//判断用户是否已经登录 未登录下面代码不执行
		MemberInfo memberinfo = (MemberInfo) session.getAttribute("memberinfo");
		if(memberinfo==null) {
			//没有登录
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		
		//目的：封装好一个Order对象 传递给service层
		Order order = new Order();
		
		//1、private String oid;//该订单的订单号
		String oid = CommonsUtils.getUUID();
		order.setOid(oid);
		//2、private Date ordertime;//下单时间
		order.setOrdertime(new Date());
		//3、private double total;//该订单的总金额
		//获得session中的购物车
		Cart cart = (Cart) session.getAttribute("cart");
		double total = cart.getTotal();
		order.setTotal(total);
		//4、private int state;//订单支付状态 1代表已付款 0代表未付款
		order.setState(0);
		//5、private String address;//收货地址
		order.setAddress(null);
		//6、private String name;//收货人
		order.setName(null);
		//7、private String telephone;//收货人电话
		order.setTelephone(null);
		//8、private MemberInfo memberinfo;//该订单属于哪个用户
		order.setMemberinfo(memberinfo);
		
		//9、该订单中有多少订单项List<OrderItem> orderItems = new ArrayList<OrderItem>();
		//获得购物车中的购物项的集合map
		Map<String, CartItem> cartItems = cart.getCartItems();
		for(Map.Entry<String, CartItem> entry : cartItems.entrySet()) {
			//取出每一个购物项
			CartItem cartItem = entry.getValue();
			//创建新的订单项
			OrderItem orderItem = new OrderItem();
			//1、private String itemid;//订单项的id
			orderItem.setItemid(CommonsUtils.getUUID());
			//2、private int count;//订单项内商品的购买数量
			orderItem.setCount(cartItem.getBuyNum());
			//3、private double subtotal;//订单项小计
			orderItem.setSubtotal(cartItem.getSubtotal());
			//4、private Product product;//订单项内部的商品
			orderItem.setProduct(cartItem.getProduct());
			//5、private Order order;//该订单项属于哪个订单
			orderItem.setOrder(order);
			
			//将该订单项添加到订单的订单项集合中
			order.getOrderItems().add(orderItem);
			
		}
		
		//order对象封装完毕
		//传递数据到service层
		ProductService service = new ProductService();
		service.submitOrder(order);
		
		session.setAttribute("order", order);

		//页面跳转
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");

	}
	//清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");

		//跳转回cart.jsp
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	//删除一条购物车记录
	public void delProFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得要删除的item的proId
		String proId = request.getParameter("proId");
		// 删除session中的购物车中的购物项集合中的item
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Map<String, CartItem> cartItems = cart.getCartItems();
			// 需要修改总价
			cart.setTotal(cart.getTotal() - cartItems.get(proId).getSubtotal());
			// 删除
			cartItems.remove(proId);
			cart.setCartItems(cartItems);

		}

		session.setAttribute("cart", cart);

		// 跳转回cart.jsp
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	//添加商品到购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		ProductService service = new ProductService();
		
		//获得要放入购物车的商品的id
		String proId = request.getParameter("proId");
		//获得该商品的购买数量
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));
		//获得product对象
		Product product = service.findProductInfoById(proId);
		//计算小计
		double subtotal = product.getProPrice()*buyNum;
		
		//封装CartItem
		CartItem items = new CartItem();
		items.setProduct(product);
		items.setBuyNum(buyNum);
		items.setSubtotal(subtotal);
		
		//获得购物车
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null){
			cart = new Cart();
		}
		
		//将购物项放到车中---key是pid
		//先判断购物车中是否已将包含此购物项了 ----- 判断key是否已经存在
		//如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作
		Map<String, CartItem> cartItems = cart.getCartItems();

		double newsubtotal = 0.0;

		if(cartItems.containsKey(proId)){
			//取出原有商品的数量
			CartItem cartItem = cartItems.get(proId);
			int oldBuyNum = cartItem.getBuyNum();
			oldBuyNum+=buyNum;
			cartItem.setBuyNum(oldBuyNum);
			cart.setCartItems(cartItems);
			//修改小计
			//原来该商品的小计
			double oldsubtotal = cartItem.getSubtotal();
			//新买的商品的小计
			newsubtotal = buyNum*product.getProPrice();
			cartItem.setSubtotal(oldsubtotal+newsubtotal);

		}else{
			//如果车中没有该商品
			cart.getCartItems().put(product.getProId(), items);
			newsubtotal = buyNum*product.getProPrice();
		}

		//计算总计
		double total = cart.getTotal()+newsubtotal;
		cart.setTotal(total);
		//将车再次访问session
		session.setAttribute("cart", cart);
		
		//直接跳转到购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
	//显示首页功能
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service = new ProductService();
		
		//准备商品类别信息
		//List<ProductSort> productSortList = service.findAllProductSort();
		//request.setAttribute("productSortList", productSortList);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	//根据类别--显示某类的全部商品
	public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得proSid
		String proSid = request.getParameter("proSid");
		
		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr==null) currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 10;
		
		ProductService service = new ProductService();
		PageBean pageBean = service.findProductListByProSid(proSid,currentPage,currentCount);
		
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("proSid", proSid);
		
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
	
	//显示商品类别
	public void productSortList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		ProductService service = new ProductService();
		
		
		List<ProductSort> productSortList = service.findAllProductSort();
		Gson gson = new Gson();
		String json = gson.toJson(productSortList);

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
	}
	
	//商品具体信息
	public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String proId = request.getParameter("proId");
		
		ProductService service = new ProductService();
		Product product = service.findProductInfoById(proId);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}
	
	//查询商品
	public void searchProduct(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		//获得关键字
		String word = request.getParameter("word");
		
		//查询该关键字的所有商品
		ProductService service = new ProductService();
		List<Object> productList = null;
		try {
			productList = service.findProductByWord(word);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//["xiaomi","huawei",""...]
		
		//使用json的转换工具将对象或集合转成json格式的字符串
		/*JSONArray fromObject = JSONArray.fromObject(productList);
		String string = fromObject.toString();
		System.out.println(string);*/
		
		Gson gson = new Gson();
		String json = gson.toJson(productList);
	
		
		response.setContentType("text/html;charset=UTF-8");
		
		response.getWriter().write(json);
		
		
	}
}
