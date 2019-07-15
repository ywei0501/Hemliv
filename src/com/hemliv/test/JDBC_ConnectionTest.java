package com.hemliv.test;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.hemliv.domain.MemberInfo;
import com.hemliv.domain.ProductSort;
import com.hemliv.utils.C3P0Utils;



public class JDBC_ConnectionTest {

	/**
	 * 查询�?有商品种类信�?
	 */
	@Test
	public void testQueryAll() {
		try {
			//1
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			//2
			String sql = "select * from productsort";
			//3.执行查询操作
			List<ProductSort> prosorts = qr.query(sql, new BeanListHandler<ProductSort>(ProductSort.class));
			//4.遍历查询结果
			for (ProductSort prosort: prosorts) {
				System.out.println(prosort.getProSid()+" : "+prosort.getProSName());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加商品种类信息
	 */
	@Test
	public void testAdd() {
		try {
			//1.创建核心类QueryRunner
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			//2.编写sql语句
			String sql  = "insert into productsort values(null,?)";
			//3.设置参数
			Object[] params = {"四件�?"};
			//4.执行添加操作
			int rows = qr.update(sql,params);
			if (rows > 0) {
				System.out.println("成功");
			}else {
				System.out.println("失败");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
