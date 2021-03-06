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

    @SerializedName("Gerencia")
    private String Gerencia;

    @SerializedName("CambioPassObligatorio")
    private int CambioPassObligatorio;


    public String getUserId() {
        return userId;
    }

    public String getTokenKey() {
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
    public String getGerencia() {
        return Gerencia;
    }
    public int getCambioPassObligatorio() {
        return CambioPassObligatorio;
    }

    public void setCambioPassObligatorio(int cambioPassObligatorio) {
        CambioPassObligatorio = cambioPassObligatorio;
    }
}
