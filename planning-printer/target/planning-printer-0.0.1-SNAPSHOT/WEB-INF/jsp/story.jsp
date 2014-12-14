<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<h2>Add USER STORY</h2>

<p>Here you can add your story!</p>



<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-10">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>User Stories</strong></div>

			<table class="table table-hover developers-table">
				<thead>
					<tr>
						<th></th>
						<th>Number</th>
						<th>Description</th>
						<th>Story points</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${stories}" var="story">
						<tr>
							<td></td>
							<td>${story.number}</td>
							<td>${story.summary}</td>
							<td>${story.points}</td>
							<td><span id="${story.id}" class="glyphicon glyphicon-trash remove-story" 
							aria-hidden="true"></span></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr class="table-footer">
						<td></td>
						<td><input type="text" id="storyNumber"
							name="storyNumber" class="form-control"></td>
						<td><input type="text" id="storySummary"
							name="storySummary" class="form-control"></td>
						<td><input type="text" id="storyPoints"
							name="storyPoints" class="form-control"></td>
						<td><span class="glyphicon glyphicon-plus-sign add-story"
							aria-hidden="true"></span></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	<div class="col-md-1"></div>
</div>

<br/>

<script type="text/javascript">
$(document).ready(function() {
	$('.developers-table .add-story').click(function() {
		var storyNumber = $("#storyNumber").val();
		var storySummary = $("#storySummary").val();
		var storyPoints = $("#storyPoints").val();
		if (storyNumber != "" && storySummary != "" && storyPoints != "") {
			$.ajax({
				url : "<spring:url value='/story/addStory.html' />",
				type : "get",
				data : {
					name : function() {
						return storyNumber;
					},
					surname : function() {
						return storySummary;
					},
					surname : function() {
						return storyPoints;
					},
				},
				success : function(id) {
					$(".developers-table tbody").append("<tr><td></td>" +
							"<td>" + storyNumber + "</td><td>" + storySummary + "</td><td>" +storyPoints +"</td>" +
							"<td><span id=" + id + " class='glyphicon glyphicon-trash remove-story' " + 
							"aria-hidden='true'></span></td></tr>");
					$("#storyNumber").val("");
					$("#storySummary").val("");
					$("#storyPoints").val("");
				},
				error : function(data) {
					console.log("Error!");
				}
			});
		}
	});	
});
</script>
	