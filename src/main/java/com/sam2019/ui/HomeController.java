package com.sam2019.ui;

import com.sam2019.model.SQLiteConnection;
import com.sam2019.model.User;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.halt;

public class HomeController implements TemplateViewRoute {

    static final private String MESSAGE_ATTRIBUTE = "message";
    static final private String MESSAGE_FAIL_VALUE = "Wrong credential combination!";
    static final public String USER_ATTRIB = "user";

    ArrayList<User> users = new ArrayList<>();

    //constructor
    public HomeController ( ArrayList<User> usersDB){
        this.users = usersDB;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        //Create Session
        final Session session = request.session();

        vm.put("title", "Home Page");


        //Check the kind of request on the home route

        if (request.requestMethod() == WebServer.GET_METHOD) {

            return new ModelAndView(vm, "home.ftl");

        }

        else if(request.requestMethod() == WebServer.POST_METHOD){

            String givenUsername = request.queryParams("username");
            String givenPassword = request.queryParams("password");

            User currentUser = SQLiteConnection.validateLogin(givenUsername, givenPassword);

            if(currentUser!=null){

                session.attribute("user", currentUser);
                response.redirect(WebServer.PROFILE_URL);
                halt();
                return null;

            }
            else{
                System.out.println("credentials not valid");
                vm.put(MESSAGE_ATTRIBUTE, MESSAGE_FAIL_VALUE);
                return new ModelAndView(vm, "home.ftl");
            }

        }
        else{
            //Not a GET request neither a POST???
            return null;
        }

    }

    

}
