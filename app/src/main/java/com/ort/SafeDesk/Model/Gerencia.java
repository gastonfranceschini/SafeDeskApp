package com.ort.SafeDesk.Model;

import com.ort.SafeDesk.Interface.ISpinnereable;

public class Gerencia implements ISpinnereable {

    private String Nombre;
    private int id;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return Nombre;
    }
}
