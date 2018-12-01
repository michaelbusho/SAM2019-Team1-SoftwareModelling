<!DOCTYPE html>
<html>
<head>
    <#include "partials/_mainHead.ftl">
    <link rel="stylesheet" type="text/css" href="/styles/pcmReviewPaper.css">



</head>
<body>
 <#include "partials/_header.ftl">
    <div class="main-content" style=" margin-top: 90px;  margin-left: 10%;">

        <div class="sam-title">
            <h1>Title:  ${paper.getTitle()} </h1>
        </div>

        <h2>Author: ${paper.getContactAuthor()}</h2>




        <div class="container">
            <div class="row" style="margin-top:40px;">
                <div class="col-md-6">
                    <div class="well well-sm">

                        <div class="row" id="post-review-box" >
                            <div class="col-md-12">

                                <form accept-charset="UTF-8" action=" /pcmReview/${paper.getId()}" method="POST">
                                    <input id="ratings-hidden" name="rating" type="hidden">
                                    <textarea class="form-control animated" cols="50" id="new-review" name="comment" placeholder="Enter your review here..." rows="5"  required="required"></textarea>

                                    <div class="text-right">

                                        <input id="input-1" name="input-1" class="rating rating-loading" data-min="0" data-max="5" data-step="1" value="1" data-size="md">

                                        <a class="btn btn-danger btn-sm" href="/pcmReview" id="close-review-box" margin-right: 10px;">
                                            <span class="glyphicon glyphicon-remove"></span>Cancel</a>
                                        <button class="btn btn-success btn-lg" type="submit">Save</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>





    </div>



 <#include "partials/_includedScripts.ftl">
<script src="/javascript/pcmReviewPaper.js"></script>



</body>
</html>