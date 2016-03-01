<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 
	<form action="testConversionServiceConverer" method="post">
		 Format: lastname-email-gender-department.id For example: GG-gg@gmail.com-0-105- 
		Employee: <input type="text" name="employee"/>
		<input type="submit" value="Submit"/>
	</form>
	<br><br>
 -->

	<!-- 
	1. Why use "from" tags?
	Faster to develop web page, and more convenience to re-show form values
	2. Notice:
	Can use "modelAttribute" attribute to a specific model attribute,
	if does not specify, then it will search bean from command in request
	if the attribute still not exist, then throws an exception

 -->

	<form:form action="${pageContext.request.contextPath}/emp" moethod="POST" modelAttribute="employee">
		<!-- "path" attribute points to html page's name attribute -->
	<c:if test="${employee.id == null }">
	
	LastName: <form:input path="lastName" />
	
	</c:if>
	<c:if test="${employee.id != null }">
		<form:hidden path="id"/>
		<%-- For "_method" cannot use "form:hidden" tag, because modelAttribute's bean has no "_method" attribute --%>
		
		<input type="hidden" name="_method" value="PUT"/>
	</c:if>
		<br>
	Email: <form:input path="email" />
		<br>
		<%
			Map<String, Object> genders = new HashMap();
				genders.put("1", "Male");
				genders.put("0", "Female");
				request.setAttribute("genders", genders);
				
		%>
		
	Gender: <form:radiobuttons path="gender" items="${genders }" />
		<br>
	Department: <form:select path="department.id" items="${departments }" itemLabel="departmentName" itemValue="id"></form:select>
	<br>
	<!-- 
		1. Data type transferring 
		2. Data type format
		3. Data verificatioin
	 -->
	
	
	Birth: <form:input path="birth"/>
	<br>
	
	<input type="submit" value="Submit"/>
	</form:form>


</body>
</html>