package com.sam2019.ui;

import com.sam2019.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class RegisterController implements TemplateViewRoute {

    static final public String MESSAGE_ATTRIBUTE = "message";
    static final public String MESSAGE_FAIL_VALUE = "Could not register!Try different combination";
    static final public String MESSAGE_SUCCESS_VALUE = "Account successfully created";


    ArrayList<User> users = new ArrayList<>();

    //constructor
    public RegisterController ( ArrayList<User> usersDB){
        this.users = usersDB;
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
            String givenUsername = request.queryParams("username");
            String givenEmail = request.queryParams("email");
            String givenPassword = request.queryParams("password");
            String givenConfirmedPassword = request.queryParams("confirm_password");

            System.out.println("I just received a POST request to route: \"/post\" with the parameters:");
            System.out.println("username: " + givenUsername + " email: " + givenEmail + " password: " + givenPassword + " confirmed pass: " + givenConfirmedPassword);

            if(validateRegisterCredentials(givenUsername,givenEmail,givenPassword, givenConfirmedPassword, this.users)){

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
    private boolean validateRegisterCredentials(String username, String email, String password, String confirmed_pass, ArrayList<User> users){

        boolean isValid = false;

        isValid = (username==null|| email==null||password==null||confirmed_pass==null||!password.equals(confirmed_pass))?false:true;

        //check if the combination is not already on the list

        return isValid;
    }



}
