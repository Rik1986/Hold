<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ page import="java.util.*"%>	
<%@page import="java.text.SimpleDateFormat"%>
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
			<span class="icon-bar"></span> <span class="icon-bar"></span> </a> <a class="brand" href="/dc/sys/home">数据分析平台</a>
		<div class="nav-collapse collapse navbar-inverse-collapse">
			<ul class="nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">总体数据分析 <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/dc/overview/user/getUserData">用户总体分析</a>
						</li>
						<li><a href="/dc/overview/getPromotionData">渠道数据分析</a>
						</li>
						<li><a href="/dc/overview/forward/getTvForwardData">视频播放分析</a>
						</li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">pv数据分析 <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li class="nav-header">客户端pv</li>
						<li><a href="/dc/pv/getPvDetailData">每日pv</a>
						</li>
						<li><a href="/dc/pv/getClientVersionData">版本分布</a>
						</li>
						<!--<li><a href="#">地域分布</a>  -->
						</li>
						<li class="divider"></li>
						<li class="nav-header">wap pv</li>
						<li><a href="/dc/pv/wap/getWapPvTotalData">每日pv</a>
						</li>	
						<!--  <li><a href="/dc/pv/wap/getWapPvData">每日pv</a> 
						</li> -->
						<li><a href="/dc/pv/wap/getWapCcData">每日cc</a>
						</li>
						<li class="nav-header">web pv</li>
						<li><a href="/dc/pv/web/getWebPvTotalData">每日pv</a>
						<li><a href="/dc/pv/web/getWebCcData">每日cc</a>
						</li>
						<li class="nav-header">渠道统计</li>	
						<li><a href="/dc/pv/wap/getWapPromotionTotalData">WAP & WEB 渠道</a>
						
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">榜单排行 <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/dc/rank/doRecommendVideoRank">点击排行</a>
						</li>
						<li><a href="/dc/rank/doShareMessageRank">分享排行</a>
						</li>
					</ul></li>
			</ul>
			<ul class="nav pull-right">
				<li><a href="#">您好，<em><%=user.getName() %></em></a>
				</li>
				<li class="divider-vertical"></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">GO <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/backend/sys/home">运营后台</a>
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



