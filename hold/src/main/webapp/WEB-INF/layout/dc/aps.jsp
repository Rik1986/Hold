<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 				
		Calendar c = Calendar.getInstance();
				
		Calendar week = Calendar.getInstance();
		week.add(Calendar.WEEK_OF_YEAR, -1);
				
		Calendar lastDay = Calendar.getInstance();
		lastDay.add(Calendar.DAY_OF_YEAR, -1);

		SimpleDateFormat newdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = newdf.format(new Date().getTime()- 86400000);
		String curDate =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		String curHour=new SimpleDateFormat("HH").format(new Date());
		int hour = Integer.parseInt(curHour) - 1;
%>
	
	<div class="apt js-title js-open" data-name="数据报表"><h6>总体数据分析</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	
	<div class="apc js-subcontent" data-name="数据报表">
		<ul class="cpli">	
		    <li class=""><a href="#" onclick="alert('努力建设中...')">总体用户分析</a></li>	
		    <li class=""><a href="#" onclick="alert('努力建设中...')">频道数据分析</a></li>	

			<!-- <li class=""><a href="/dc/statistics/doTotalProduceReport?startDate=<%=week.get(Calendar.YEAR) %>-<%=week.get(Calendar.MONTH) + 1 %>-<%=week.get(Calendar.DAY_OF_MONTH) %>
					&endDate=<%=lastDay.get(Calendar.YEAR) %>-<%=lastDay.get(Calendar.MONTH) + 1 %>-<%=lastDay.get(Calendar.DAY_OF_MONTH) %>">总体用户分析</a></li>
			<li class=""><a href="/dc/statistics/doChannelVideoReport?date=<%=c.get(Calendar.YEAR) %>-<%=c.get(Calendar.MONTH) + 1 %>-<%=c.get(Calendar.DAY_OF_MONTH) %>">频道数据分析</a></li>
			 -->
			<li class=""><a href="#" onclick="alert('努力建设中...')">播放数据分析</a></li>			

			<li class=""><a href="/dc/statistics/doPromotionReport?date=<%=date%>">渠道数据分析</a></li>
			<!-- <li class=""><a href="#">用户数据</a></li> -->
		</ul>
	</div>
	
	<div class="apt js-title js-open" data-name="PV数据分析"><h6>PV数据分析</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>	
	
	<div class="apc js-subcontent" data-name="PV数据分析">
		<ul class="cpli">		
			<li class=""><a href="/dc/pv/getPvDetail?date=<%=curDate%>">每日PV</a></li>
			<li class=""><a href="/dc/pv/getClientVersion?date=<%=date%>">客户端版本分布</a></li>
		</ul>
	</div>	
	
	<div class="apt js-title js-open" data-name="WAP数据分析"><h6>WAP数据分析</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	<div class="apc js-subcontent" data-name="WAP数据分析">
		<ul class="cpli">	
			<li class=""><a href="/dc/wap/getWapPvByDate?date=<%=curDate%>">每日PV</a></li>
			<!-- --><li class=""><a href="/dc/wap/getWapCcByDate?date=<%=curDate%>">每日CC</a></li> 
			<li class=""><a href="/dc/wap/getWapCcByHour?date=<%=curDate%>&hour=<%=hour%>">每小时CC</a></li> 
		</ul>
	</div>
	<div class="apt js-title js-open" data-name="榜单数据"><h6>榜单数据</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	<div class="apc js-subcontent" data-name="榜单数据">
		<ul class="cpli">		
			<li class=""><a href="/dc/rank/doRecommendVideoRank?date=<%=date%>&topNum=20">点击排行</a></li>
			<li class=""><a href="/dc/rank/doShareMessageRank?date=<%=date%>&topNum=20">分享排行</a></li>
		</ul>
	</div>	
	
<script type="text/javascript">
	kola("product.tvbackend.common.Menu", function (Menu) {
		Menu();
	});
</script>
