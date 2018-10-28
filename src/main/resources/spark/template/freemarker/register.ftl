<!DOCTYPE html>
<html>
<head>

    <#include "partials/_mainHead.ftl">
    <link rel="stylesheet" type="text/css" href="/styles/register.css">

</head>
<body>

<div class="sam-title">
    <h1> SAM 2019 </h1>
</div>

<div class="signup-form">
    <form action="/register" method="POST">
        <h2>Sign Up</h2>
        <p>Please fill in this form to create an account!</p>
        <hr>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <input type="text" class="form-control" name="username" placeholder="Username" required="required">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-paper-plane"></i></span>
                <input type="email" class="form-control" name="email" placeholder="Email Address" required="required">
            </div>
        </div>
        <div class="form-group">"
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <input type="password" id="password" class="form-control" name="password" placeholder="Password" required="required" pattern="[^()/><\][\\\x22,;|]+" title="Password contains invalid characters (()/><\][\,;|]">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
				<span class="input-group-addon">
					<i class="fa fa-lock"></i>
					<i class="fa fa-check"></i>
				</span>
                <input type="password" id="confirm_password" class="form-control" name="confirm_password" placeholder="Confirm Password" required="required">
            </div>
        </div>
        <#if message??>
        <div class="text-center alert-danger" >${message}</div>
        </#if>
        <div class="form-group ">
            <button type="submit" class="btn btn-primary btn-lg center-block">Sign Up</button>
            <script src="/javascript/register.js"></script>
        </div>
    </form>

    <div class="text-center">Already have an account? <a href="/">Login here</a></div>
</div>





  <#include "partials/_includedScripts.ftl">

</body>
</html>