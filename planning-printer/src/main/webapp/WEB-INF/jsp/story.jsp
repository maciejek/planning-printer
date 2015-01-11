<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<div class="row" id="story-step-content">
	<p>Add new user stories and tasks here.</p>
	<br/>
	<div class="col-md-1"></div>
	<div class="col-md-10">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>Current sprint</strong></div>

			<table class="table table-hover story-table">
				<thead>
					<tr>
						<th></th>
						<th style="text-align: center">Number</th>
						<th style="text-align: center">Description</th>
						<th style="text-align: center">SP</th>
						<th></th>
						<th></th>
						<th></th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${stories}" var="story">
						<tr class="user-story-tr" data-id="${story.id}">
							<td></td>
							<td class="story-number" style="text-align: center">${story.number} </td>
							<td class="story-summary">${story.summary}</td>
							<td class="story-points" style="text-align: center">${story.points}</td>
							<td style="text-align: center"><span class="glyphicon glyphicon-pencil edit-story" aria-hidden="true"></span></td>
							<td style="text-align: center"><span class="glyphicon glyphicon-trash remove-story" aria-hidden="true"></span></td>
							<td style="text-align: center"><span class="glyphicon glyphicon-plus-sign add-task" data-toggle="modal" data-target="#popupAddTask" aria-hidden="true"></span></td>
						</tr>
						
						<c:forEach items="${story.tasks}" var="task">
							<tr class="task-tr" data-story-id="${story.id}" data-type="${task.type}" data-est="${task.estimation}">
								<td></td>
								<td style="text-align: right">${task.number}</td>
								<td colspan="3">
									<span style="padding-left: 20px;" class="badge">${task.estimation}</span>
									<span class="label label-primary task-type">${task.type == "DEVELOPER_TASK" ? "DEV" : "TEST"}</span>
									<span style="padding-left: 10px;" > ${task.summary} </span> 
								</td>
								<td style="text-align: center"><span id="${task.id}" class="glyphicon glyphicon-trash remove-task" aria-hidden="true"></span></td>
						</c:forEach>
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
						<td></td>	
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	<div class="col-md-1"></div>
	
	<div class="panel panel-default hours-state" style="position:fixed; bottom: 10px; right: 40px">
		<table class="table">
			<tr>
				<td style="text-align:right">
					Developer tasks hours:<br/>
					Developers hours:<br/>
					Summary:
				</td>
				<td style="text-align:center">
					<span class="hours dev-total-hours">0</span><br/>
					<span class="hours dev-team-hours">0</span><br/>
					<span class="hours dev-summary">0</span>
				</td>
			</tr>
			<tr>
				<td style="text-align:right">
					Tester tasks hours:<br/>
					Testers hours:<br/>
					Summary:
				</td>
				<td style="text-align:center">
					<span class="hours test-total-hours">0</span><br/>
					<span class="hours test-team-hours">0</span><br/>
					<span class="hours test-summary">0</span>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="modal fade" id="popupAddTask" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add new task!</h4>
				</div>
				<form:form cssClass="form-horizontal" id="addTaskForm">
					<div class="modal-body">
					
						<div class="form-group">
							<label for="number" class="col-sm-3 control-label">Number
								<span class="required-field">*</span>
							</label>
							<div class="col-sm-9">
								<input type="text" name="number" id="number" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="summary" class="col-sm-3 control-label">Summary
								<span class="required-field">*</span>
							</label>
							<div class="col-sm-9">
								<input type="text" name="summary" id="summary" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="estimation" class="col-sm-3 control-label">
								Estimation <span class="required-field">*</span>
							</label>
							<div class="col-sm-9">
								<input type="text" name="estimation" id="estimation" class="form-control" />
							</div>
						</div>

						<div class="form-group">

							<label for="estimation" class="col-sm-3 control-label">
								Type <span class="required-field">*</span>
							</label>
							<div class="col-sm-9">
								<div class="radio">
									<label><input type="radio" value="DEV" checked="checked"
										name="optradio" class="task-type-radio">Developer</label>
								</div>
								<div class="radio">
									<label><input type="radio" value="TEST" name="optradio" 
									class="task-type-radio">Tester</label>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="hidden" name="storyId" value="" id="task-story-id" />
						<input type="hidden" name="taskType" value="DEV" id="task-type-hidden" />
						<button type="button" id="closeAddTask" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<input type="submit" id="save" value="Save" class="btn btn-primary" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

    <div align="center">
		<a href="./planning/downloadPDF.pdf" class="btn btn-primary btn-default">Generuj PDF <span class="glyphicon glyphicon-file"></span></a>
    </div>

<script type="text/javascript">
$(document).ready(function() {
	sum_task_hours();
	function sum_task_hours() {
		var dev_task = 0;
		var test_task = 0;
		$('.task-tr').each(function() {
			if ($(this).data('type') == "DEVELOPER_TASK") {
				dev_task += parseFloat($(this).data('est'));
			} else {
				test_task += parseFloat($(this).data('est'));
			}
		});
		var dev_team_hours = $('#developers-hours-sum').text();
		var test_team_hours = $('#testers-hours-sum').text();
		$('.dev-total-hours').text(dev_task);
		$('.dev-team-hours').text(dev_team_hours);
		$('.dev-summary').text(dev_team_hours - dev_task);
		if (dev_team_hours - dev_task < 0) {
			$('.dev-summary').addClass('error');
		}
		$('.test-total-hours').text(test_task);
		$('.test-team-hours').text(test_team_hours);
		$('.test-summary').text(test_team_hours - test_task);
		if (test_team_hours - test_task < 0) {
			$('.test-summary').addClass('error');
		}
	}
	$('.story-table .add-story').click(function() {
		var storyNumber = $("#storyNumber").val();
		var storySummary = $("#storySummary").val();
		var storyPoints = $("#storyPoints").val();
		if (storyNumber != "" && storySummary != "" && storyPoints != "") {
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
				success : function(html) {
					if (html != "false") {
						$('#story-step-content').parent().html(html);
					}
				},
				error : function(data) {
					console.log("Error!");
				}
			});
		}
	});
	$('.story-table').on("click", ".remove-story", function() {
		var id = $(this).closest('tr').data("id");
		$.ajax({
			url : "<spring:url value='/story/removeStory.html' />",
			type : "get",
			data : {
				id : function() {
					return id;
				},
			},
			success : function(html) {
				if (html != "false") {
					$('#story-step-content').parent().html(html);
				}
			},
			error : function(data) {
				console.log("Error!");
			}
		});
	});
	$('.story-table').on("click", ".remove-task", function() {
		var id = $(this).attr("id");
		$.ajax({
			url : "<spring:url value='/task/removeTask.html' />",
			type : "get",
			data : {
				id : function() {
					return id;
				},
			},
			success : function(html) {
				if (html != "false") {
					$('#story-step-content').parent().html(html);
				}
			},
			error : function(data) {
				console.log("Error!");
			}
		});
	});
	
	$('.story-table').on("click", ".edit-story", function() {
		$('#story-id').val($(this).closest('tr').data('id'));
	});	
	
	$('#editStoryForm').submit(function(e) {
		var number = $('#number').val();
		var story_id = $('#task-story-id').val();
		var summary = $('#summary').val();
		var estimation = $('#estimation').val();
		var task_type = $('#task-type-hidden').val();
		$.ajax({
			url : "<spring:url value='/task/addTask.html' />",
			type : "post",
			data : {
				summary : function() {
					return summary;
				},
				estimation : function() {
					return estimation;
				},
				storyId : function() {
					return story_id;
				},
				taskType : function() {
					return task_type;
				},
				number : function() {
					return number;
				}
			},
			success : function(html) {
				$('#closeAddTask').click();
				if (html != "false") {
					$('#story-step-content').parent().html(html);
				}
			},
			error : function(data) {
				console.log("Error!");
			}
		});
		return false;
	});
	$(".task-type-radio").on("click", function() {
		$("#task-type-hidden").val($(this).attr("value"));
	});
	
	
	
	
	$('.story-table').on("click", ".add-task", function() {
		$('#task-story-id').val($(this).closest('tr').data('id'));
	});	
	$('#addTaskForm').submit(function(e) {
		var number = $('#number').val();
		var story_id = $('#task-story-id').val();
		var summary = $('#summary').val();
		var estimation = $('#estimation').val();
		var task_type = $('#task-type-hidden').val();
		$.ajax({
			url : "<spring:url value='/task/addTask.html' />",
			type : "post",
			data : {
				summary : function() {
					return summary;
				},
				estimation : function() {
					return estimation;
				},
				storyId : function() {
					return story_id;
				},
				taskType : function() {
					return task_type;
				},
				number : function() {
					return number;
				}
			},
			success : function(html) {
				$('#closeAddTask').click();
				if (html != "false") {
					$('#story-step-content').parent().html(html);
				}
			},
			error : function(data) {
				console.log("Error!");
			}
		});
		return false;
	});
	$(".task-type-radio").on("click", function() {
		$("#task-type-hidden").val($(this).attr("value"));
	});
	$("#addTaskForm").validate({
		rules : {
			number : {
				required : true
			},
			summary : {
				required : true
			},
			estimation : {
				required : true
			},
			taskType : {
				required : true
			}
		},
		highlight: function(element) {
			$(element).closest(".form-group").removeClass('has-success').addClass('has-error').addClass('has-feedback');
		},
		unhighlight: function(element) {
			$(element).closest(".form-group").removeClass('has-error').addClass('has-success').addClass('has-feedback');
		}
	});
});
</script>
