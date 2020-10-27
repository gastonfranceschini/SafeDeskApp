package com.ort.SafeDesk.Model;

import com.ort.SafeDesk.Interface.Spinnereable;

public class Piso implements Spinnereable {

    private String Nombre;
    private int Numero;
    private int Cupo;
    private int pID;

    public int getCupo() { return Cupo; }

    public int getNumero() {
        return Numero;
    }

    public int getId() {
        return pID;
    }

    public String getNombre() {
        return Nombre;
    }
}
