<!DOCTYPE html>
<html>
<head>
    <#include "partials/_mainHead.ftl">
    <link rel="stylesheet" type="text/css" href="/styles/profile.css">
</head>
<body>

 <#include "partials/_header.ftl">

<div class="main-content col-sm-6 col-md-8 col-lg-10">

    <div class="row">
        <div class="profile">
            <div class="profile-header">
                <!-- BEGIN profile-header-cover -->
                <div class="profile-header-cover"></div>
                <!-- END profile-header-cover -->
                <!-- BEGIN profile-header-content -->
                <div class="profile-header-content">
                    <!-- BEGIN profile-header-img -->
                    <div class="profile-header-img">
                        <img src="/img/user.png" alt="user">
                    </div>
                    <!-- END profile-header-img -->
                    <!-- BEGIN profile-header-info -->
                    <div class="profile-header-info">
                        <h4 class="m-t-10 m-b-5">${user.getUserName()}</h4>
                        <p class="m-b-10"> ${user.getType()}</p>
                    </div>
                    <!-- END profile-header-info -->
                </div>
                <!-- END profile-header-content -->
             </div>
        </div>
    </div>

    <div class="row spaceDevide">

    </div>


    <div class="container float-left">
        <div class="row">




            <div class="col-md-8 list">
                <div class="d-flex flex-column flex-md-row mb-3 mb-md-0">
                    <nav class="mr-auto d-flex align-items-center" aria-label="breadcrumb" >
                        <ol class="breadcrumb" >
                            <li>
                                <div class="input-group" >
                                    <input placeholder="Search for..." type="text" class="form-control" >
                                    <div class="input-group-append">
                                        <button class="btn btn-secondary"><i class="fa fa-search"></i></button>
                                    </div>
                                </div>
                            </li>

                        </ol>
                    </nav>
                    <div role="toolbar" class="btn-toolbar">

                        <div role="group" class="btn-group">
                            <button id="tooltipAddNew" class="align-self-center btn btn-primary"><i class="fa-fw fa fa-plus"></i></button>
                        </div>
                    </div>
                </div>
                <div class="mb-3 Card_custom-card--border_5wJKy card">
                    <div class="table-responsive-xl">
                        <table class="mb-0 table table-hover">
                            <thead>
                            <tr>
                                <th class="align-middle bt-0" style="display: none"></th>
                                <th class="align-middle bt-0">Title</th>
                                <th class="align-middle bt-0">Submitter</th>
                                <th class="align-middle bt-0">Authors</th>
                                <th class="align-middle bt-0">Status</th>
                                <th class="align-middle bt-0">PDF</th>
                                <th class="align-middle bt-0 text-right">Actions</th>
                            </tr>
                            </thead>
                            <tbody class="table-content">

                             <#if uploadedPapers ??>

                                 <#list uploadedPapers as paper>
                                 <!-- A paper-->
                                    <tr>
                                        <td class="align-middle tbl-id" style="display: none">
                                            <div>${paper.getId()}</div>
                                        </td>

                                        <td class="align-middle tbl-title">
                                            <div>${paper.getTitle()}</div>
                                        </td>
                                        <td class="align-middle tbl-submitter">
                                            <div> ${paper.getContactAuthor()}</div>
                                        </td>

                                        <td class="align-middle tbl-authors">
                                            <div> ${paper.getAuthors()}</div>
                                        </td>
                                        <td class="align-middle tbl-status"><span class="   <#if paper.getStatus() == "Complete"> badge badge-success badge-pill <#elseif paper.getStatus() == "Expired">badge badge-danger badge-pill  <#else> badge badge-warning badge-pill  </#if> ">
                                            ${paper.getStatus()}
                                        </span>
                                        </td>
                                        <td class="align-middle">      <a href="#"><i class="far fa-file-pdf fa_custom fa-2x"></i> </a></td>
                                        <td class="align-middle text-right">
                                            <div class="btn-group">

                                                <button  class="btn dropdown-toggle paper-options-btn" type="button" data-toggle="dropdown"><i class="fas fa-cog"></i></button>
                                                <div tabindex="-1" role="menu" aria-hidden="true" class="dropdown-menu dropdown-menu-right">

                                                    <!--    <button type="button" tabindex="0" class="dropdown-item"><i class="drop-icon far fa-edit"></i> Edit </button>  -->

                                                     <#if user.getType() == "PCM" && paper.getContactAuthor() != user.getUserName()>
                                                      <button type="button" tabindex="0" class="dropdown-item request"><i class="drop-icon far fa-edit"></i> Request review </button>
                                                     </#if>


                                                    <div tabindex="-1" class="dropdown-divider"></div>
                                                    <!-- PCC can assign reviewers-->
                                                     <#if user.getType() == "PCC" >

                                                         <#if paper.getSubmitters() ?has_content >

                                                             <#list paper.getSubmitters() as submitter>
                                                                <button type="button" tabindex="0" class="dropdown-item"><i class="drop-icon far fa-user"></i>  ${submitter}</button>
                                                             </#list>

                                                             <#if paper.getSubmitters()?size < 3 >
                                                                   <button type="button" tabindex="0" class="dropdown-item asign reviewer1"><i class="drop-icon far fa-plus-square"></i> Assign reviewer</button>
                                                             </#if>

                                                         <#else>
                                                          <button type="button" tabindex="0" class="dropdown-item asign reviewer1"><i class="drop-icon far fa-plus-square"></i> Assign reviewer</button>
                                                         </#if>



                                                     </#if>


                                                    <!-- extra functions
                                                    <button type="button" tabindex="0" class="dropdown-item"><i class="drop-icon fa fa-fw fa-ticket mr-2"></i>Add Task</button>
                                                    <button type="button" tabindex="0" class="dropdown-item"><i class="drop-icon fa fa-fw fa-paperclip mr-2"></i>Add Files</button>  -->
                                                    <div tabindex="-1" class="dropdown-divider"></div>
                                                    <button type="button" id="deletePaper" tabindex="0" class="dropdown-item deletePaper"><i class="drop-icon far fa-trash-alt"></i> Delete</button>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <!-- end of a paper-->
                                     </#list>
                                 <#else>
                                     <tr>
                                         <td colspan="5"> You have no papers submited. </td>
                                     </tr>
                                 </#if>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>








             <div class="col-md-4 latest float-right">
               <div  class="breadcrumb latest-title">Latest Notifications </div>
                <hr>

                 <div class="latest-content-wrapper">

                     <!-- notification!! -->
                     <div class="g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-15 g-pa-20--md g-mb-30 latest-content">

                         <div class="noty_bar noty_type__warning noty_theme__unify--v1 g-mb-25">
                             <div class="noty_body">
                                 <div class="g-mr-20">
                                     <div class="noty_body__icon">
                                         <i class="hs-admin-bolt"></i>
                                     </div>
                                 </div>
                             <div>The submission deadline for this period is in 3 days!</div>
                         </div>
                     </div>
                     <div class="notificationDate"> 07-11-2018 16:42 </div>


                     <!-- notification!! -->
                     <div class="g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-15 g-pa-20--md g-mb-30 latest-content">
                         <div class="noty_bar noty_type__success noty_theme__unify--v1 g-mb-25">
                             <div class="noty_body">
                                 <div class="g-mr-20">
                                     <div class="noty_body__icon">
                                         <i class="hs-admin-bolt"></i>
                                     </div>
                                 </div>
                                 <div>One of your papers has been reviewed.</div>
                             </div>
                         </div>
                         <div class="notificationDate"> 07-11-2018 16:42 </div>

                         <!-- notification!! -->
                         <div class="g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-15 g-pa-20--md g-mb-30 latest-content">
                             <div class="noty_bar noty_type__error noty_theme__unify--v1 g-mb-25">
                                 <div class="noty_body">
                                     <div class="g-mr-20">
                                         <div class="noty_body__icon">
                                             <i class="hs-admin-bolt"></i>
                                         </div>
                                     </div>
                                 <div>The deadline for this submission period has passed</div>
                             </div>
                        </div>
                         <div class="notificationDate"> 07-11-2018 16:42 </div>
                 </div>

                 </div>


            </div>
            </div>



 <!-- The new paper Modal -->
 <div id="createModal" class="modal">

     <!-- Modal content -->
     <div class="modal-content">
         <div class="modal-header">
             <h3 class="modal-title" id="lineModalLabel">New Publication</h3>
             <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">  <i class="far fa-window-close"></i>  </span><span class="sr-only">Close</span></button>
         </div>
         <form id="paperForm" enctype="multipart/form-data" class="paperForm" action="/profile"
               method="POST">
             <div class="modal-body">


                 <div class="form-group bmd-form-group">
                     <div class="input-group">
                         <label for="paper">Title: </label><br>
                         <input type="text" name="title" placeholder="Title of the paper" required="required"><br><br>
                     </div>
                 </div>

                 <div class="form-group bmd-form-group">
                     <div class="input-group">
                         <label for="paper">Authors: </label><br>
                         <input type="text" name="authors" placeholder="Authors (separate by coma(,)" required="required"><br><br>
                     </div>
                 </div>

                 <div class="form-group bmd-form-group">
                     <div class="input-group">
                         <label for="paper">Submitter: </label><br>
                         <input type="text" name="contactAuthor" placeholder="Contact Author" readonly="readonly"    value=${user.getUserName()}><br><br>
                     </div>
                 </div>

                 <div class="form-group bmd-form-group">
                     <div class="input-group">
                         <label for="paper">Format: </label><br>
                         <input type="text" name="format" placeholder="Format of the paper" required="required"><br><br>
                     </div>
                 </div>

                 <input type="checkbox" name="old_version" value="true" > Does this papers already exist?<br><br>


                 <div class="form-group bmd-form-group">
                     <div class="input-group">
                         <label for="paper">Paper: </label><br>
                         <input type="file" required="required" id="paper" name="paper" accept=".pdf,.doc,.docx"/><br><br>
                     </div>
                 </div>



             </div>
             <div class="modal-footer justify-content-center">
                 <input type="submit" value="Save" class="float-left">
             </div>
         </form>
    </div>
 </div>


 <!-- The delete Modal -->
 <div id="deleteModal" class="modal">

     <!-- Modal content -->
     <div class="modal-content">
         <div class="modal-header-delete">
             <h3 class="modal-title" id="deleteTitle"> </h3>
             <button type="button" class="delClose float-right" data-dismiss="modal"><span aria-hidden="true">  <i class="far fa-window-close"></i>  </span><span class="sr-only">Close</span></button>
         </div>
         <form id="paperForm" enctype="multipart/form-data" class="paperForm" action="/submitPaper"
               method="POST">
             <div class="modal-body" id="deleteBody">

             </div>
             <div class="modal-footer justify-content-center">
                 <input type="submit" value="Yes" class="float-left">

             </div>
         </form>
     </div>
 </div>




<#if user.getType() == "PCC" >
     <!-- The assign Modal -->
     <div id="assignModal" class="modal">

         <!-- Modal content -->
         <div class="modal-content">
             <div class="modal-header-assign">

                 <h3 class="modal-title" id="lineModalLabel">Assign reviewer</h3>
                 <button type="button" id="assignClose"  class="float-right" data-dismiss="modal"><span aria-hidden="true">  <i class="far fa-window-close"></i>  </span><span class="sr-only">Close</span></button>
             </div>

             <form id="paperForm" class="paperForm" role="form" action="/assign" method="POST">
                 <div class="modal-body">

                     <div id="paper-asign" class="input-group">
                         <label for="paper">Paper for review: </label><br>
                     </div>

                     <div id="paperidasd" class="input-group" >
                         <label for="paper">Paper id: </label><br>
                     </div>


                     <div class="REQUEST_SELECT_LIST">
                         <label for="paper">PCMS requesting this paper: </label><br>
                         <select id="REQUEST_SELECT_LIST" style="overflow:auto;" name="reviewer">
                         </select>
                     </div>



                     <div class=" SELECT_LIST">
                         <label for="paper">Rest of the PCMs: </label><br>
                         <select id="SELECT_LIST" style="overflow:auto;" name="reviewer">
                         </select>
                     </div>


                 </div>
                 <div class="modal-footer justify-content-center">
                     <input type="submit" value="Assign" class="float-left">
                 </div>

             </form>


         </div>
     </div>
</#if>




<#if user.getType() == "PCM" >
 <!-- The request Modal -->
 <div id="requestModal" class="modal">

     <!-- Modal content -->
     <div class="modal-content">
         <div class="modal-header-request">

             <h3 class="modal-title" id="lineModalLabel">Request this paper for review?</h3>
             <button type="button" id="requestClose"  class="float-right" data-dismiss="modal"><span aria-hidden="true">  <i class="far fa-window-close"></i>  </span><span class="sr-only">Close</span></button>
         </div>

         <form id="paperForm" class="paperForm" role="form" action="/requestReview" method="POST">
             <div class="modal-body">

                 <div id="paper-request" class="input-group">
                     <label for="paper">Paper requested: </label><br>
                 </div>

                 <div id="paperRequestID" class="input-group" >
                     <label for="paper">Paper id: </label><br>
                 </div>


             </div>
             <div class="modal-footer justify-content-center">
                 <input type="submit" value="Request" class="float-left">
             </div>

         </form>


     </div>
 </div>
</#if>




<#include "partials/_includedScripts.ftl">
     <script src="/javascript/profile.js"></script>




</body>
</html>