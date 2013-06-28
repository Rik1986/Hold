<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html lang="zh-CN">
<head>
<%@include file="/WEB-INF/inc/globalConfig.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="http://image0.cv.itc.cn/c/g.css"></head>
<body>

<div class="main">
	<div class="c1">
		<div class="ap">
			<div class="apt">
				<h6>创建频道</h6>
			</div>
			<div class="apc">
				<div class="form2">
					<!--S form-->
					<form action="/backend/channel/addChannel" enctype="multipart/form-data" method="post" target="_parent" id="addChannelForm">
					<fieldset>
					    
						<input type="text" style="display: none;" name="parent" value="0">
						<div class="frm frm_up">
							<p class="frmt"><em><label><i class="red">*</i>图标</label></em></p>
							<div class="frmc">
						            <label>
						                <input type="file" name="Filedata"/>
						            </label> 
								<p class="frm_tip"><b><em></em></b></p>
							</div>
						</div>
						
						<div class="frm frm_subj">
					      <p class="frmt"><em><label><i class="red">*</i>频道名称</label></em></p>
					      <div class="frmc">
						            <label>
						                <input name="title" type="text" value="">
						            </label> 
							</div>
						</div>
							
						<div class="frm frm_subj">
							<p class="frmt"><em><label><i class="red">*</i>排序级别</label></em></p>
							<div class="frmc">
								<input type="text" name="sort" data-validate="number: '排序级别须为数字'"/>
								<p class="frm_tip"><b><em></em></b></p>
							</div>
						</div>
						
						<div class="frm frm_subj">
							<p class="frmt"><em><label><i class="red">*</i>描述</label></em></p>
							<div class="frmc">
								<div class="tarea"><textarea name="descn"></textarea></div>
							</div>
						</div>
					</fieldset>
					
					<div class="opt">
					   <input class="btn" type="submit" value="保存"/>
					</div>
			       </form>
				   <script type="text/javascript">
						kola("product.tvbackend.util.FormValidator, util.jQuery", function (FormValidator, $) {
							new FormValidator({
								form: $("#addChannelForm"),
								triggers: "keyup|blur"
							});
						});
				   </script>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>