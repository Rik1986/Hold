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
			<li class=""><a  href="/backend/channel/getRootChannels">所有频道</a></li>
			<li class=""><a  href="/backend/channel/getDefaultChannels?cursor=0&size=100">默认频道</a></li>
			<li class=""><a  href="/backend/cover/getCover">封面管理</a></li>
			<li class=""><a  href="/backend/editchannel/getEditChannels?cursor=0&size=100">遥控器</a></li>
			<li class=""><a  href="/backend/recommendchannel/getRecommendChannels?cursor=0&size=100">默认订阅</a></li>
			<!-- 
			<c:forEach items="${items}" var="channel" varStatus="varStatus">
				<li class=""><a target="ifr" href="getChildrenChannels?channelId=${channel.id}&cursor=0&size=10">${channel.title}</a></li>
			</c:forEach>
			 -->
			</ul>
	</div>
	
	<div class="apt js-title js-open" data-name="评论管理"><h6>评论管理</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	<div class="apc js-subcontent" data-name="评论配置">
			<ul class="cpli">
			<li class=""><a href="/backend/comment/getRootChannels">评论配置</a></li>
			<li class=""><a href="/backend/commentFilter/list">评论过滤规则</a></li>	
			<!-- 
			<li class=""><a href="/backend/comment/getRootChannels">评论配置</a></li>
			<li class=""><a href="/backend/commentFilter/list">评论过滤规则</a></li>		
			 -->
			</ul>
	</div>
	
	
	<div class="apt js-title js-open" data-name="微信运营工具"><h6>微信运营工具</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>	
	<div class="apc js-subcontent" data-name="微信运营工具">
			<ul class="cpli">
			<% 				
				Calendar c1 = Calendar.getInstance();
			%>
			<li class=""><a href="/backend/weixin/getRecommendToWeixinlist?date=<%=c1.get(Calendar.YEAR) %>-<%=c1.get(Calendar.MONTH) + 1 %>-<%=c1.get(Calendar.DAY_OF_MONTH) %>">推荐到微信</a></li>			
			<li class=""><a href="/backend/weixinrule/list">微信规则管理</a></li>			
			
			</ul>
	</div>
	
	<!--  -->
	<div class="apt js-title js-open" data-name="搜狐新闻运营工具"><h6>搜狐新闻运营工具</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>	
	<div class="apc js-subcontent" data-name="搜狐新闻运营工具">
			<ul class="cpli">
				<li class=""><a href="/backend/news/getSohuNewslist?date=<%=c1.get(Calendar.YEAR) %>-<%=c1.get(Calendar.MONTH) + 1 %>-<%=c1.get(Calendar.DAY_OF_MONTH) %>">推荐到搜狐新闻</a></li>			
				<li class=""><a href="/backend/news/getRecommendList?">搜狐新闻推荐池</a></li>			
				<li class=""><a href="/backend/news/getHistoryRecommend?date=<%=c1.get(Calendar.YEAR) %>-<%=c1.get(Calendar.MONTH) + 1 %>-<%=c1.get(Calendar.DAY_OF_MONTH) %>">搜狐新闻推荐历史</a></li>
			</ul>
	</div>
	
	<div class="apt js-title js-open" data-name="抓取/运营工具"><h6>抓取/运营工具</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>	
	<div class="apc js-subcontent" data-name="抓取/运营工具">
			<ul class="cpli">
				<li class=""><a href="/backend/crawler/toTempCrawlerInstancePage">手动抓取</a></li>
				<li class=""><a href="/backend/crawler/toUpdateCrawlerUrl">重新抓取</a></li>		
				<li class=""><a href="/backend/token/accessToken">数据源授权</a></li>		
				<li class=""><a href="/backend/filter/getFilterSites">视频站点过滤</a></li>
							
			</ul>
	</div>	
	
	
	<div class="apt js-title js-open" data-name="异常数据管理"><h6>异常数据管理</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	<div class="apc js-subcontent" data-name="异常数据管理">
		<ul class="cpli">
			<li class=""><a href="/backend/crawler/getErrorCrawlerInstances">异常数据源列表</a></li>
			<li class=""><a href="/backend/checkVideo/getCheckVideos?type=1&page=1&size=20">已被下线视频</a></li>
			<li class=""><a href="/backend/checkVideo/getCheckVideos?type=2&page=1&size=20">已被切换备用源视频</a></li>
		</ul>
	</div>	
	
	<div class="apt js-title js-open" data-name="数据源类型管理"><h6>数据源类型管理</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	<div class="apc js-subcontent" data-name="数据源类型管理">
		<ul class="cpli">
			<li class=""><a href="/backend/crawler/getCrawlers?page=1&size=10">数据源类型定义</a></li>
		</ul>
		<ul class="cpli">
			<li class=""><a href="/backend/cache/remove">缓存清理工具</a></li>
		</ul>		
		<ul class="cpli">
			<li class=""><a href="/backend/cache/addChannelFilter">添加频道过滤缓存</a></li>
		</ul>	
	</div>
	
	<div class="apt js-title js-open" data-name="版本管理"><h6>版本管理</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	<div class="apc js-subcontent" data-name="版本管理">
		<ul class="cpli">
			<li class=""><a href="/backend/version/getAllVersion">版本号管理</a></li>
		</ul>
	</div>
	<div class="apt js-title js-open" data-name="其他"><h6>其他</h6>
		<div class="opt">
			<p class="trig trig_on js-clicker"><a href="javascript: void(0);">收起</a></p>
		</div>
	</div>
	<div class="apc js-subcontent" data-name="其他">
		<ul class="cpli">
			<li class=""><a href="/backend/advice/getUnreadAdvices">用户建议</a></li>
			<li class=""><a href="/backend/share/getAllShareContent">文案管理</a></li>
		</ul>
	</div>
	
	
	
	
<script type="text/javascript">
	kola("product.tvbackend.common.Menu", function (Menu) {
		Menu();
	});
</script>
