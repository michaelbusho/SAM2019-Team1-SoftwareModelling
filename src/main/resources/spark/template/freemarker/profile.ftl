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
        Hello <h3 style="display: inline;"><#if userName??> ${userName}</#if> </h3>
    </div>

    <div class ="row" style="padding-top: 5%;">
        <div class="col-md-6 papers">
            Submitted papers
        </div>

        <div class="col-md-2 latest">
            Latest notifications
        </div>
    </div>

</div>

 <#include "partials/_includedScripts.ftl">

</body>
</html>