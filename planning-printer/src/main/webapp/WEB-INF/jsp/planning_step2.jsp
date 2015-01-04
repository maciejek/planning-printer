<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<div class="row">
	<p>Select tasks that are not finished.</p>
	<br/>
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
							<tr class="task" data-id="${task.id}" data-story-id="${userStory.id}">
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
	$('.user-stories tr.task').click(function() {
		var task = $(this);
		var story_id = task.data('story-id');
		var story = $('.user-stories .story[data-id="' + story_id + '"]').eq(0);
		var all_tasks = $('.user-stories .task[data-story-id="' + story_id + '"]');
		
		if (task.hasClass('selected')) {
			task.removeClass('selected');
		} else {
			task.addClass('selected');
		}
		
		var selected_tasks = $('.user-stories .selected.task[data-story-id="' + story_id + '"]');
		
		if (selected_tasks.length == all_tasks.length) {
			story.addClass('selected');
		} else {
			story.removeClass('selected');
		}
	});
	$('.user-stories tr.story').click(function() {
		var story = $(this);
		var story_id = story.data('id');
		var tasks = $('.user-stories .task[data-story-id="' + story_id + '"]');
		console.log(tasks);
		if (story.hasClass('selected')) {
			story.removeClass('selected');
			tasks.each(function() {
				$(this).removeClass('selected');
			});
		} else {
			story.addClass('selected');
			tasks.each(function() {
				$(this).addClass('selected');
			});
		}
	});
});
</script>