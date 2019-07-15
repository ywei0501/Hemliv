package com.hemliv.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hemliv.domain.MemberInfo;
import com.hemliv.utils.C3P0Utils;

public class MemberInfoDao {

	public Long checkmemUsername(String memUsername) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from memberinfo where memusername=?";
		Long query = (Long) runner.query(sql, new ScalarHandler(),memUsername);
		return query;
	}

	public int register(MemberInfo memberinfo) throws SQLException {
		//操作数据库
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		// sql语句
		String sql = "insert into memberinfo values(?,?,?,?,?,?)";
		int update = runner.update(sql, memberinfo.getMemId(), memberinfo.getMemUserName(), memberinfo.getMemPassword(),
				memberinfo.getMemSex(), memberinfo.getMemEmail(), memberinfo.getMemMoblieP());
		return update;
	}

	public MemberInfo login(String memusername, String mempassword) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from memberinfo where memusername=? and mempassword=?";
		return runner.query(sql, new BeanHandler<MemberInfo>(MemberInfo.class), memusername,mempassword);
	}

}
