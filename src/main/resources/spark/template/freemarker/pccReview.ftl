<!DOCTYPE html>
<html>
<head>
    <#include "partials/_mainHead.ftl">

</head>
<body>
 <#include "partials/_header.ftl">
    <div class="main-content" style=" margin-top: 90px;  margin-left: 10%;">

        <div class="sam-title">
            <h1> PCC REVIEW </h1>
        </div>

      <#if completedPapers?has_content>

          <#list completedPapers as paper>
         <div>  Paper title: <a href="/pccReview/${paper.getId()}"> ${paper.getTitle()}</a> </div>
          </#list>

      <#else>
         No more papers ready for review
      </#if>

    </div>



 <#include "partials/_includedScripts.ftl">


</body>
</html>