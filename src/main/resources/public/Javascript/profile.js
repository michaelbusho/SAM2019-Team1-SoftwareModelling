// Get the modal
var modal = document.getElementById('createModal');
var deletemodal = document.getElementById('deleteModal');
var assignModal = document.getElementById('assignModal');


// Get the button that opens the modal
var btn = document.getElementById("tooltipAddNew");
var deleteBtns = document.getElementsByClassName("deletePaper")
var assignBtns = document.getElementsByClassName("asign");




var cancelbtn = document.getElementById("cancel-modal");


// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];
var delspan = document.getElementsByClassName("delClose")[0];

var assignCloseBtn = document.getElementById("assignClose");
var cancelAssBtn = document.getElementById("cancel-assign-modal");

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


var theclass = "";
// When the user clicks the assign button, open the asign modal

for (i = 0; i < assignBtns.length; i++) {
    assignBtns[i].onclick = function () {


        theclass = $(this).attr("class");

        if (theclass.indexOf('reviewer1') > -1) {
            console.log("reviewer1");
        }else if(theclass.indexOf('reviewer2') > -1){
            console.log("reviewer2");
        }else if(theclass.indexOf('reviewer3') > -1){
            console.log("reviewer3");
        }else{

        }

        var title = $(this).parent().parent().parent().parent().children( ".align-middle.tbl-title" ).children().text();

        getReviewers( title);

        assignModal.style.display = "block";

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

assignCloseBtn.onclick =  function() {
    assignModal.style.display = "none";
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
    }
}




function getReviewers(title) {


    $.ajax({
        url:     "/reviewers/",
        type:    "POST",
        data:   JSON.stringify( {paperTitle: title}),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            /* ...use the data to fill in some HTML elements... */
            //var obj = JSON.parse(data );
            var names = data.pcms;

            $('#SELECT_LIST').attr('size', names.length);

            for (var i = 0; i < names.length; i++) {
                $('#SELECT_LIST').append('<option value="' + names[i] + '">' + names[i] + '</option>');
            }

            $('#paper-asign').append('<input type="text" name="paper" placeholder="paper" readonly="readonly" value= "' + title +'">');

        },
        error:   function(data ) {
            /* ...show an error... */
            console.log("fail" + data);
        }
    });

}

