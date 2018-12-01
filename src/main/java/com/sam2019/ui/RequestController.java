package com.sam2019.ui;

import com.sam2019.model.SQLiteConnection;
import com.sam2019.model.User;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class RequestController implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {

        String requestedPaper = request.queryParams("id");

        //Create Session
        final Session session = request.session();
        User currentUser = session.attribute("user");

        SQLiteConnection.insertRequest(currentUser.getUserName(), requestedPaper);
        response.redirect(WebServer.PROFILE_URL);
        halt();
        return null;
    }
}
