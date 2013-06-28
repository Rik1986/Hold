<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>野火视频-数据分析平台</title>
	<%@include file="/WEB-INF/inc/globalConfig.jsp"%>
	<link rel="stylesheet" type="text/css" href="http://image0.cv.itc.cn/backend/thirdPlugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="http://image0.cv.itc.cn/backend/thirdPlugins/bootstrap/css/bootstrap-responsive.min.css">
	<link rel="stylesheet" type="text/css" href="http://wildfire.chinaren.com/resources/c/yehuo_dc/table.css"/>
<%--	<link rel="stylesheet" type="text/css" href="http://10.1.57.173:8890/c/yehuo_dc/table.css"/>--%>
	<script type="text/javascript" src="http://image0.cv.itc.cn/fusioncharts/FusionCharts.js"></script> 
	<script type="text/javascript">
	          (function(){
	            var originalUrl = window.location.href,
	                 toUrl = originalUrl .indexOf('#')!=-1 && originalUrl.split('#')[1];
	            if (toUrl&&!toUrl.match(/javascript/igm)) {
	                window.location.href = toUrl;
	            }
	          })();       
	
	          (function() {
	              //  ????kola
	              var    script = document.createElement('script');
	              //script.src = 'http://d2.s5.cr.itc.cn/src/kola/Package.js';
	              //script.src = 'http://10.1.57.173/src/kola/Package.js';
	              script.src = 'http://image0.cv.itc.cn/backend/src/kola/Package.js';
	              //script.src = 'http://image0.cv.itc.cn/src/kola/Package.js';
	          	script.type = 'text/javascript';
	          	document.getElementsByTagName('head')[0].appendChild(script);
	              script = null;
	
	              //  ?ù?÷????kola???ú±???
	              window.kola = function() {
	                  var arr = [this];
	                  for (var i = 0, il = arguments.length; i < il; i++) {
	                      arr.push(arguments[i]);
	                  }
	                  kola._cache.push(arr);
	              };
	              kola._cache = [];
	          })();
	          
	          kola("third.Bootstrap, util.jQuery", function (Bootstrap, $) {
	              Bootstrap.use();
	          });          
	</script>
</head>

<body >
	<!-- top bar -->
     <div class="navbar navbar-inverse" style="position: static;">
			<jsp:include page="/WEB-INF/layout/dc/header.jsp"></jsp:include>
     </div>

	<div class="container">	
 		<sitemesh:write property='body'/>		
    </div>
    <footer>
       
    </footer>	        
</body>
</html>
