package com.ort.SafeDesk.Model;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("userId")
    private String userId;
    @SerializedName("token")
    private String token;
    @SerializedName("Nombre")
    private String Nombre;
    @SerializedName("Email")
    private String Email;
    @SerializedName("IdTipoDeUsuario")
    private int IdTipoDeUsuario;
    @SerializedName("IdGerencia")
    private int IdGerencia;


    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getNombre() {
        return Nombre;
    }
    public String getEmail() {
        return Email;
    }
    public int getIdTipoDeUsuario() {
        return IdTipoDeUsuario;
    }
    public int getIdGerencia() {
        return IdGerencia;
    }


}
