package com.hemliv.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.hemliv.domain.Product;
import com.hemliv.domain.ProductSort;
import com.hemliv.service.AdminProductService;
import com.hemliv.utils.CommonsUtils;

public class AdminUpdateProductServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		request.setCharacterEncoding("UTF-8");
		// 1. 获取数据
		Map<String, String[]> properties = request.getParameterMap();

		// 2 ...封装数据
		Product product = new Product();
		try {
			BeanUtils.populate(product, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 此位置product已经封装完毕
		// 需要手动封装表单中没有 的数据

		// private String proImage;
		product.setProImage("image/bedroom10.jpg");

		// 3..传递数据给service层

		AdminProductService service = new AdminProductService();
		try {
			service.updateProduct(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 跳转到列表页面
		response.sendRedirect(request.getContextPath() + "/adminproductlist");	
		*/
		
		//目的：收集表单的数据 封装一个Product实体 将上传图片存到服务器磁盘上
		
		Product product = new Product();

		// 收集数据的容器
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 创建文件上传核心对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解析request获得文件项对象集合

			List<FileItem> parseRequest = upload.parseRequest(request);
			for (FileItem item : parseRequest) {
				// 判断是否是普通表单项
				boolean formField = item.isFormField();
				if (formField) {
					// 普通表单项 获得表单的数据 封装到Product实体中
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");

					map.put(fieldName, fieldValue);

				} else {
					// 文件上传项 获得文件名称 获得文件的内容
					String fileName = item.getName();
					String path = this.getServletContext().getRealPath("upload");
					
					//String path = "E:\\Program Files\\workpace\\Hemliv_1\\WebContent\\upload";
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(path + "/" + fileName);// I:/xxx/xx/xxx/xxx.jpg
					IOUtils.copy(in, out);
					in.close();
					out.close();
					// item.delete();

					map.put("proImage", "upload/" + fileName);
				}

			}

			BeanUtils.populate(product, map);
			// 是否product对象封装数据完全
			// private ProductSort productsort;
			ProductSort productsort = new ProductSort();
			productsort.setProSid(map.get("proSid").toString());
			product.setProductsort(productsort);

			// 将product传递给service层
			// AdminProductService service = (AdminProductService)
			// BeanFactory.getBean("adminProductService");
			AdminProductService service = new AdminProductService();
			service.updateProduct(product);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 跳转到列表页面
		response.sendRedirect(request.getContextPath() + "/admin?method=productList");
		

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
