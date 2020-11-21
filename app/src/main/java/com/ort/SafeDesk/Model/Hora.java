package com.ort.SafeDesk.Model;

import com.ort.SafeDesk.Interface.ISpinnereable;

public class Hora implements ISpinnereable {
    private int id;
    private String horario;
    private int Cupo;

    public int getCupo() { return Cupo; }

    public String getHora() {
        return horario;
    }

    public String getNombre() {
        return horario;
    }
    public int getId() {
        return id;
    }
}
