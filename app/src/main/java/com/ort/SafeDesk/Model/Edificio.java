package com.ort.SafeDesk.Model;

import com.ort.SafeDesk.Interface.ISpinnereable;

public class Edificio implements ISpinnereable {

    private int eID;
    private String Nombre;
    private String Direccion;
    private int Cupo;
    /*private String lat;
    private String longitud;*/

    public String getDireccion() {
        return Direccion;
    }

    public int getCupo() { return Cupo; }

    /*public String getLat() {
        return lat;
    }

    public String getLongitud() {
        return longitud;
    }*/

    public int getId() {
        return eID;
    }

    public String getNombre() {
        return Nombre;
    }

}
