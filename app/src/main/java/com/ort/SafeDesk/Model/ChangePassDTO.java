package com.ort.SafeDesk.Model;

public class ChangePassDTO {

    private String dni;
    private String oldPassword;
    private String newPassword;

    public ChangePassDTO(String dni, String passOld,String passNew){
        this.dni = dni;
        this.oldPassword = passOld;
        this.newPassword = passNew;
    }

    public String getDNI() {
        return dni;
    }


}
