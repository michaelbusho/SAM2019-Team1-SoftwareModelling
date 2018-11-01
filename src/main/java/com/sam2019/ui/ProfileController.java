package com.sam2019.ui;

import com.sam2019.model.User;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class ProfileController implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

            //Create Session
            final Session session = request.session();
            User currentUser = session.attribute("user");

            vm.put("title", "Profile Page");
            vm.put("userName", currentUser==null?null:currentUser.getUserName());

            return new ModelAndView(vm, "profile.ftl");

    }

}
