<!DOCTYPE html>
<html>
<head>
    <#include "partials/_mainHead.ftl">

</head>
<body>
 <#include "partials/_header.ftl">
    <div class="main-content" style=" margin-top: 90px;  margin-left: 10%;">

        <div class="sam-title">
            <h1> PCM REVIEW </h1>
        </div>

        <h2>You are assigned to review these papers</h2>
     <#if papers?has_content>

         <#list papers as paper>
         <div>  Paper title: <a href="/pcmReview/${paper.getId()}"> ${paper.getTitle()}</a> </div>
         </#list>

     <#else>
     No more papers for review
     </#if>

    </div>



 <#include "partials/_includedScripts.ftl">


</body>
</html>