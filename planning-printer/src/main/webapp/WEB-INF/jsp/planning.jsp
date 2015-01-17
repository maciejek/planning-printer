<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<h2>Start planning</h2>

<p>Here you can start planning your sprint!</p>

<br />

<ul class="nav nav-tabs nav-justified">
	<li role="presentation" class="nav-step active" id="step1-nav"><a href="#step1">Step 1</a></li>
	<li role="presentation" class="nav-step" id="step2-nav"><a href="#step2">Step 2</a></li>
	<li role="presentation" class="nav-step" id="step3-nav"><a href="#step3">Step 3</a></li>
</ul>

<br />

<div class="tab-content">
	<div style="display: none" class="ajax-loader">
		<span class="loader"></span>
	</div>
	<div class="planning-step col-md-12" id="step1">
		<div class="row">
			<p>Specify hours for each member of your team.</p>
			<br />
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div class="col-md-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<strong>Developers</strong>
						</div>

						<table class="table table-hover developers-hours">
							<thead>
								<tr>
									<th class="text-center">Developer</th>
									<th class="text-center">Hours</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${developers}" var="developer">
									<tr>
										<td>${developer.name} ${developer.surname}</td>
										<td class="text-center"><input type="text"
											class="developers-hours-input form-control"></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr class="hours-summary">
									<td><strong>Summary:</strong></td>
									<td class="text-center"><span id="developers-hours-sum">0</span></td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
				<div class="col-md-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<strong>Testers</strong>
						</div>

						<table class="table table-hover testers-hours">
							<thead>
								<tr>
									<th class="text-center">Tester</th>
									<th class="text-center">Hours</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${testers}" var="tester">
									<tr>
										<td>${tester.name} ${tester.surname}</td>
										<td class="text-center"><input type="text"
											class="testers-hours-input form-control"></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr class="hours-summary">
									<td><strong>Summary:</strong></td>
									<td class="text-center"><span id="testers-hours-sum">0</span></td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
			<div class="col-md-1"></div>

		</div>
		<div class="row">
			<nav>
				<ul class="pager">
					<li class="next nav-step"><a href="#step2">Next <span
							aria-hidden="true">&rarr;</span></a></li>
				</ul>
			</nav>
		</div>
	</div>

	<div class="planning-step col-md-12" id="step2">
		<div class="step-content"></div>
		<div class="row">
			<nav>
				<ul class="pager">
					<li class="previous nav-step"><a href="#step1"><span
							aria-hidden="true">&larr;</span> Prev</a></li>
					<li class="next nav-step"><a href="#step3">Next <span
							aria-hidden="true">&rarr;</span></a></li>
				</ul>
			</nav>
		</div>
	</div>

	<div class="planning-step col-md-12" id="step3">
		<div class="row">
			<p class="description">Add new user stories and tasks here.</p>
			<br />
		</div>
		<div class="step-content"></div>
		<div class="row">
			<nav>
				<ul class="pager">
					<li class="previous nav-step"><a href="#step2"><span
							aria-hidden="true">&larr;</span> Prev</a></li>
				</ul>
			</nav>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$('.planning-step').hide();
	$('#step1').show();
	$('.nav-step').click(function() {
		var stepId = $(this).find('a').attr('href');
		if (stepId == "#step3") {
			$.ajax({
				url : "<spring:url value='/task.html' />",
				type : "post",
				data : {
					tasksJSONObject : get_incomplete_tasks_and_stories_as_json_object(),
					planning : true
				},
				beforeSend : function() {
					display_loader();
				},
				success : function(data) {
					hide_loader();
					go_to_step(stepId);
					$(stepId).find('.step-content').html(data);
				}
			});
		} else if (stepId == "#step2") {
			$.ajax({
				url : "<spring:url value='/planning/loadStep2.html' />",
				type : "post",
				beforeSend : function() {
					display_loader();
				},
				success : function(data) {
					hide_loader();
					go_to_step(stepId);
					$(stepId).find('.step-content').html(data);
				}
			});
		} else {
			go_to_step(stepId);
		}
	});
	$('.developers-hours-input').blur(function() {
		var hours = $('.developers-hours-input');
		$('#developers-hours-sum').text(sum_input_values(hours));
	});
	$('.testers-hours-input').blur(function() {
		var hours = $('.testers-hours-input');
		$('#testers-hours-sum').text(sum_input_values(hours));
	});
	function sum_input_values(inputs) {
		var sum = 0;
		for (i = 0; i < inputs.length; i++) {
			var number = parseInt(inputs.eq(i).val());
			if (!isNaN(number)) {
				sum += number
			} else {
				sum += 0;
			}
		}
		return sum;
	}
});
</script>
