package com.ort.SafeDesk.Model;

import com.ort.SafeDesk.Interface.Spinnereable;

public class Reporte implements Spinnereable {

    private String Nombre;
    private int Id;
    private int SelGerencia;
    private int SelUsuario;
    private int SelFecha;
    private int SelEdificio;
    private int SelPiso;
    private int SelHorario;

    public int isSelGerencia() {
        return SelGerencia;
    }

    public int isSelUsuario() {
        return SelUsuario;
    }

    public int isSelFecha() {
        return SelFecha;
    }

    public int isSelEdificio() {
        return SelEdificio;
    }

    public int isSelPiso() {
        return SelPiso;
    }

    public int isSelHorario() {
        return SelHorario;
    }


    public int getId() {
        return Id;
    }


    public String getNombre() {
        return Nombre;
    }
}
