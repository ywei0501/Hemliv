package com.hemliv.service;

import java.sql.SQLException;

import com.hemliv.dao.MemberInfoDao;
import com.hemliv.domain.MemberInfo;

/**
 * 用户数据校验
 * @author Administrator
 *
 */
public class MemberInfoService {
	//用户名校验
	public boolean checkmemUsername(String memUsername) throws SQLException {
		MemberInfoDao dao = new MemberInfoDao();
		Long isExist = dao.checkmemUsername(memUsername);
		return isExist>0?true:false;
	}

	public boolean register(MemberInfo memberinfo) throws SQLException {
		MemberInfoDao dao = new MemberInfoDao();
		int row = dao.register(memberinfo);
		return row>0?true:false;
	}

	public MemberInfo login(String memusername, String mempassword) throws SQLException {
		MemberInfoDao dao = new MemberInfoDao();
		return dao.login(memusername,mempassword);
		
	}

}
