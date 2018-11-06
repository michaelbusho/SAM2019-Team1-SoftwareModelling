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
    <#if errorExistingFile??>
        ${errorExistingFile}<br><br>

    </#if>
    <div class="row" style="padding-top: 5%;">
        <div class="col-md-6 papers">
            Submitted papers<br><br>
            <input type="button" value="Submit new paper" onclick="toggleForm()"><br><br>
            <form id="paperForm" enctype="multipart/form-data" class="paperForm" style="display: none" action="/submitPaper" method="POST" >
                <input type="text" name="title" placeholder="Title of the paper" required="required"><br><br>
                <input type="text" name="format" placeholder="Specify the format (ACM/IEEE, etc)" required="required"><br><br>
                <input type="checkbox" name="version" value="on"> is this a new version of an existing paper?</input><br><br>
                <input type="text" name="authors" placeholder="Authors (separate by coma(,)" required="required"><br><br>
                <input type="text" name="contactAuthor" placeholder="Contact Author" readonly="readonly" value=${userName} ><br><br>
                <label for="paper">Paper:</label><br>
                <input type="file" required="required"
                       id="paper" name="paper"
                       accept=".pdf,.doc,.docx" /><br><br>
                <input type="submit" value="Submit Paper">
            </form>


        </div>

        <div class="col-md-2 latest">
            Latest notifications
        </div>
    </div>

</div>

 <#include "partials/_includedScripts.ftl">
<script src="/javascript/submissionForm.js"></script>


</body>
</html>