<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<c:if test="${passwordNotChanged eq true}">
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
     	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
     	Cannot change password!
    </div>
</c:if>
<c:if test="${passwordChanged eq true}">
	<div class="alert alert-success alert-dismissible fade in" role="alert">
     	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
     	Your password has been changed successfuly!
    </div>
</c:if>
<c:if test="${deputyCreated eq true}">
	<div class="alert alert-success alert-dismissible fade in" role="alert">
     	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
     	Your deputy account has been created!
    </div>
</c:if>

<div class="bs-example">
	<ul class="nav nav-pills" role="tablist">
		<li role="presentation" class="active"><a href="#">Your
				account</a></li>
		<li id="settings" role="presentation" class="dropdown"><a id="drop4" href="#"
			class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
			role="button" aria-expanded="false"> Settings <span class="caret"></span>
		</a>
			<ul id="menu1" class="dropdown-menu" role="menu"
				aria-labelledby="drop4">
				<li role="presentation"><a role="menuitem" tabindex="1"
					href="https://twitter.com/fat" data-toggle="modal"
					data-target="#assignDeputyDialog">Assign a deputy</a></li>
				<li role="presentation" id="changePassword"><a role="menuitem" tabindex="2"
					href="https://twitter.com/fat" data-toggle="modal"
					data-target="#changePasswordDialog">Change password</a></li>
			</ul>
		</li>
	</ul>
	<!-- /pills -->
</div>
<!-- /example -->

<h2>Welcome!</h2>

<p>This is your account page.</p>

<br/>

<div class="row">
<div class="col-md-6">
	<div class="panel panel-default">
		<div class="panel-heading">Connect Planning Printer with Jira</div>
		<div class="panel-body">
			<p>Enter your Jira cridentials</p>
      		<form:form action="setupJira.html">
				<input type="text" name="jiraUrl" id="jiraUrl" class="form-control" placeholder="Jira server URL"
					required value="${user.jiraUrl}"> 
				<input type="text" name="jiraLogin" id="jiraLogin" class="form-control" placeholder="Jira login"
					required value="${user.jiraLogin}"> 
				<input type="password" name="jiraPassword" id="jiraPassword" class="form-control"
					placeholder="Jira password" required value="${user.jiraPassword}">
				<p class="text-right">
					<input class="btn btn-primary" type="submit" value="Connect">
				</p>
			</form:form>
			<br/>
			<c:if test="${connectionFailed eq true}">
				<div class="alert alert-danger" role="alert">
	      			<strong>Connection did not succeed.</strong> Make sure if these cridentials are correct. You may also need to login through Jira website first.
	      		</div>
	      	</c:if>
	      	<c:if test="${connectionSuccessful eq true}">
				<div class="alert alert-success" role="alert">
	      			<strong>Connection succeeded.</strong> Choose a project from the list below to pair it with your Planning Printer workspace.
	      		</div>
	      	</c:if>
			<c:if test="${not empty projects}">
			<div class="btn-group"> <a class="btn btn-default dropdown-toggle btn-select2" data-toggle="dropdown" href="#">Select a project<span class="caret"></span></a>
            <ul class="dropdown-menu">
            <form:form action="setupJira.html">
            	<c:forEach items="${projects}" var="project">
                	<li><a class="jiraProjectName" href="#">${project}</a></li>    			
				</c:forEach>
			</form:form>
            </ul>
        	</div>
        	</c:if>
		</div>
	</div>
</div>

<div class="col-md-6">
	<div class="panel panel-default">
		<div class="panel-heading">Create your team</div>
		<div class="panel-body">
			<p>To start working with Planning Printer first you need to
				create your team!</p>
			<p class="text-right">
				<a class="btn btn-primary" href="<spring:url value="/createTeam.html" />" role="button">Create team</a>
			</p>
		</div>
	</div>
</div>
</div>
<!-- Assign a deputy Modal -->
<div class="modal fade" id="assignDeputyDialog" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Assign a deputy</h4>
			</div>
			<form:form commandName="deputy" cssClass="form-horizontal"
				action="createDeputy.html">
				<div class="modal-body">
					<div class="form-group">
						<label for="email" class="col-sm-4 control-label">Your deputy email:</label>
						<div class="col-sm-8 deputy-field">
							<c:if test="${not empty mydeputy}">
								<div class="deputy-assigned" style="padding-top:6px">
									${mydeputy.email}
									<span class="glyphicon glyphicon-remove remove-deputy" data-toggle="tooltip" data-placement="right" title="Remove deputy"></span>
								</div>
								<form:input path="email" name="deputyEmail" cssClass="form-control deputy-email-input" style="display:none"/>
							</c:if>
							<c:if test="${empty mydeputy}">
								<form:input path="email" name="deputyEmail" cssClass="form-control deputy-email-input" />
							</c:if>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" id="saveDeputy" value="Save" class="btn btn-primary" />
				</div>
			</form:form>
		</div>
	</div>
</div>


<!-- Change password Modal -->
<div class="modal fade" id="changePasswordDialog" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Change password</h4>
			</div>
			<form:form cssClass="form-horizontal change-password-form"
				action="updatePassword.html">
				<div class="modal-body">
					<div class="form-group">
						<label for="oldPassword" class="col-sm-4 control-label">Old password <span class="required-field">*</span></label>
						<div class="col-sm-8">
							<input type="password" name="oldPassword" id="oldPassword" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label for="newPassword" class="col-sm-4 control-label">New password <span class="required-field">*</span></label>
						<div class="col-sm-8">
							<input type="password" name="newPassword" id="newPassword" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label for="confirmPassword" class="col-sm-4 control-label">Confirm password <span class="required-field">*</span></label>
						<div class="col-sm-8">
							<input type="password" name="confirmPassword" id="confirmPassword" class="form-control" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" id="save" value="Save" class="btn btn-primary" />
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$(".remove-deputy").click(function() {
		$.ajax({
			url : "<spring:url value='/removeDeputy.html' />",
			type : "post",
			success : function(data) {
				if (data == "true") {
					$('.deputy-assigned').hide();
					$('.deputy-email-input').show();
				}
			}
		});
	});
	$(".change-password-form").validate(
		{
			rules : {
				oldPassword : {
					required : true,
					remote : {
						url : "<spring:url value='/passwordCorrect.html' />",
						type : "get",
						data : {
							oldPassword : function() {
								return $("#oldPassword").val();
							}
						}
					}
				},
				newPassword : {
					required : true,
					minlength : 5
				},
				confirmPassword : {
					required : true,
					minlength : 5,
					equalTo : "#newPassword"
				}
			},
			highlight: function(element) {
				$(element).closest(".form-group").removeClass('has-success').addClass('has-error').addClass('has-feedback');
			},
			unhighlight: function(element) {
				$(element).closest(".form-group").removeClass('has-error').addClass('has-success').addClass('has-feedback');
			},
			messages : {
				oldPassword : {
					remote : "Password is incorrect!"
				}
			}
		}
	);
	
	$('.jiraProjectName').click(function() {
		var task = $(this).text();
		project_selected(task);
	});
});

$(".dropdown-menu li a").click(function(){
	  var selText = $(this).text();
	  $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
	});
	
function project_selected(projectName) {
	$.ajax({
		url : "<spring:url value='/projectSelected.html' />",
		type : "post",
		data : {
			projectNameParam : function() {
				return projectName;
			}
		},
		success : function(data) {
			if (data == "true") {
				//window.alert("2");
			}
		}
	});
}
</script>