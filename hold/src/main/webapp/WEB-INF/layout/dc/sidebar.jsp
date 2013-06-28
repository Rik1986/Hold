<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ page import="java.util.*"%>	
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page
	import="com.chinaren.tv.backend.common.utils.AdminIdentityUtil,
			com.chinaren.tv.backend.model.AdminUser,
			com.chinaren.tv.backend.common.Constants"%>
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
	
	
	String url = "";
	url = request.getAttribute("dc_url").toString();
	
%>


    <% if(url != null && !"".equals(url) ){
         if(url.indexOf("/dc/overview/")>=0){
         
     %>
    <div id="overviewDiv" >
    
  	  <ul class="">
  	   	<li><a href="#" onclick="alert('努力建设中...')">频道总体分析</a></li>
  	   	<li><a href="#" onclick="alert('努力建设中...')" >用户总体分析</a></li>
		<li><a href="#" onclick="alert('努力建设中...')" >播放数据分析</a></li>
		<li><a href="/dc/overview/statistics/doPromotionReport?date=<%=date%>">渠道数据分析</a></li>
  	  </ul>
 	 </div>     
     
     <%    	
         }
    	else if(url.indexOf("/dc/pv/")>=0){
    	
    	%>
    <div id="pvDiv" >
    
   	  <ul class="">
   	    <li class="nav-header">客户端pv</li>
		<li><a href="/dc/pv/getPvDetail?date=<%=curDate%>">每日pv</a></li>
		<li><a href="/dc/pv/getClientVersion?date=<%=date%>">版本分布</a></li>
		<li><a href="#">地域分布</a></li>
	    <li class="nav-header">wap pv</li>
		<li><a href="/dc/pv/wap/getWapPvByDate?date=<%=curDate%>">每日pv</a></li>
		<li><a href="/dc/pv/wap/getWapCcByDate?date=<%=curDate%>">每日cc</a></li>
	  </ul>
     </div>    	
    	
    	<%
    	}
    	else if(url.indexOf("/dc/rank/")>=0){
    	
       %>
    <div id="rankDiv" >
  	  <ul class="">
		<li><a href="/dc/rank/doRecommendVideoRank?date=<%=date%>&topNum=20">点击排行</a></li>
		<li><a href="/dc/rank/doShareMessageRank?date=<%=date%>&topNum=20">分享排行</a></li>
	  </ul>
     </div>    	 
    	 
    	 <%
    	}
    }
    	
     %>
    

  
  

  
  


