<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ page
	import="com.chinaren.tv.backend.common.utils.AdminIdentityUtil,
com.chinaren.tv.backend.model.AdminUser,
com.chinaren.tv.backend.common.Constants"%>
<%

	AdminIdentityUtil identityUtil = AdminIdentityUtil.getInstance();
	//AdminUser user = (AdminUser)request.getAttribute(Constants.CURRENT_VISITOR);
	AdminUser user = identityUtil.getIdentity(request);
	
%>


<div class="navbar-inner">
	<div class="container">
		<a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse"> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span> </a> <a class="brand" href="#">积分计划管理后台</a>
		<div class="nav-collapse collapse navbar-inverse-collapse">

			<ul class="nav pull-right">
				<li><a href="#">您好，<em><%=user.getName() %></em></a>
				</li>
				<li class="divider-vertical"></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">GO <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/backend/sys/home">运营后台</a>
						</li>
						<li><a href="/dc/sys/home">数据平台</a>
						</li>						
						<li class="divider"></li>
						<li><a href="/sys/logout">退出</a>
						</li>
					</ul></li>
			</ul>
		</div>
		<!-- /.nav-collapse -->
	</div>
</div>



