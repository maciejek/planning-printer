<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<div class="row" id="story-step-content">
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
						<th style="text-align: center">SP/EST</th>
						<th></th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${stories}" var="story">
						<tr class="user-story-tr" data-id="${story.id}" data-number="${story.number}" data-summary="${story.summary}"
									data-points="${story.points}">
							<td></td>
							<td class="story-number" style="text-align: center">${story.number} </td>
							<td class="story-summary">
								${story.summary}
								<span class="glyphicon glyphicon-pencil edit-story" data-toggle="modal" aria-hidden="true"  data-target="#popupUserStory"></span>
							</td>
							<td class="story-points" style="text-align: center">${story.points} SP</td>
							<td style="text-align: center"><span class="glyphicon glyphicon-trash remove-story" aria-hidden="true" data-tooltip="tooltip" data-placement="left" title="Remove user story"></span></td>
							<td style="text-align: center"><span class="glyphicon glyphicon-plus-sign add-task" data-toggle="modal" data-tooltip="tooltip" data-target="#popupAddTask" aria-hidden="true" data-placement="left" title="Add task to user story"></span></td>
							<td></td>
						</tr>
						
						<c:forEach items="${story.tasks}" var="task">
							<tr class="task-tr" data-id="${task.id}" data-story-id="${story.id}" data-type="${task.type}" data-number="${task.number}" data-summary="${task.summary}" data-est="${task.estimation}">
								<td></td>
								<td style="text-align: right">${task.number}</td>
								<td>
									<span class="label label-primary task-type">${task.type == "DEVELOPER_TASK" ? "DEV" : "TEST"}</span>
									<span style="padding-left: 10px;" > ${task.summary} </span> 
									<span id="${task}"  class="glyphicon glyphicon-pencil edit-task" data-toggle="modal" aria-hidden="true" data-target="#popupEditTask"></span>
								</td>
								<td style="text-align: center"><span class="badge">${task.estimation}</span></td>
								<td style="text-align: center"><span id="${task.id}" class="glyphicon glyphicon-trash remove-task" aria-hidden="true" data-tooltip="tooltip" data-placement="right" title="Remove task"></span></td>
								<td></td>
								<td></td>
							</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr class="table-footer">
						<td></td>
						<td><input type="text" id="storyNumber" name="storyNumber" class="form-control"></td>
						<td><input type="text" id="storySummary" name="storySummary" class="form-control"></td>
						<td><input type="text" id="storyPoints" name="storyPoints" class="form-control"></td>
						<td><span class="glyphicon glyphicon-plus-sign add-story"aria-hidden="true" data-tooltip="tooltip" data-placement="right" title="Add user story"></span></td>
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
					<h4 class="modal-title" id="myModalLabel">Add task</h4>
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
	
	<div class="modal fade" id="popupEditTask" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Edit Task</h4>
				</div>
				<form:form cssClass="form-horizontal" id="editTaskForm">
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
						<input type="hidden" name="taskId" value="" id="task-id" />
						<input type="hidden" name="taskType" value="DEV" id="task-type" />
						<button type="button" id="closeEditTask" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<input type="submit" id="save" value="Save" class="btn btn-primary" />
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="popupUserStory" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Edit User Story</h4>
				</div>
				<form:form cssClass="form-horizontal" id="editStoryForm">
					<div class="modal-body">
					
						<div class="form-group">
							<label for="number" class="col-sm-3 control-label">Number
								<span class="required-field">*</span>
							</label>
							<div class="col-sm-9">
								<input type="text" name="number" id="number" class="form-control" value="${story.number}"/>
							</div>
						</div>
						<div class="form-group">
							<label for="summary" class="col-sm-3 control-label">Summary
								<span class="required-field">*</span>
							</label>
							<div class="col-sm-9">
								<input type="text" name="summary" id="summary" class="form-control" value="${story.summary}"/>
							</div>
						</div>
						<div class="form-group">
							<label for="points" class="col-sm-3 control-label">Points <span class="required-field">*</span>
							</label>
							<div class="col-sm-9">
								<input type="text" name="points" id="points" class="form-control" value="${story.points}"/>
							</div>
						</div>

						
					</div>
					<div class="modal-footer">
						<input type="hidden" name="storyId" value="" id="story-id" />
						<button type="button" id="closeEditStory" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<input type="submit" id="save" value="Save" class="btn btn-primary" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<div class="row">
    <div align="center">
		<a href="./planning/downloadPDF.pdf" class="btn btn-primary btn-default generate-pdf">Generuj PDF <span class="glyphicon glyphicon-file"></span></a>
    </div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$('[data-tooltip="tooltip"]').tooltip();
	$('.generate-pdf').click(function() {
		
	}); 
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
					$('#story-step-content').parent().html(html);
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
				$('#story-step-content').parent().html(html);
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
				$('#story-step-content').parent().html(html);
			}
		});
	});
	$('.story-table').on("click", ".edit-story", function() {
		var story = $(this).closest('tr');
		
		$('#editStoryForm').find('#number').val(story.data("number"));
		$('#editStoryForm').find('#summary').val(story.data("summary"));
		$('#editStoryForm').find('#points').val(story.data("points"));
		$('#editStoryForm').find('#story-id').val(story.data("id"));
	});	
	$('#editStoryForm').submit(function(e) {
		var number = $('#editStoryForm').find('#number').val();
		var story_id = $('#editStoryForm').find('#story-id').val();
		var summary = $('#editStoryForm').find('#summary').val();
		var points = $('#editStoryForm').find('#points').val();
		
		$.ajax({
			url : "<spring:url value='/story/editStory.html' />",
			type : "post",
			data : {
				summary : function() {
					return summary;
				},
				points : function() {
					return points;
				},
				storyId : function() {
					return story_id;
				},
				number : function() {
					return number;
				}
			},
			success : function(html) {
				$('#closeEditStory').click();
				$('#story-step-content').parent().html(html);
			}
		});
		return false;
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
				$('#story-step-content').parent().html(html);
			}
		});
		return false;
	});
	$('.story-table').on("click", ".edit-task", function() {
		var task = $(this).closest('tr');
		$('#editTaskForm').find('#task-id').val(task.data('id'));
		$('#editTaskForm').find('#number').val(task.data("number"));
		$('#editTaskForm').find('#summary').val(task.data("summary"));
		$('#editTaskForm').find('#estimation').val(task.data("est"));
		if (task.data("type") == "DEVELOPER_TASK") {
			$('#editTaskForm').find('.task-type-radio[value="DEV"]').prop("checked", true);
		} else {
			$('#editTaskForm').find('.task-type-radio[value="TEST"]').prop("checked", true);
		}
	});
	$('#editTaskForm').submit(function(e) {
		var number = $('#editTaskForm').find('#number').val();
		var task_id = $('#editTaskForm').find('#task-id').val();
		var summary = $('#editTaskForm').find('#summary').val();
		var estimation = $('#editTaskForm').find('#estimation').val();
		var task_type = $('#editTaskForm').find('#task-type').val();
		$.ajax({
			url : "<spring:url value='/task/editTask.html' />",
			type : "post",
			data : {
				summary : function() {
					return summary;
				},
				estimation : function() {
					return estimation;
				},
				taskId : function() {
					return task_id;
				},
				taskType : function() {
					return task_type;
				},
				number : function() {
					return number;
				}
			},
			success : function(html) {
				$('#closeEditTask').click();
				$('#story-step-content').parent().html(html);
			}
		});
		return false;
	});
	$("#addTaskForm .task-type-radio").on("click", function() {
		$("#addTaskForm").find("#task-type-hidden").val($(this).attr("value"));
	});
	$("#editTaskForm .task-type-radio").on("click", function() {
		$("#editTaskForm").find("#task-type").val($(this).attr("value"));
	});
	var task_validation = {
		rules : {
			number : {
				required : true
			},
			summary : {
				required : true
			},
			estimation : {
				required : true
			}
		},
		highlight: function(element) {
			$(element).closest(".form-group").addClass('has-error').addClass('has-feedback');
		},
		unhighlight: function(element) {
			$(element).closest(".form-group").removeClass('has-error').addClass('has-feedback');
		}
	};
	$("#addTaskForm").validate(task_validation);
	$("#editTaskForm").validate(task_validation);
	$("#editStoryForm").validate({
		rules : {
			number : {
				required : true
			},
			summary : {
				required : true
			},
			points : {
				required : true
			}
		},
		highlight: function(element) {
			$(element).closest(".form-group").addClass('has-error').addClass('has-feedback');
		},
		unhighlight: function(element) {
			$(element).closest(".form-group").removeClass('has-error').addClass('has-feedback');
		}
	});
});
</script>
