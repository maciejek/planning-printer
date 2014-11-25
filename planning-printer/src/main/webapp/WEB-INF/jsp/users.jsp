<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>

<table class="table table-bordered table-hover">
	<thead>
	<tr>
	<th>user email</th>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>
					<a href="<spring:url value="/users/${user.id}.html"/>">
						${user.email}
					</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>