package com.ort.myapplication.Model;

import java.util.Date;

public class Turnos {

    private int TurnoId;
    private Date FechaTurno;
    private String Piso;
    private String Edificio;
    private String Horario;

    public int getTurnoId() {
        return TurnoId;
    }

    public Date getFechaTurno() {
        return FechaTurno;
    }

    public String getPiso() {
        return Piso;
    }

    public String getEdificio() {
        return Edificio;
    }

    public String getHorario() {
        return Horario;
    }

}
