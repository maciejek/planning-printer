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
						<th>Number</th>
						<th>Description</th>
						<th>SP</th>
						<th></th>
						<th></th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${stories}" var="story">
						<tr class="user-story-tr" data-id="${story.id}">
							<td></td>
							<td class="story-number">${story.number} </td>
							<td class="story-summary">${story.summary}</td>
							<td class="story-points">${story.points}</td>
							<td><span class="glyphicon glyphicon-trash remove-story" aria-hidden="true"></span></td>
							<td><span class="glyphicon glyphicon-plus-sign add-task" data-toggle="modal" data-target="#popupAddTask" aria-hidden="true"></span></td>
						</tr>
						
						<c:forEach items="${story.tasks}" var="task">
							<tr class="task-tr" data-story-id="${story.id}">
								<td style="padding-left: 20px;" colspan="5">
									<span style="padding-left: 80px; padding-right: 20px ;" >${task.number}</span>
									<span style="padding-left: 20px;" class="badge">${task.estimation}</span>
									<span style="padding-right: 10px;" > ${task.summary} </span> 
									<span class="label label-primary">${task.type}</span>
								</td>
								<td><span id="${task.id}" class="glyphicon glyphicon-minus-sign remove-task" aria-hidden="true"></span></td>
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
						<td><span class="glyphicon glyphicon-plus-sign add-story"aria-hidden="true"></span></td>
						<td></td>	
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	<div class="col-md-1"></div>
	
	<div class="panel panel-default" style="position:fixed; bottom: 10px; right: 40px">
		<table class="table">
			<tr>
				<td style="text-align:right">
					Developer tasks hours:<br/>
					Developers hours:<br/>
					Summary:
				</td>
				<td>
					<span class="dev-total-hours">0</span><br/>
					<span class="dev-team-hours">0</span><br/>
					<span class="dev-summary">0</span>
				</td>
			</tr>
			<tr>
				<td style="text-align:right">
					Tester tasks hours:<br/>
					Testers hours:<br/>
					Summary:
				</td>
				<td>
					<span class="test-total-hours">0</span><br/>
					<span class="test-team-hours">0</span><br/>
					<span class="test-summary">0</span>
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
							<label for="summary" class="col-sm-4 control-label">Summary
								<span class="required-field">*</span>
							</label>
							<div class="col-sm-8">
								<input type="text" name="summary" id="summary" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="estimation" class="col-sm-4 control-label">
								Estimation <span class="required-field">*</span>
							</label>
							<div class="col-sm-8">
								<input type="text" name="estimation" id="estimation" class="form-control" />
							</div>
						</div>

						<div class="form-group">

							<label for="estimation" class="col-sm-4 control-label">
								Type <span class="required-field">*</span>
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
					</div>
					<div class="modal-footer">
						<input type="hidden" name="storyId" value="" id="task-story-id" />
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
        <h1>Spring MVC PDF View Demo (using iText library)</h1>
        <h3><a href="/planning/downloadPDF">Download PDF Document</a></h3>
    </div>

<script type="text/javascript">
$(document).ready(function() {
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
	$('.story-table').on("click", ".add-task", function() {
		$('#task-story-id').val($(this).closest('tr').data('id'));
	});	
	$('#addTaskForm').submit(function(e) {
		var story_id = $('#task-story-id').val();
		var summary = $('#summary').val();
		var estimation = $('#estimation').val();
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
