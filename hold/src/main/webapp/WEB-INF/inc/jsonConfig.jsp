<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib
	prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@taglib
	prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@taglib
	prefix="json" uri="http://www.atg.com/taglibs/json"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Content-Type",
			"application/json; charset=UTF-8");
	response.setDateHeader("Expires", 0);

%>