package com.ort.myapplication.Model;

public class TurnoBody {

    private String idUsuario;
    private String fechaTurno;
    private int idHorarioEntrada;
    private int IdPiso;
    private int IdEdificio;

    public TurnoBody(String idUsuario, String fechaTurno, int idHorarioEntrada, int idPiso, int idEdificio) {
        this.idUsuario = idUsuario;
        this.fechaTurno = fechaTurno;
        this.idHorarioEntrada = idHorarioEntrada;
        IdPiso = idPiso;
        IdEdificio = idEdificio;
    }


    public String getUsuario() {
        return idUsuario;
    }

    public String getFechaTurno() {
        return fechaTurno;
    }

    public int getPiso() {
        return IdPiso;
    }

    public int getEdificio() {
        return IdEdificio;
    }

    public int getHorario() {
        return idHorarioEntrada;
    }

}
