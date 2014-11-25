<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<h1>${user.email}</h1>


<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
	data-target="#myModal">Assign a deputy</button>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Assign a deputy</h4>
			</div>
			<div class="modal-body">


				<form:form commandName="user" cssClass="form-horizontal"
					action="userC.html">
					<div class="form-group">
						<label for="email" class="col-sm-2 control-label">Email:</label>
						<div class="col-sm-10">
							<form:input path="email" cssClass="form-control" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2">
							<input type="submit" value="Save" class="btn btn-lg btn-primary" />
						</div>
					</div>
				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>


<form:form commandName="user" cssClass="form-horizontal"
	action="updatePassword.html">
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Your new
			password:</label>
		<div class="col-sm-10">
			<form:password path="password" cssClass="form-control" />
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<input type="submit" value="Update password"
				class="btn btn-lg btn-primary" />
		</div>
	</div>
</form:form>