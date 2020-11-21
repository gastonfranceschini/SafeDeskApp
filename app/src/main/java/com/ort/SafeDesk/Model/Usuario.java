package com.ort.SafeDesk.Model;

import com.ort.SafeDesk.Interface.ISpinnereable;

public class Usuario implements ISpinnereable {

    private String dni;
    private String nombre;

    public String getDni() { return dni; }
    public int getId() { return Integer.parseInt(dni); }
    public String getNombre() { return nombre; }

}
