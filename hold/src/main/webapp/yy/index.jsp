<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.chinaren.tv.backend.common.utils.passport.RequestToolKit,
org.apache.commons.lang.StringUtils,
com.chinaren.tv.backend.common.utils.AdminIdentityUtil,
com.chinaren.tv.backend.model.AdminUser"%>
<%@include file="/WEB-INF/inc/globalConfig.jsp" %>
<script type="text/javascript">
PATH={

    domain:'bai.sohu.com',
    d1: 'http://s1.bai.itc.cn/',
    d2: 'http://s2.bai.itc.cn/',
	js: 'r/j-src/',
	flash: 'r/f/',
	img: 'http://s2.bai.itc.cn/r/i/',
	cssBase: 'http://s1.bai.itc.cn/r/c/base/',
	cssCell: 'http://s1.bai.itc.cn/r/c/cells/',
	cssTheme: 'http://s1.bai.itc.cn/r/c/themes/',
	cssPage: 'http://s2.bai.itc.cn/r/c/pages/',
	cssApp: 'http://s2.bai.itc.cn/r/c/apps/',
	cssTopic: 'http://s2.bai.itc.cn/r/c/topics/',
	jsSrc: true,
	jsKola: 'http://s1.bai.itc.cn/r/j-src/kola/core/kola.js',
	jsProfile:'http://s1.bai.itc.cn/r/j-src/sohu/core/core.js'
};

try{

 //document.domain = PATH.domain;

_KOLA={c:[],r:[]};

$call=function(){
	_KOLA.c.push(arguments);
};

$register=function(){
_KOLA.r.push(arguments);
};

_KOLA.d=function(){
document.domReady=true;
if(window.kola&&kola.Event&&kola.Event._fireDomLoad)
                                 kola.Event._fireDomLoad();
};

_KOLA.dc=function(){
  if(document.documentElement.doScroll)(
                             function(){
                                         try{
                                                document.documentElement.doScroll("left");
                                             }
                                         catch(error){
                                                         setTimeout(arguments.callee,5);
							 return;
					 }					
					_KOLA.d();
			    })();
};

if(document.addEventListener){ 
                 document.addEventListener("DOMContentLoaded",_KOLA.d,false);
}else if(document.attachEvent){
               		   document.attachEvent("onreadystatechange",
            		   function(){
            	   					if(document.readyState==="complete"){
            	   						document.detachEvent("onreadystatechange",arguments.callee);
            	   						_KOLA.d();}
            	   				 });
                       _KOLA.dc();
       };
       
(function() {
    			  var a = document.createElement("script");
    			  a.type = "text/javascript";
    			  a.src = PATH.jsKola;
    			  document.getElementsByTagName("head")[0].appendChild(a);}
)();


}catch(e){
	alert(e.message);
}



</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="noodp,noydir" />
<meta name="keywords" content="搜狐微博" />
<meta name="copyright" content="搜狐,SOHU.com" />

<title>欢迎来到微博数据分析平台！</title>
<%

	AdminIdentityUtil identityUtil = AdminIdentityUtil.getInstance();
	
	AdminUser admin = identityUtil.getIdentity(request);


%>


<script type="text/javascript">
$call(function() {
	PassportSC.loginRedirectUrl =  '/sys/login';
	PassportSC.autoRedirectUrl='/sys/login';
	PassportSC.drawPassport(document.getElementById('indexLogin'));
 }, 'sohu.passport.*', {afterDom: true});
 

</script>
<link type="text/css" rel="stylesheet" href="http://image0.cv.itc.cn/backend/login/login.css" />
</head>
<body onload="drawPassportCard();">


<div id="page">

	<div id="pageBd">
		<div id="indexLogo"></div>
		<div id="indexContent">
			<div id="indexWel"></div>
			<div id="indexLogin"><div class="ppWaitMsg"></div></div>
			<div id="indexHelp">
			请使用域名访问,http://wildfire.chinaren.com/backend </br>
			<span id="forgotPassword" style="display:none;"><a href="http://passport.sohu.com/web/recoverpwd" target="_blank">忘记密码？</a></span></div>			
		</div>
	</div>
	

</div>
</body>
</html>
