<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@ include file="../layout/taglib.jsp"%>

<form class="form-signin" role="form" method="POST" action="${pageContext.request.contextPath}/j_spring_security_check">
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
	<div class="alert alert-danger">
		Can not login: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
	</div>
	</c:if>
	<h2 class="form-signin-heading">Please sign in</h2>
	<label for="inputEmail" class="sr-only">Email address</label> 
	<input type="text" name="j_username" id="inputEmail" class="form-control" placeholder="Email"
		required autofocus> 
	<label for="inputPassword" class="sr-only">Password</label>
	<input type="password" name="j_password" id="inputPassword" class="form-control"
		placeholder="Password" required>
	<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>

