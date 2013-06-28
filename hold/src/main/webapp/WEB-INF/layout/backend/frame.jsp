<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>野火视频-运营管理系统</title>
<link rel="stylesheet" type="text/css" href="http://image0.cv.itc.cn/c/g.css">
<%@include file="/WEB-INF/inc/globalConfig.jsp"%>
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


</script>
</head>

<body>
	<!-- top bar -->
	<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

	<!-- left  -->
	<div id="aside">
		<div class="c1" id="menu">
			<jsp:include page="/WEB-INF/common/backend/aps.jsp"></jsp:include>
		</div>
	</div>
	<!-- main -->
	<div id="ipage">
	    <div id="ifr">
			<sitemesh:write property='body'/>
		</div>
	</div>
</body>
</html>
