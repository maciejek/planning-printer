function display_loader() {
	$('.ajax-loader').show();
	$('.tab-content').addClass('white-overlay');
}

function hide_loader() {
	$('.ajax-loader').hide();
	$('.tab-content').removeClass('white-overlay');
}

function go_to_step(stepId) {
	$('.nav-step').removeClass('active');
	$(stepId + '-nav').addClass('active');
	$('.planning-step').hide();
	$(stepId).show();
}

function get_incomplete_tasks_and_stories_as_json_object() {
	var tasks = $('.user-stories .task.selected');
	var stories = [];
	var json_object = "{";
	json_object += "incomplete_tasks: [";
	for (i = 0; i < tasks.length; i++) {
		json_object += "{id : " + tasks.eq(i).data("id") + "}";
		json_object += i < tasks.length - 1 ? "," : "";
		stories.push(tasks.eq(i).data("story-id"));
	}
	json_object += "],"; 
	json_object += "incomplete_stories: [";
	for (i = 0; i < stories.length; i++) {
		json_object += "{id : " + stories[i] + "}";
		json_object += i < stories.length - 1 ? "," : "";
	}
	json_object += "]"; 
	json_object += "}";
	return json_object;
}