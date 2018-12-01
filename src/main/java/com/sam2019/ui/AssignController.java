package com.sam2019.ui;

import com.sam2019.model.SQLiteConnection;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class AssignController implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        //Create Session
        final Session session = request.session();

        //final String reviewer = request.queryParams(Player.PLAYER_NAME_ATTRIBUTE);

        String reviewr1 = request.queryParams("reviewer");
        String paper = request.queryParams("paper");
        SQLiteConnection.assignSubmitter1(reviewr1,paper);


        response.redirect(WebServer.PROFILE_URL);
        halt();
        return null;
    }
}
