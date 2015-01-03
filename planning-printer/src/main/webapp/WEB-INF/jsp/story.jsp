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

			<table class="table table-hover story-table">
				<thead>
					<tr>
						<th></th>
						<th>Number</th>
						<th>Description</th>
						<th>Story points</th>
						<th></th>
						<th></th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${stories}" var="story">
						<tr class="user-story-tr">
							<td></td>
							<td style="width:60px;">${story.number} </td>
							<td style="padding-left:20px;">${story.summary}</td>
							<td style="width:60px;">${story.points}</td>
							<td><span id="${story.id}" class="glyphicon glyphicon-trash remove-story" aria-hidden="true"></span></td>
							<td><span  class="glyphicon glyphicon-plus-sign" data-toggle="modal" data-target="#popupAddTask_${story.id}" aria-hidden="true"></span></td>
						</tr>
									<c:forEach items="${story.tasks}" var="task">
										<tr class="task-tr">
											<td style="padding-left: 20px;" colspan="5">
												<span style="padding-left: 80px; padding-right: 20px ;" >${task.number}</span>
												<span style="padding-left: 20px;" class="badge">${task.estimation}</span>
												<span style="padding-right: 10px;" > ${task.summary} </span> 
												<span class="label label-primary">${task.type}</span>
											</td>
											<td><span id="${task.id}" class="glyphicon glyphicon-minus remove-task" aria-hidden="true"></span></td>
										</tr>
									</c:forEach>

						<tr class="hours-summary">
						<td><strong></strong></td>
						<td><strong></strong></td>
						<td><strong></strong></td>
							<td style="padding-left: 120px;"><strong>Summary:</strong></td>
							<td class="text-center" style="padding-left: 20px;"><span id="task-hours"></span></td>
						<td><strong></strong></td>
						</tr>

						<div class="modal fade" id="popupAddTask_${story.id}" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Add new task!</h4>
									</div>
									<form:form commandName="task"
										cssClass="form-horizontal add-task" action="addTask.html">
										<div class="modal-body">
										
											<div class="form-group">
												<label for="summary" class="col-sm-4 control-label">Summary
													<span class="required-field">*</span>
												</label>
												<div class="col-sm-8">
													<form:input path="summary" cssClass="form-control" />
													<form:errors path="summary" />
												</div>
											</div>
											<div class="form-group">
												<label for="estimation" class="col-sm-4 control-label">
													Estimation <span class="required-field">*</span>
												</label>
												<div class="col-sm-8">
													<form:input path="estimation" cssClass="form-control" />
													<form:errors path="estimation" />
												</div>
											</div>

											<div class="form-group">

												<label for="estimation" class="col-sm-4 control-label">
													Task type : <span class="required-field">*</span>
												</label>
												<div class="col-sm-8">
													<div class="radio">
														<label><input type="radio" checked="checked"
															name="optradio">Developer</label>
													</div>
													<div class="radio">
														<label><input type="radio" name="optradio">Tester</label>
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<input type="hidden" name="storyId" value="${story.id}" />
												<button type="button" class="btn btn-default"
													data-dismiss="modal">Close</button>
												<input type="submit" value="Save" class="btn btn-primary" />
											</div>
										</div>
									</form:form>
								</div>

							</div>
						</div>

					</c:forEach>
				</tbody>
				<tfoot>
					<tr class="table-footer">
						<td></td>
						<td><input type="text" id="storyNumber" name="storyNumber" class="form-control"></td>
						<td><input type="text" id="storySummary" name="storySummary" class="form-control"></td>
						<td><input type="text" id="storyPoints" name="storyPoints" class="form-control"></td>
						<td><span class="glyphicon glyphicon-plus-sign add-story"aria-hidden="true"></span></td>
						<td></td>	
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
	$('.story-table').on("click", ".add-task", function() {
		$.ajax({
			url : "<spring:url value='/story/addTask.html' />",
			type : "get",
			data : {
				id : function() {
					return id;
				},
			},
			success : function(data) {
				$("#"+id).closest('tr').remove();
			},
			error : function(data) {
				console.log("Error!");
			}
		});
	});
	$('.story-table').on("click", ".save-task", function() {
		var taskSummary = $("#taskSummary").val();
		var taskEstimation = $("#taskEstimation").val();
		var taskType = $("#taskType").val();
		if (taskSummary != "" && taskEstimation != "" && taskType != "") {
			$.ajax({
				url : "<spring:url value='/story/saveTask.html' />",
				type : "get",
				data : {
					summary : function() {
						return taskSummary;
					},
					estimation : function() {
						return taskEstimation;
					},
					type : function() {
						return taskType;
					},
				},
				success : function(data) {
					$("#"+id).closest('tr').remove();
				},
				error : function(data) {
					console.log("Error in save task!");
				}
			});
		}
	});
	$('.story-table .add-story').click(function() {
		var storyNumber = $("#storyNumber").val();
		var storySummary = $("#storySummary").val();
		var storyPoints = $("#storyPoints").val();
		if (storyNumber != "" && storySummary != "" && storyPoints != "") {
			console.log("dupa");
			$.ajax({
				url : "<spring:url value='/story/addStory.html' />",
				type : "get",
				data : {
					number : function() {
						return storyNumber;
					},
					summary : function() {
						return storySummary;
					},
					points : function() {
						return storyPoints;
					},
				},
				success : function(id) {
					$(".story-table tbody").append("<tr><td></td>" +
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
	$('.story-table').on("click", ".remove-story", function() {
		var id = $(this).attr("id");
		$.ajax({
			url : "<spring:url value='/story/removeStory.html' />",
			type : "get",
			data : {
				id : function() {
					return id;
				},
			},
			success : function(data) {
				$("#"+id).closest('tr').remove();
			},
			error : function(data) {
				console.log("Error!");
			}
		});
	});
	
	$('.story-table').on("click", ".remove-task", function() {
		var id = $(this).attr("id");
		$.ajax({
			url : "<spring:url value='/story/removeTask.html' />",
			type : "get",
			data : {
				id : function() {
					return id;
				},
			},
			success : function(data) {
				$("#"+id).closest('tr').remove();
			},
			error : function(data) {
				console.log("Error!");
			}
		});
	});
	
	
	$('.task-hours').blur(function() {
		var hours = $(task.estimation);
		var sum = 0;
		for (i = 0; i < hours.length; i++) {
			var number = parseInt(hours.eq(i).val());
			if (!isNaN(number)) {
				sum += number
			} else {
				sum += 0;
			}
		}
		$('#task-hours').text(sum);
	});
});
</script>
