<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<div class="row">
<div class="col-md-2"></div>
<form:form commandName="user" cssClass="form-horizontal registration-form col-md-8">
	<c:if test="${param.success eq true}">
		<div class="alert alert-success alert-dismissible fade in" role="alert">
	     	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
	     	Registration successful!
	    </div>
	</c:if>
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label">Email <span class="required-field">*</span></label>
		<div class="col-sm-10">
			<form:input path="email" id="email" cssClass="form-control"/>
			<form:errors path="email"/>
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password <span class="required-field">*</span></label>
		<div class="col-sm-10">
			<form:password path="password" id="password" name="password" cssClass="form-control"/>
			<form:errors path="password"/>
		</div>
	</div>
	<div class="form-group">
		<label for="confirmPassword" class="col-sm-2 control-label">Confirm password <span class="required-field">*</span></label>
		<div class="col-sm-10">
			<input type="password" name="confirmPassword" id="confirmPassword" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2 right">
			<input type="submit" value="Register" class="btn btn-lg btn-primary"/>
		</div>
	</div>
</form:form>
</div>
<div class="col-md-2"></div>

<script type="text/javascript">
$(document).ready(function() {
	$(".registration-form").validate(
		{
			rules: {
				email: {
					required : true,
					email : true,
					remote : {
						url : "<spring:url value='/register/available.html' />",
						type : "get",
						data : {
							email : function() {
								return $("#email").val();
							}
						}
					}
				},
				password: {
					required : true,
					minlength : 5
				},
				confirmPassword: {
					required : true,
					minlength : 5,
					equalTo : "#password"
				}
			},
			highlight: function(element) {
				$(element).closest(".form-group").removeClass('has-success').addClass('has-error').addClass('has-feedback');
				$("span[id$='errors']").remove();
			},
			unhighlight: function(element) {
				$(element).closest(".form-group").removeClass('has-error').addClass('has-success').addClass('has-feedback');
				$("span[id$='errors']").remove();
			},
			messages : {
				email : {
					remote : "Such email already exists!"
				}
			}
		}
	);
	
	$("span[id$='errors']").closest('.form-group').addClass('has-error');
});
</script>