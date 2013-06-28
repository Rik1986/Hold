<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ page import="com.chinaren.tv.backend.common.utils.AdminIdentityUtil,
com.chinaren.tv.backend.model.AdminUser,
com.chinaren.tv.backend.common.Constants" %>
<%
	AdminIdentityUtil identityUtil =  AdminIdentityUtil.getInstance();
	//AdminIdentityUtil identityUtil = AdminIdentityUtil.getInstance();
	//AdminUser user = (AdminUser)request.getAttribute(Constants.CURRENT_VISITOR);
	AdminUser user = identityUtil.getIdentity(request);
	
%>
<link rel="stylesheet" type="text/css" href="http://image0.cv.itc.cn/c/f.css">
<div class="header" style="z-index: 999999;">
	<div class="tray">
		<div class="usr">
			<!--<em>zhonghuaren</em>，您的身份是 <b>管理员</b>-->
			您好，<em><%=user.getName() %></em>，<!-- 您的身份是 <b>管理员</b> --> <a href="/sys/logout">[退出]</a>
		</div>
		<div class="opts">
			<a href="/backend/sys/home">管理平台</a> 
			<a href="/dc/sys/home">数据平台</a>
			 <!--<a href="#"><b>系统管理</b></a>-->
		</div>
	</div>
	<div class="logos">
		<b class="logo"><a href="#" title="Tv后台管理">Tv后台管理</a></b>
	</div>
</div>