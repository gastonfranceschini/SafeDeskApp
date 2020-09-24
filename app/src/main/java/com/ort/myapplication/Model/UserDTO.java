package com.ort.myapplication.Model;

public class UserDTO {

    private String username;
    private String password;

    public UserDTO(String user, String pass){
        this.username = user;
        this.password = pass;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
