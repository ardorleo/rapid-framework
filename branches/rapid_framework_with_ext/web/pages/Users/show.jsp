<%@page import="com.awd.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>

<head>
	<%@ include file="/commons/meta.jsp" %>
	<base href="<%=basePath%>">
	<title><%=Users.TABLE_ALIAS%>信息</title>
</head>

<body>
<%@ include file="/commons/messages.jsp" %>

<s:form action="/pages/Users/list.do" method="get" theme="simple">
	<input type="button" value="返回列表" onclick="window.location='${ctx}/pages/Users/list.do'"/>

	<s:hidden name="id" id="id" value="%{model.id}"/>

	<table class="formTable">
		<tr>	
			<td class="tdLabel"><%=Users.ALIAS_LOGIN_NAME%></td>	
			<td><s:property value="%{model.loginName}" /></td>
		</tr>
		<tr>	
			<td class="tdLabel"><%=Users.ALIAS_PASSWORD%></td>	
			<td><s:property value="%{model.password}" /></td>
		</tr>
		<tr>	
			<td class="tdLabel"><%=Users.ALIAS_NAME%></td>	
			<td><s:property value="%{model.name}" /></td>
		</tr>
		<tr>	
			<td class="tdLabel"><%=Users.ALIAS_EMAIL%></td>	
			<td><s:property value="%{model.email}" /></td>
		</tr>
	</table>
</s:form>

</body>

</html>