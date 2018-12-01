// Get the modal
var modal = document.getElementById('createModal');
var deletemodal = document.getElementById('deleteModal');
var assignModal = document.getElementById('assignModal');


var requestModal = document.getElementById('requestModal');







// Get the button that opens the modal
var btn = document.getElementById("tooltipAddNew");
var deleteBtns = document.getElementsByClassName("deletePaper")
var assignBtns = document.getElementsByClassName("asign");
var requestBtns = document.getElementsByClassName("request");



var cancelbtn = document.getElementById("cancel-modal");


// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];
var delspan = document.getElementsByClassName("delClose")[0];

var assignCloseBtn = document.getElementById("assignClose");
var requestCloseBtn = document.getElementById("requestClose");




// ** OPEN MODALS **

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks the delete button, open the delete modal
for (i = 0; i < deleteBtns.length; i++) {

    deleteBtns[i].onclick = function () {
        var title = $(this).parent().parent().parent().parent().children( ".align-middle.tbl-title" ).children().text();
        var submitter = $(this).parent().parent().parent().parent().children( ".align-middle.tbl-submitter" ).children().text();
        var finalTitle = "Delete " + title + " ";
        var finalBody = "Are you sure you want to delete the paper " + title + " uploaded by " + submitter;
        $('#deleteTitle').text(finalTitle);
        $('#deleteBody').text(finalBody);


        deletemodal.style.display = "block";
    }

}


// When the user clicks the assign button, open the asign modal

for (i = 0; i < assignBtns.length; i++) {
    assignBtns[i].onclick = function () {

        var title = $(this).parent().parent().parent().parent().children( ".align-middle.tbl-title" ).children().text();
        var id = $(this).parent().parent().parent().parent().children( ".align-middle.tbl-id" ).children().text();


        getReviewers( title, id);

        assignModal.style.display = "block";

    }
}




for (i = 0; i < requestBtns.length; i++) {
    requestBtns[i].onclick = function () {


        var requestTitle = $(this).parent().parent().parent().parent().children( ".align-middle.tbl-title" ).children().text();
        var requestID = $(this).parent().parent().parent().parent().children( ".align-middle.tbl-id" ).children().text();

        $('#paper-request').append('<input type="text" name="paper" placeholder="paper" readonly="readonly" value= "' + requestTitle +'">');

        $('#paperRequestID').append('<input type="text" name="id" placeholder="id" readonly="readonly"  value= "' + requestID +'">');

        requestModal.style.display = "block";

    }
}









// ** CLOSE MODALS  ON BUTTON CLICKS**



// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

delspan.onclick = function() {
    deletemodal.style.display = "none";
}


if(assignCloseBtn != null){
    assignCloseBtn.onclick =  function() {
        assignModal.style.display = "none";
        $('#paper-asign').children("input").remove();
        $('#paperidasd').children("input").remove();
        $('#SELECT_LIST').children().remove();
        $('#REQUEST_SELECT_LIST').children().remove();

    }
}


if(requestCloseBtn!= null){
    requestCloseBtn.onclick =  function() {
        requestModal.style.display = "none";
        $('#paper-request').children("input").remove();
        $('#paperRequestID').children("input").remove();
    }
}






//** ON ANYWHERE CLICK CLOSE MODALS

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
    else if (event.target == deletemodal){
        deletemodal.style.display ="none";
    }
    else if(event.target == assignModal){
        assignModal.style.display ="none";
        $('#paper-asign').children("input").remove();
        $('#paperidasd').children("input").remove();
        $('#SELECT_LIST').children().remove();
        $('#REQUEST_SELECT_LIST').children().remove();

    }
    else if(event.target == requestModal){
        requestModal.style.display = "none";
        $('#paper-request').children("input").remove();
        $('#paperRequestID').children("input").remove();
    }
}




function getReviewers(title, id) {


    $.ajax({
        url:     "/reviewers/",
        type:    "POST",
        data:   JSON.stringify( {paperID: id}),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            /* ...use the data to fill in some HTML elements... */
            //var obj = JSON.parse(data );
            console.log(data);

            var requestNames = data.pcmsrequested;
            var names = data.pcms;

            $('#SELECT_LIST').attr('size', names.length);


            if(requestNames.length < 1){
                $('#REQUEST_SELECT_LIST').append('<option value="null"> No PCMS have selected this paper </option>');
            }else{
                for (var i = 0; i < requestNames.length; i++) {
                    $('#REQUEST_SELECT_LIST').append('<option value="' + requestNames[i] + '">' + requestNames[i] + '</option>');
                }
            }





            if(names.length < 1){
                $('#SELECT_LIST').append('<option value="null"> No other PCMS available </option>');
            }
            else{
                for (var i = 0; i < names.length; i++) {
                    $('#SELECT_LIST').append('<option value="' + names[i] + '">' + names[i] + '</option>');
                }
            }


            $('#paper-asign').append('<input type="text" name="paper" placeholder="paper" readonly="readonly" value= "' + title +'">');

            $('#paperidasd').append('<input type="text" name="id" placeholder="id" readonly="readonly"  value= "' + id +'">');
        },
        error:   function(data ) {
            /* ...show an error... */
            console.log("fail" + data);
        }
    });

}

