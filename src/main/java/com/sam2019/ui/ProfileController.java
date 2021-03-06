package com.sam2019.ui;

import com.sam2019.model.Paper;
import com.sam2019.model.SQLiteConnection;
import com.sam2019.model.User;
import spark.*;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProfileController implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        //Create Session
        final Session session = request.session();


       // User mike = new User("mikebsh", "mike@gmail.com", "123", "Submitter");
      User currentUser = session.attribute("user");
       // User currentUser = mike;



        vm.put("title", "Profile Page");
        vm.put("user", currentUser);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime submissionDeadline = LocalDate.of(2018, Month.DECEMBER, 18).atTime(23, 59);
        String formattedDateTime = submissionDeadline.format(formatter);

        vm.put("PaperSubmissionDeadline", formattedDateTime);

        //Show available papers
        List<Paper> papers = SQLiteConnection.getPapers(currentUser.getUserName(), currentUser.getType());

        for(Paper currentPaper: papers){

            //get all reviewers for that paper
            List<String> submitters = SQLiteConnection.getReviewersNames(currentPaper.getId());
            // save those reviewers to the paper
            currentPaper.setSubmitters(submitters);

        }


        if (!papers.isEmpty()){
            vm.put("uploadedPapers", papers);
        }


        if (request.requestMethod() == WebServer.GET_METHOD) {
            

            return new ModelAndView(vm, "profile.ftl");
        }
        else if(request.requestMethod() == WebServer.POST_METHOD){




            //region Manage form
            if (request.raw().getAttribute("org.eclipse.multipartConfig") == null) {
                MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
                request.raw().setAttribute("org.eclipse.multipartConfig", multipartConfigElement);
            }

            // dont know why, but without this getParameter does not work..
            Collection<Part> parts = null;
            try {
                parts = request.raw().getParts();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }



            String title = request.raw().getParameter("title");
            String authors = request.raw().getParameter("authors");
            String contactAuthor = request.raw().getParameter("contactAuthor");
            String format = request.raw().getParameter("format");
            Boolean old_version = false;


            if (request.raw().getParameter("old_version") != null) {
                if (request.raw().getParameter("old_version").equals("on")) {
                    old_version = true;
                }
            }




            //get the paper
            Part uploadedFile = null;
            try {
                uploadedFile = request.raw().getPart("paper");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }

            try {

                if ( old_version) {
                    //if the title exists and belogns ot the user and newversion is checked, update it.
                    if (SQLiteConnection.getPapers(contactAuthor, currentUser.getType()).contains(title)) {
                        SQLiteConnection.insertPaper(title, format, authors, contactAuthor, uploadedFile.getInputStream());
                        vm.put("uploadMessage", title + " was updated successfully");

                    }
                    //if paper does not belong to user, and checkbox is checked, error.
                    else
                        vm.put("uploadMessage", "The paper you are trying to update does not exist or belongs to someone else. De-select new version checkbox if this is a new paper.");
                }
                //if no new version, and database does not return unique constrain, insert
                else if (SQLiteConnection.insertPaper(title, format, authors, contactAuthor, uploadedFile.getInputStream())) {
                    vm.put("uploadMessage", title + " was uploaded correctly");
                }
                //else error.
                else
                    vm.put("uploadMessage", "The title of your paper is not unique or does not belong to you. If you have previously submitted a paper with this title, please mark the new version checkbox to update it");


            } catch (IOException e) {
                e.printStackTrace();
            }

            //Delete the file
            try {
                uploadedFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ModelAndView(vm, "profile.ftl");
        }
        else{
            return null;
        }





























/*



        //region Manage form
        if (request.raw().getAttribute("org.eclipse.multipartConfig") == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            request.raw().setAttribute("org.eclipse.multipartConfig", multipartConfigElement);
        }

        // dont know why, but without this getParameter does not work..
        Collection<Part> parts = null;
        try {
            parts = request.raw().getParts();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        //endregion

        //region get data from parameters

        String title = request.raw().getParameter("title");
        String format = request.raw().getParameter("format");
        Boolean newVersion = false;
        if (request.raw().getParameter("version") != null)
            if (request.raw().getParameter("version").equals("on"))
                newVersion = true;
        String authors = request.raw().getParameter("authors");
        String contactAuthor = request.raw().getParameter("contactAuthor");

        //endregion





        if (request.requestMethod() == WebServer.POST_METHOD) {


            System.out.println("Got a POST request on the ProfileController");


            //get the paper
            Part uploadedFile = null;
            try {
                uploadedFile = request.raw().getPart("paper");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }

            try {
                //if new version checkbox is checked
                if (newVersion) {
                    //if the title exists and belogns ot the user and newversion is checked, update it.
                    if (SQLiteConnection.getPapers(contactAuthor).contains(title)) {
                        SQLiteConnection.updatePaper(title, format, authors, contactAuthor, uploadedFile.getInputStream());
                        vm.put("uploadMessage", title + " was updated successfully");

                    }
                    //if paper does not belong to user, and checkbox is checked, error.
                    else
                        vm.put("uploadMessage", "The paper you are trying to update does not exist or belongs to someone else. De-select new version checkbox if this is a new paper.");
                }
                //if no new version, and database does not return unique constrain, insert
                else if (SQLiteConnection.insertPaper(title, format, authors, contactAuthor, uploadedFile.getInputStream())) {
                    vm.put("uploadMessage", title + " was uploaded correctly");
                }
                //else error.
                else
                    vm.put("uploadMessage", "The title of your paper is not unique or does not belong to you. If you have previously submitted a paper with this title, please mark the new version checkbox to update it");


            } catch (IOException e) {
                e.printStackTrace();
            }

            //Delete the file
            try {
                uploadedFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }


            vm.put("title", "Profile Page");
          //  vm.put("userName", contactAuthor);


        } else if (request.requestMethod() == WebServer.GET_METHOD) {


            System.out.println("Got a GET request on the ProfileController");
            vm.put("title", "Profile Page");
            vm.put("userName", currentUser == null ? null : currentUser.getUserName());
            //response.redirect(WebServer.PROFILE_URL);
            return new ModelAndView(vm, "profile.ftl");




        } else {
            return null;
        }


        //always check for uploaded papers of the user.
        List<String> papers = SQLiteConnection.getPapers(contactAuthor);
        if (!papers.isEmpty()) {
            vm.put("uploadedPapers", papers);
        }

        return new ModelAndView(vm, "profile.ftl");

 */






    }
}
