package com.sam2019.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class RegisterController implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        //Check the kind of request on the register route
        if (request.requestMethod() == WebServer.GET_METHOD) {
            return new ModelAndView(vm, "register.ftl");
        }
        else if(request.requestMethod() == WebServer.POST_METHOD){
            return new ModelAndView(vm, "register.ftl");
        }
        else{
            return new ModelAndView(vm, "register.ftl");
        }
    }
}
