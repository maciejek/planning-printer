<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<h2>Replanning</h2>

<p>Here you can add some new user stories and tasks to your current sprint.</p>

<br />

<div class="row tab-content" style="min-width: 100px">
	<div style="display: none" class="ajax-loader">
		<span class="loader"></span>
	</div>

	<div class="planning-step col-md-12">
		<div class="step-content"></div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$.ajax({
		url : "<spring:url value='/task.html' />",
		type : "post",
		data : {
			tasksJSONObject : get_incomplete_tasks_and_stories_as_json_object(),
			planning : false
		},
		beforeSend : function() {
			display_loader();
		},
		success : function(data) {
			hide_loader();
			$('.step-content').html(data);
		}
	});
});
</script>
