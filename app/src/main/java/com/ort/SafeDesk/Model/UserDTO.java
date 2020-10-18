package com.ort.SafeDesk.Model;

public class UserDTO {

    private String dni;
    private String password;

    public UserDTO(String dni, String pass){
        this.dni = dni;
        this.password = pass;
    }

    public String getDNI() {
        return dni;
    }

    public String getPassword() {
        return password;
    }

}
