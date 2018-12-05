package com.sam2019.ui;

import com.sam2019.model.SQLiteConnection;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;


public class RegisterController implements TemplateViewRoute {

    static final public String MESSAGE_ATTRIBUTE = "message";
    static final public String MESSAGE_FAIL_VALUE = "The User Name or Email is already registered";
    static final public String MESSAGE_SUCCESS_VALUE = "Account successfully created";



    //constructor
    public RegisterController (){
    }


    @Override
    public ModelAndView handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        vm.put("title", "Register Page");

        //Check the kind of request on the register route
        if (request.requestMethod() == WebServer.GET_METHOD) {
            System.out.println("I just received a GET request to show route: \"/register\" page");

            return new ModelAndView(vm, "register.ftl");
        }
        else if(request.requestMethod() == WebServer.POST_METHOD){
            String givenUsername = request.queryParams("username").trim();
            String givenEmail = request.queryParams("email").trim();
            String givenPassword = request.queryParams("password").trim();
            String givenConfirmedPassword = request.queryParams("confirm_password").trim();

            System.out.println("I just received a POST request to route: \"/post\" with the parameters:");
            System.out.println("username: " + givenUsername + " email: " + givenEmail + " password: " + givenPassword + " confirmed pass: " + givenConfirmedPassword);

            if(validateRegisterCredentials(givenUsername,givenEmail)){

                registerUserCredentials(givenUsername, givenEmail, givenPassword);
                vm.put(MESSAGE_ATTRIBUTE, MESSAGE_SUCCESS_VALUE);
                return new ModelAndView(vm, "home.ftl");

            }else{

                vm.put(MESSAGE_ATTRIBUTE, MESSAGE_FAIL_VALUE);
                return new ModelAndView(vm, "register.ftl");
            }


        }
        else{
            //Not a GET request neither a POST???
            return null;
        }
    }

    //Fix this function to validate credentials properly (Check if the combination is on the registered users list)
    private boolean validateRegisterCredentials(String username, String email){

        if (SQLiteConnection.validateRegistration().contains(username) || SQLiteConnection.validateRegistration().contains(email))
            return false;
        else
            return true;
    }

    private void registerUserCredentials (String username, String email, String password){

        SQLiteConnection.insertSubmitter(username, email, password);

    }


}
