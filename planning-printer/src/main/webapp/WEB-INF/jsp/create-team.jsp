<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<h2>Create your team</h2>

<p>Here you can create your team.</p>

<br />

<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-10">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>Developers</strong></div>

			<table class="table table-hover developers-table">
				<thead>
					<tr>
						<th></th>
						<th>Name</th>
						<th>Surname</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${developers}" var="developer">
						<tr>
							<td></td>
							<td>${developer.name}</td>
							<td>${developer.surname}</td>
							<td><span id="${developer.id}" class="glyphicon glyphicon-trash remove-employee" 
							aria-hidden="true"></span></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr class="table-footer">
						<td></td>
						<td><input type="text" id="developerName"
							name="developerName" class="form-control"></td>
						<td><input type="text" id="developerSurname"
							name="developerSurname" class="form-control"></td>
						<td><span class="glyphicon glyphicon-plus-sign add-employee"
							aria-hidden="true"></span></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	<div class="col-md-1"></div>
</div>

<br/>

<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-10">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>Testers</strong></div>

			<table class="table table-hover testers-table">
				<thead>
					<tr>
						<th></th>
						<th>Name</th>
						<th>Surname</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${testers}" var="tester">
						<tr>
							<td></td>
							<td>${tester.name}</td>
							<td>${tester.surname}</td>
							<td><span id="${tester.id}" class="glyphicon glyphicon-trash remove-employee" 
							aria-hidden="true"></span></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr class="table-footer">
						<td></td>
						<td><input type="text" id="testerName"
							name="testerName" class="form-control"></td>
						<td><input type="text" id="testerSurname"
							name="testerSurname" class="form-control"></td>
						<td><span class="glyphicon glyphicon-plus-sign add-employee"
							aria-hidden="true"></span></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	<div class="col-md-1"></div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('.developers-table .add-employee').click(function() {
			var developerName = $("#developerName").val();
			var developerSurname = $("#developerSurname").val();
			if (developerName != "" && developerSurname != "") {
				$.ajax({
					url : "<spring:url value='/workspace/addDeveloper.html' />",
					type : "get",
					data : {
						name : function() {
							return developerName;
						},
						surname : function() {
							return developerSurname;
						},
					},
					success : function(id) {
						$(".developers-table tbody").append("<tr><td></td>" +
								"<td>" + developerName + "</td><td>" + developerSurname + "</td>" +
								"<td><span id=" + id + " class='glyphicon glyphicon-trash remove-employee' " + 
								"aria-hidden='true'></span></td></tr>");
						$("#developerName").val("");
						$("#developerSurname").val("");
					},
					error : function(data) {
						console.log("Error!");
					}
				});
			}
		});
		
		$('.developers-table').on("click", ".remove-employee", function() {
			var id = $(this).attr("id");
			$.ajax({
				url : "<spring:url value='/workspace/removeEmployee.html' />",
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
		
		$('.testers-table .add-employee').click(function() {
			var testerName = $("#testerName").val();
			var testerSurname = $("#testerSurname").val();
			if (testerName != "" && testerSurname != "") {
				$.ajax({
					url : "<spring:url value='/workspace/addTester.html' />",
					type : "get",
					data : {
						name : function() {
							return testerName;
						},
						surname : function() {
							return testerSurname;
						},
					},
					success : function(id) {
						$(".testers-table tbody").append("<tr><td></td>" +
								"<td>" + testerName + "</td><td>" + testerSurname + "</td>" +
								"<td><span id=" + id + " class='glyphicon glyphicon-trash remove-employee' " + 
								"aria-hidden='true'></span></td></tr>");
						$("#testerName").val("");
						$("#testerSurname").val("");
					},
					error : function(data) {
						console.log("Error!");
					}
				});
			}
		});
		
		$('.testers-table').on("click", ".remove-employee", function() {
			var id = $(this).attr("id");
			$.ajax({
				url : "<spring:url value='/workspace/removeEmployee.html' />",
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
	});
</script>