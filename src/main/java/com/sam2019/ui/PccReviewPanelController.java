package com.sam2019.ui;

import com.sam2019.model.Paper;
import com.sam2019.model.SQLiteConnection;
import com.sam2019.model.User;
import spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PccReviewPanelController implements TemplateViewRoute {


    @Override
    public ModelAndView handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        //Create Session
        final Session session = request.session();

        User currentUser = session.attribute("user");


        List<Paper> completedPapers = SQLiteConnection.getCompletedPapers();
        // List<Paper> papers = null;

        //select all papers where current username is one of the submitters
        vm.put("user", currentUser);
        vm.put("title", "Register Page");
        vm.put("completedPapers", completedPapers);
        return new ModelAndView(vm, "pccReview.ftl");
    }
}
