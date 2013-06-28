<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>野火视频-数据分析平台</title>
	<%@include file="/WEB-INF/inc/globalConfig.jsp"%>
	<link rel="stylesheet" type="text/css" href="http://image0.cv.itc.cn/backend/thirdPlugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="http://image0.cv.itc.cn/backend/thirdPlugins/bootstrap/css/bootstrap-responsive.min.css">
	
	
<style type="text/css">



/* Sidenav for Docs
-------------------------------------------------- */

.bs-docs-sidenav {
  width: 228px;
  margin: 30px 0 0;
  padding: 0;
  background-color: #fff;
  -webkit-border-radius: 6px;
     -moz-border-radius: 6px;
          border-radius: 6px;
  -webkit-box-shadow: 0 1px 4px rgba(0,0,0,.065);
     -moz-box-shadow: 0 1px 4px rgba(0,0,0,.065);
          box-shadow: 0 1px 4px rgba(0,0,0,.065);
}
.bs-docs-sidenav > li > a {
  display: block;
  width: 190px \9;
  margin: 0 0 -1px;
  padding: 8px 14px;
  border: 1px solid #e5e5e5;
}
.bs-docs-sidenav > li:first-child > a {
  -webkit-border-radius: 6px 6px 0 0;
     -moz-border-radius: 6px 6px 0 0;
          border-radius: 6px 6px 0 0;
}
.bs-docs-sidenav > li:last-child > a {
  -webkit-border-radius: 0 0 6px 6px;
     -moz-border-radius: 0 0 6px 6px;
          border-radius: 0 0 6px 6px;
}
.bs-docs-sidenav > .active > a {
  position: relative;
  z-index: 2;
  padding: 9px 15px;
  border: 0;
  text-shadow: 0 1px 0 rgba(0,0,0,.15);
  -webkit-box-shadow: inset 1px 0 0 rgba(0,0,0,.1), inset -1px 0 0 rgba(0,0,0,.1);
     -moz-box-shadow: inset 1px 0 0 rgba(0,0,0,.1), inset -1px 0 0 rgba(0,0,0,.1);
          box-shadow: inset 1px 0 0 rgba(0,0,0,.1), inset -1px 0 0 rgba(0,0,0,.1);
}
/* Chevrons */
.bs-docs-sidenav .icon-chevron-right {
  float: right;
  margin-top: 2px;
  margin-right: -6px;
  opacity: .25;
}
.bs-docs-sidenav > li > a:hover {
  background-color: #f5f5f5;
}
.bs-docs-sidenav a:hover .icon-chevron-right {
  opacity: .5;
}
.bs-docs-sidenav .active .icon-chevron-right,
.bs-docs-sidenav .active a:hover .icon-chevron-right {
  opacity: 1;
}
.bs-docs-sidenav.affix {
  top: 40px;
}
.bs-docs-sidenav.affix-bottom {
  position: absolute;
  top: auto;
  bottom: 270px;
}

/* Base class */
.bs-docs-example {
  position: relative;
  margin: 15px 0;
  padding: 39px 19px 14px;
  *padding-top: 19px;
  background-color: #fff;
  border: 1px solid #ddd;
  -webkit-border-radius: 4px;
     -moz-border-radius: 4px;
          border-radius: 4px;
}

input[type="text"] {
	height:30px;
}

label{
	height:30px;
	line-height:30px;
}

/* Echo out a label for the example */
.bs-docs-example:after {
  content: "<sitemesh:write property='title' />";
  position: absolute;
  top: -1px;
  left: -1px;
  padding: 3px 7px;
  font-size: 12px;
  font-weight: bold;
  background-color: #f5f5f5;
  border: 1px solid #ddd;
  color: #9da0a4;
  -webkit-border-radius: 4px 0 4px 0;
     -moz-border-radius: 4px 0 4px 0;
          border-radius: 4px 0 4px 0;
}

/* Remove spacing between an example and it's code */
.bs-docs-example + .prettyprint {
  margin-top: -20px;
  padding-top: 15px;
}

</style>


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
			<jsp:include page="/WEB-INF/layout/score/header.jsp"></jsp:include>
     </div>

	<div class="container">	
	   <div class= "row">
	   		<div class="span3 bs-docs-sidebar">
				<ul class="nav nav-list bs-docs-sidenav affix">
        		  <li <c:if test="${nav==1}">class="active"</c:if> ><a href="/score/task/list"><i class="icon-chevron-right "></i> 积分任务</a></li>
        		  <li <c:if test="${nav==2}">class="active"</c:if> ><a href="/score/prize/config"><i class="icon-chevron-right "></i> 奖项设置</a></li>        		  
        		  <li <c:if test="${nav==3}">class="active"</c:if> ><a href="/score/prize/list"><i class="icon-chevron-right"></i> 奖品管理</a></li>     		  
        		  <li <c:if test="${nav==5}">class="active"</c:if> ><a href="/score/prize/uploadView"><i class="icon-chevron-right"></i> 奖品上传</a></li>
        		  <li <c:if test="${nav==4}">class="active"</c:if> ><a href="/socre/settings"><i class="icon-chevron-right"></i> 全局设置</a></li>        		  
        		</ul>	   			
	   		</div>
	   		<div class="span9">
	   		 	<sitemesh:write property='body'/>		
	   		</div>	   		
	   </div>
    </div>
    <footer>
       
    </footer>	        
</body>
</html>
