<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
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
				<div class="opt">
					<a href="javascript:void(0);" class="btn" onclick='kola("system.Model", function (Model) {Model.instantModel({url: "/backend/weixin/saveSpall", params: {"html":$("#elm1").xheditor().getSource()}, success: function (data) {alert(data.statusText);if(data.status==0){window.location.href="/backend/weixin/getRecommendToWeixinlist?date=<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>"}}, fail: function () {}});});'>
	                   <b>保存</b>
					</a>
				</div>
				<h6>编辑碎片</h6>
			</div>
			<div class="apc">
				<div class="form2">
					<form id="frmDemo" method="post" action="show.php">
						<textarea id="elm1" name="elm1" rows="20" cols="80" style="width: 100%" ></textarea>
					</form>
				</div>
			</div>
		</div>
	</div><script type="text/javascript">

 kola("widget.Editor, util.jQuery", function (Editor, $) {
	 $(function () {
	 	Editor.init({
	 		target: $('#elm1'), 
	 		upImgUrl:"uploadImageForAjax",
	 		upImgExt:"jpg,jpeg,gif,png",
	 		html5Upload:false,
	 		complete: function (editor) {
	 			editor.xheditor().setSource('${html}');
	 		}
	 	});
	});
});



</script>
</div>

</body>
</html>
