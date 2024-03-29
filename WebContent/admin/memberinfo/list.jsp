<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/Style1.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript">
			
			function delMemberInfo(memId){
				var isDel = confirm("您确定要删除吗？ ");
				if(isDel){
					//删除
					location.href = "${pageContext.request.contextPath}/admin?method=delMemberInfo&memId="+memId;
				}
			}
</script>
</HEAD>
<body>
	<br>
	<table cellSpacing="1" cellPadding="0" width="100%" align="center"
		bgColor="#f5fafe" border="0">
		<TBODY>
			<tr>
				<td class="ta_01" align="center" bgColor="#afd1f3"><strong>会员列表</strong>
				</TD>
			</tr>
			<tr>
				
			</tr>
			<tr>
				<td class="ta_01" align="center" bgColor="#f5fafe">
					<table cellspacing="0" cellpadding="1" rules="all"
						bordercolor="gray" border="1" id="DataGrid1"
						style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
						<tr
							style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

							<td align="center" width="10%">序号</td>
							<td align="center" width="21%">会员名称</td>
							<td align="center" width="21%">会员密码</td>
							<td width="7%" align="center">删除</td>
						</tr>
						
						<c:forEach items="${memberinfoList }" var="memberinfo" varStatus="vs">
							<tr onmouseover="this.style.backgroundColor = 'white'"
								onmouseout="this.style.backgroundColor = '#F5FAFE';">
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="18%">${vs.count }</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${memberinfo.memUserName }</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${memberinfo.memPassword }</td>
								
								<td align="center" style="HEIGHT: 22px">
									<a href="javascript:void(0);" onclick="delMemberInfo('${memberinfo.memId}')">
										<img src="${pageContext.request.contextPath}/images/i_del.gif"
										width="16" height="16" border="0" style="CURSOR: hand">
									</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</TBODY>
	</table>
</body>
</HTML>

