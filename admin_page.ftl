<!DOCTYPE html>
<html>
<head>
    <#include "partials/_mainHead.ftl">
    <link rel="stylesheet" type="text/css" href="/styles/profile.css">
</head>
<body>

 <#include "partials/_header.ftl">

<div class="main-content col-md-10 ">
    <div class="Greeting">
        Welcome <h4 style="display: inline;"><#if userName??> ${userName}</#if> </h4> to administration page.
    </div>
    <input type="button" value="Approve paper" onclick="toggleForm()"><br><br>
    </form>

<#if uploadMessage??>
    ${uploadMessage}<br><br>
</#if>

</div>

 <#include "partials/_includedScripts.ftl">
<script src="/javascript/submissionForm.js"></script>


</body>
</html>