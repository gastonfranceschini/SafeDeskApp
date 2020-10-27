package com.ort.SafeDesk.Model;

import com.ort.SafeDesk.Interface.Spinnereable;

public class UsuarioDep implements Spinnereable {

    private String dni;
    private String nombre;

    public String getDni() { return dni; }
    public int getId() { return Integer.parseInt(dni); }
    public String getNombre() { return nombre; }

}
