package com.sam2019.model;

public class User {
    String username;
    String email;
    String password;
    String type;

    public User(String userName, String email, String password, String type){
        this.username = userName;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getUserName(){
        return username;
    }

    public String getPassword(){return password;}

    public String getEmail(){
        return email;
    }

    public String getType() {
        return type;
    }
}
