<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<h2>Replanning</h2>

<p>Here you can add some new user stories and tasks to your current sprint.</p>

<br />

<div class="replanning col-md-12">
	<div class="content"></div>
	<br/>
	<br/>
</div>


<script type="text/javascript">
$(document).ready(function() {
	$.ajax({
		url : "<spring:url value='/task.html' />",
		type : "post",
		success : function(data) {
			$('.replanning').find('.content').html(data);
			$('.hours-state').hide();
		}
	});
});
</script>
