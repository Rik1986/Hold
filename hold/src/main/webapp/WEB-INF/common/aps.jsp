<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>


	<div class="apt js-title js-open" data-name="频道管理"><h6>频道管理</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>	
	<div class="apc js-subcontent" data-name="频道管理">
			<ul class="cpli">
			<li class=""><a  href="toAddChannel">创建频道</a></li>
			<li class=""><a  href="getRootChannels">所有频道</a></li>
			<li class=""><a  href="getDefaultChannels?cursor=0&size=100">默认频道</a></li>
			<!-- 
			<c:forEach items="${items}" var="channel" varStatus="varStatus">
				<li class=""><a target="ifr" href="getChildrenChannels?channelId=${channel.id}&cursor=0&size=10">${channel.title}</a></li>
			</c:forEach>
			 -->
			</ul>
	</div>
	
	<div class="apt js-title js-open" data-name="推荐到微信"><h6>微信推荐</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>	
	<div class="apc js-subcontent" data-name="推荐到微信">
			<ul class="cpli">
			<% 				
				Calendar c1 = Calendar.getInstance();
			%>
			<li class=""><a href="getRecommendToWeixinlist?date=<%=c1.get(Calendar.YEAR) %>-<%=c1.get(Calendar.MONTH) + 1 %>-<%=c1.get(Calendar.DAY_OF_MONTH) %>">推荐到微信</a></li>			
			</ul>
	</div>
	
	<div class="apt js-title js-open" data-name="数据源类型管理"><h6>数据源类型管理</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	<div class="apc js-subcontent" data-name="数据源类型管理">
		<ul class="cpli">
			<li class=""><a href="getCrawlers?page=1&size=10">数据源类型定义</a></li>
			<li class=""><a href="getErrorCrawlerInstances">异常数据源列表</a></li>
			<li class=""><a href="getUnreadAdvices">用户建议列表</a></li>
			<li class=""><a href="toTempCrawlerInstancePage">新建视频</a></li>
			<li class=""><a href="accessToken">数据源授权</a></li>
		</ul>
	</div>
	
	<div class="apt js-title js-open" data-name="数据报表"><h6>数据报表</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	
	<div class="apc js-subcontent" data-name="数据报表">
		<ul class="cpli">			
			<% 				
				Calendar c = Calendar.getInstance();
				
				Calendar week = Calendar.getInstance();
				week.add(Calendar.WEEK_OF_YEAR, -1);
				
				Calendar lastDay = Calendar.getInstance();
				lastDay.add(Calendar.DAY_OF_YEAR, -1);
			%>
			<li class=""><a href="doTotalProduceReport?startDate=<%=week.get(Calendar.YEAR) %>-<%=week.get(Calendar.MONTH) + 1 %>-<%=week.get(Calendar.DAY_OF_MONTH) %>
					&endDate=<%=lastDay.get(Calendar.YEAR) %>-<%=lastDay.get(Calendar.MONTH) + 1 %>-<%=lastDay.get(Calendar.DAY_OF_MONTH) %>">总体用户分析</a></li>
			<li class=""><a href="doChannelVideoReport?date=<%=c.get(Calendar.YEAR) %>-<%=c.get(Calendar.MONTH) + 1 %>-<%=c.get(Calendar.DAY_OF_MONTH) %>">频道数据</a></li>
			<%SimpleDateFormat newdf = new SimpleDateFormat("yyyy-MM-dd");
			  String date = newdf.format(new Date().getTime()- 86400000);
			%>
			<li class=""><a href="doPromotionReport?date=<%=date%>">渠道数据</a></li>
			<li class=""><a href="doRecommendVideoRank?date=<%=date%>&topNum=20">点击排行</a></li>
			<li class=""><a href="doShareMessageRank?date=<%=date%>&topNum=20">分享排行</a></li>
			<!-- <li class=""><a href="#">用户数据</a></li> -->
		</ul>
	</div>
<!-- 
	<div class="apt js-title js-open" data-name="碎片管理"><h6>碎片管理</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>	
	
	<div class="apc js-subcontent" data-name="碎片管理">
			<ul class="cpli">
			<li class=""><a href="getSpalllist">碎片列表</a></li>	
			</ul>
	</div> 
 -->	
<script type="text/javascript">
	kola("product.tvbackend.common.Menu", function (Menu) {
		Menu();
	});
</script>
