package com.sam2019.ui;

import com.sam2019.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeController implements TemplateViewRoute {

    static final private String MESSAGE_ATTRIBUTE = "message";
    static final private String MESSAGE_FAIL_VALUE = "Wrong credential combination!";

    ArrayList<User> users = new ArrayList<>();

    //constructor
    public HomeController ( ArrayList<User> usersDB){
        this.users = usersDB;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Home Page");


        //Check the kind of request on the home route

        if (request.requestMethod() == WebServer.GET_METHOD) {

            System.out.println("I just received a GET request to show route: \"/\" page");
            return new ModelAndView(vm, "home.ftl");

        }

        else if(request.requestMethod() == WebServer.POST_METHOD){
            String givenUsername = request.queryParams("username");
            String givenPassword = request.queryParams("password");

            System.out.println("I just received a POST request to route: \"/\" with the parameters: username - "+ givenUsername +" and password - " + givenPassword);


            if(validateLoginCredentials(givenUsername, givenPassword, this.users)){

                return new ModelAndView(vm, "home.ftl"); //change to redirect to user profile if valid
            }else{

                vm.put(MESSAGE_ATTRIBUTE, MESSAGE_FAIL_VALUE);
                return new ModelAndView(vm, "home.ftl");
            }

        }

        else{
            //Not a GET request neither a POST???
            return null;
        }

    }



    //Fix this function to validate credentials properly (Check if the combination is on the registered users list)
    private boolean validateLoginCredentials(String username, String password,  ArrayList<User> users){

        boolean isValid = false;

        isValid = (username==null||password==null)?false:true;

        //check if the combination is already on the list

        return isValid;
    }

}
