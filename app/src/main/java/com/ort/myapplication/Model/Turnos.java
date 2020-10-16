package com.ort.myapplication.Model;

import java.util.Date;

public class Turnos {

    private int TurnoId;
    private String FechaTurno;
    private String Piso;
    private String Edificio;
    private String Horario;

    public Turnos(String fecha, String piso, String edificio, String horario){
        this.FechaTurno = fecha;
        this.Piso = piso;
        this.Edificio = edificio;
        this.Horario = horario;
    }

    public int getTurnoId() {
        return TurnoId;
    }

    public String getFechaTurno() {
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
