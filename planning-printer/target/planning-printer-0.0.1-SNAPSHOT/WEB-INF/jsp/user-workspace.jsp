<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

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
					<input type="submit" value="Save" class="btn btn-primary" />
				</div>
			</form:form>
		</div>
	</div>
</div>