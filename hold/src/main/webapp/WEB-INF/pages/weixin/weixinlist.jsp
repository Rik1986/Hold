<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ page import="com.chinaren.tv.backend.common.Constants" %>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="http://image0.cv.itc.cn/c/g.css">
<%@include file="/WEB-INF/inc/globalConfig.jsp"%>
</head>
<body>

<div class="main">
	<div class="c1">
		<div class="ap">
			<div class="apt">
				<div class="opt"><a href="/backend/weixin/editSpall" class="btn"><b>编辑碎片</b></a></div>
				<h6>微信推荐</h6>
			</div>
			<div class="apc">
				<div class="form2">
						<div class="tblist">
							<div class="tbl tbl01">
								<form method="post" action="/backend/weixin/getRecommendToWeixinlist">
									日期：<input type="text" name="date" onclick="var t = this; kola('widget.datepicker.Manager', function (Manager) {Manager.datePicker({target: t});})" value="${date }"/>
									<!-- 结束时间：<input type="text" onclick="var t = this; kola('widget.datepicker.Manager', function (Manager) {Manager.datePicker({target: t});})"/> -->
									<input class="btn" type="submit" value="&ensp;查询 &ensp;"/>
								</form>
								<table id="dataSourceTable">
									<thead>
										<tr>
											<th>标题</th>
											<th>URL</th>
											<th>操作</th>
											
										</tr>
									</thead>
									<tbody>
										<c:if test="${not empty items}">
											<c:forEach items="${items}" var="view" varStatus="status">
												<tr class="js-item" style="cursor: pointer;">
													<td>
														 ${view.title}
													</td>
													<td>
														<a href="<%=Constants.YEHUO_URL %>/w/weixin?msgId=${view.msgId}"><%=Constants.YEHUO_URL %>/w/weixin/${view.msgId}</a>
													</td>
													<td>
														<a href="/backend/weixin/deleteRecommendToWeixin?weixinId=${view.id}&date=${date}"><b>删除</b></a>
													</td>
													
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
								
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
