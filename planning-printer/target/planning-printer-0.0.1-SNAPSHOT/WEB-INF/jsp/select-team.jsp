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
<c:if test="${deputyCreated eq true}">
	<div class="alert alert-success">
		Your deputy account has been created!
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

<h2>Add new Programmer/Tester!</h2>

<p>This is your account page.</p>


