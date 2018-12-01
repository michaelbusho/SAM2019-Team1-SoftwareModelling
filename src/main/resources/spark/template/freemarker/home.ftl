<!DOCTYPE html>
<html>
<head>
    <#include "partials/_mainHead.ftl">
    <link rel="stylesheet" type="text/css" href="/styles/homeLogin.css">
</head>
<body>

<div class="sam-title">
    <h1> SAM 2019 </h1>
</div>


<div class="login-form">
    <form action="/" method="POST">
        <div class="avatar">
            <img src="/img/avatar.png" alt="Avatar">
        </div>
        <h2 class="text-center">User Login</h2>
        <hr>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fa fa-user"></i></span>
                    <input type="text" class="form-control" name="username" placeholder="Username" required="required">
                </div>

            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fa fa-lock"></i></span>
                    <input type="password" class="form-control" name="password" placeholder="Password" required="required">
                </div>
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-lg btn-block">Sign in</button>
        </div>

        <#if message??>
            <div class="message ">${message}</div>
        </#if>
        <!--
        <div class="clearfix">
            <label class="pull-left checkbox-inline"><input type="checkbox"> Remember me</label>
            <a href="#" class="pull-right">Forgot Password?</a>
        </div>
        -->
    </form>
    <p class="text-center">Don't have an account? <a href="/register">Sign up here!</a></p>
</div>


 <#include "partials/_includedScripts.ftl">
<script src="Javascript/homelogin.js"></script>
</body>
</html>