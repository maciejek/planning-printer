<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<div class="modal fade bs-example-modal-sm" id="jiraModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Data from Jira available</h4>
      </div>
      <div class="modal-body">
        <p>Do you want to import user stories and tasks from your Jira project to this planning?&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
        <button type="button" class="btn btn-primary" onclick="add_all_from_jira()" >Yes</button>
      </div>
    </div>
  </div>
</div>

<div class="row">
	<p>Select tasks that are not done and should appear in this sprint.</p>
	<br/>

	<c:if test="${importSucceed eq true}">
		<div class="alert alert-success" role="alert">
 			<strong>Import succeed.</strong> Choose now which tasks you would plan.
  		</div>
	</c:if>
	<c:if test="${importFailure eq true}"> 
		<div class="alert alert-danger" role="alert">
 			<strong>Import did not succeed.</strong> You may want to check the connection on My Account page.
  		</div>
	</c:if>
	
	<div class="col-md-1"></div>
	<div class="col-md-10">
		<div class="panel panel-default">
			<table class="table table-hover user-stories">
				<tbody>
					<c:forEach items="${userStories}" var="userStory">
						<tr class="story" data-id="${userStory.id}">
							<td><strong>${userStory.number}</strong></td>
							<td><strong>${userStory.summary}</strong></td>
							<td class="text-center"><span class="badge">${userStory.points} SP</span></td>
						</tr>
						
						<c:forEach items="${userStory.tasks}" var="task">
							<tr class="task" data-id="${task.id}" data-story-id="${userStory.id}" data-complete="${task.complete}">
								<td colspan="3">${task.summary}</td>
							</tr>
							
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="col-md-1"></div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$('#jiraModal').modal('show') 
	$('.user-stories tr.task').each(function() {
		var task = $(this);
		console.log(task.data("complete"));
		if (task.data("complete") == false) {
			task.addClass("selected");
			select_or_deselect_user_story(task.data('story-id'));
		}
	});
	$('.user-stories tr.task').click(function() {
		var task = $(this);
		
		if (task.hasClass('selected')) {
			unselect_task(task);
		} else {
			select_task(task);
		}
	});
	$('.user-stories tr.story').click(function() {
		var story = $(this);
		var story_id = story.data('id');
		
		var tasks = $('.user-stories .task[data-story-id="' + story_id + '"]');
		
		if (story.hasClass('selected')) {
			story.removeClass('selected');
			tasks.each(function() {
				unselect_task($(this));
			});
		} else {
			story.addClass('selected');
			tasks.each(function() {
				select_task($(this));
			});
		}
	});
	function select_task(task) {
		$.ajax({
			url : "<spring:url value='task/setTaskIncomplete.html' />",
			type : "post",
			data : {
				taskId : function() {
					return task.data("id");
				}
			},
			success : function(data) {
				if (data == "true") {
					task.addClass('selected');
					select_or_deselect_user_story(task.data('story-id'));
				}
			}
		});
	}
	function unselect_task(task) {
		$.ajax({
			url : "<spring:url value='task/setTaskComplete.html' />",
			type : "post",
			data : {
				taskId : function() {
					return task.data("id");
				}
			},
			success : function(data) {
				if (data == "true") {
					task.removeClass('selected');
					select_or_deselect_user_story(task.data('story-id'));
				}
			}
		});
	}
	function select_or_deselect_user_story(story_id) {
		var story = $('.user-stories .story[data-id="' + story_id + '"]').eq(0);
		var all_tasks = $('.user-stories .task[data-story-id="' + story_id + '"]');
		
		var selected_tasks = $('.user-stories .selected.task[data-story-id="' + story_id + '"]');
		
		if (selected_tasks.length == all_tasks.length) {
			story.addClass('selected');
		} else {
			story.removeClass('selected');
		}
	}
	

});
function add_all_from_jira() {
	$.ajax({
		url : "<spring:url value='/importFromJira.html' />",
		type : "post",
		data : {
		},
		success : function(data) {

			$('#jiraModal').parent().html(data)
			
		}
	});

}
</script>