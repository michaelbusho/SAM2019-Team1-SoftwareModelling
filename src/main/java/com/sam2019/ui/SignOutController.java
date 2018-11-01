package com.sam2019.ui;

import spark.*;

import static spark.Spark.halt;

public class SignOutController implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {

        //Create Session
        final Session session = request.session();

        session.invalidate();
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

}
