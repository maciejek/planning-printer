<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>
<c:if test="${differentPasswords eq true}">
	<div class="alert alert-danger">
		You must enter the same passwords!
	</div>
</c:if>
<c:if test="${passwordChanged eq true}">
	<div class="alert alert-success">
		Your password has been changed successfuly!
	</div>
</c:if>

<div class="bs-example">
	<ul class="nav nav-pills" role="tablist">
		<li role="presentation" class="active"><a href="#">Your
				account</a></li>
		<li role="presentation" class="dropdown"><a id="drop4" href="#"
			class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
			role="button" aria-expanded="false"> Settings <span class="caret"></span>
		</a>
			<ul id="menu1" class="dropdown-menu" role="menu"
				aria-labelledby="drop4">
				<li role="presentation"><a role="menuitem" tabindex="-1"
					href="https://twitter.com/fat" data-toggle="modal"
					data-target="#assignDeputyDialog">Assign a deputy</a></li>
				<li role="presentation"><a role="menuitem" tabindex="-1"
					href="https://twitter.com/fat" data-toggle="modal"
					data-target="#changePasswordDialog">Change password</a></li>
			</ul></li>
	</ul>
	<!-- /pills -->
</div>
<!-- /example -->

<h2>Welcome!</h2>

<p>This is your account page.</p>

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
			<form:form commandName="user" cssClass="form-horizontal"
				action="userC.html">
				<div class="modal-body">
					<div class="form-group">
						<label for="email" class="col-sm-4 control-label">Your deputy email:</label>
						<div class="col-sm-8">
							<form:input path="email" cssClass="form-control" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" value="Save" class="btn btn-primary" />
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
			<form:form cssClass="form-horizontal"
				action="updatePassword.html">
				<div class="modal-body">
					<div class="form-group">
						<label for="password" class="col-sm-4 control-label">Old password:</label>
						<div class="col-sm-8">
							<input type="password" name="oldPassword" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-4 control-label">New password:</label>
						<div class="col-sm-8">
							<input type="password" name="newPassword" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label for="passwordRepeated" class="col-sm-4 control-label">Reenter password:</label>
						<div class="col-sm-8">
							<input type="password" name="passwordRepeated" class="form-control" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" value="Save" class="btn btn-primary" />
				</div>
			</form:form>
		</div>
	</div>
</div>