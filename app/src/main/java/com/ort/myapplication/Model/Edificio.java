package com.ort.myapplication.Model;

public class Edificio {

    private int Id;
    private String Nombre;
    private String Direccion;
    /*private String lat;
    private String longitud;*/

    public String getDireccion() {
        return Direccion;
    }

    /*public String getLat() {
        return lat;
    }

    public String getLongitud() {
        return longitud;
    }*/

    public int getId() {
        return Id;
    }

    public String getNombre() {
        return Nombre;
    }

}
