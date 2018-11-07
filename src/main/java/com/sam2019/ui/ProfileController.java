package com.sam2019.ui;

import com.sam2019.model.SQLiteConnection;
import com.sam2019.model.User;
import spark.*;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileController implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        //Create Session
        final Session session = request.session();
        User currentUser = session.attribute("user");
     //   String username = request.queryParams("contactAuthor");

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

            //if version checkbox is not selected, validate there is no other row with the same paper. I guess this would be better to do based on the exception the database insert throws about the constrain of primary key
            if (newVersion == false && SQLiteConnection.existingPaper(title, contactAuthor)) {
                vm.put("errorExistingFile", "The Paper you are trying to upload is already added. If this is a new version, please mark the checkbox");
                vm.put("title", "Profile Page");
                vm.put("userName", contactAuthor);
                return new ModelAndView(vm, "profile.ftl");
            } else if (newVersion == true && !SQLiteConnection.existingPaper(title, contactAuthor)) {
                vm.put("errorExistingFile", "There is no paper added with that title yet, do not select the new version checkbox");
                vm.put("title", "Profile Page");
                vm.put("userName", contactAuthor);
                return new ModelAndView(vm, "profile.ftl");
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


            Path out = Paths.get("temp/test.pdf");
            //delete the file temp/test.pdf
            try {
                Files.deleteIfExists(out.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //since was not able to get the path... copy the uploaded file into temp/test.pdf and send that path to the database
            try (final InputStream in = uploadedFile.getInputStream()) {
                Files.copy(in, out);
                System.out.println(out.toString());
                uploadedFile.getInputStream().close();
                uploadedFile.delete();
                SQLiteConnection.insertPaper(title, format, newVersion, authors, contactAuthor, out.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            vm.put("title", "Profile Page");
            vm.put("userName", contactAuthor);


        }
        else if (request.requestMethod() == WebServer.GET_METHOD){
            System.out.println("Got a GET request on the ProfileController");
            vm.put("title", "Profile Page");
            vm.put("userName", currentUser == null ? null : currentUser.getUserName());

            //response.redirect(WebServer.);
        }
        else
            return null;

        List<String> papers = SQLiteConnection.getPapers(contactAuthor);
        if (!papers.isEmpty()){
            vm.put("uploadedPapers", papers);
        }
        //response.redirect(WebServer.PROFILE_URL);
        return new ModelAndView(vm, "profile.ftl");
    }
}
