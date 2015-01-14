<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<c:if test="${passwordNotChanged eq true}">
	<div class="alert alert-danger">
		Cannot change password!
	</div>
</c:if>
<c:if test="${passwordChanged eq true}">
	<div class="alert alert-success">
		Your password has been changed successfuly!
	</div>
</c:if>
<c:if test="${deputyCreated eq true}">
	<div class="alert alert-success">
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
			<div class="alert alert-warning" role="alert">
      			<strong>Warning!</strong> You may need to login through Jira website first.
      		</div>
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
						<div class="col-sm-8">
							<c:if test="${not empty mydeputy}">
								${mydeputy.email}
							</c:if>
							<c:if test="${empty mydeputy}">
								<form:input path="email" name="deputyEmail" cssClass="form-control" />
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
});
</script>