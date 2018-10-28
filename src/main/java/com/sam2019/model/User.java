package com.sam2019.model;

public class User {
    String username;
    String email;
    String password;

    public User(String userName, String email, String password){
        this.username = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName(){
        return username;
    }

    public String getEmail(){
        return email;
    }


}
