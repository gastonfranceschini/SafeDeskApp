package com.ort.SafeDesk.Model;

import java.util.Date;

public class Diagnostico {

    private boolean Temperatura;
    private boolean PerdioGusto;
    private boolean ContactoCercano;
    private boolean EstoyEmbarazada;
    private boolean Cancer;
    private boolean Diabetes;
    private boolean Hepatica;
    private boolean PerdioOlfato;
    private boolean DolorGarganta;
    private boolean DificultadRespiratoria;

    public Diagnostico(){}

    public Diagnostico(boolean temp, boolean perdioGusto, boolean contacto, boolean estoyEmbarazada,
                       boolean cancer, boolean diabetes, boolean hepatica, boolean perdioOlfato,
                       boolean dolorGarganta, boolean dificultadRespiratoria){
        this.Temperatura = temp;
        this.PerdioGusto = perdioGusto;
        this.ContactoCercano = contacto;
        this.EstoyEmbarazada = estoyEmbarazada;
        this.Cancer = cancer;
        this.Diabetes = diabetes;
        this.Hepatica = hepatica;
        this.PerdioOlfato = perdioOlfato;
        this.DolorGarganta = dolorGarganta;
        this.DificultadRespiratoria = dificultadRespiratoria;
    }

    public boolean getTemperatura(){ return Temperatura; }
    public boolean isPerdioGusto() { return PerdioGusto; }
    public boolean getContactoCercano() { return ContactoCercano; }
    public boolean isEstoyEmbarazada() { return EstoyEmbarazada; }
    public boolean isCancer() { return Cancer; }
    public boolean isDiabetes() { return Diabetes; }
    public boolean isHepatica() { return Hepatica; }
    public boolean isPerdioOlfato() { return PerdioOlfato; }
    public boolean isDolorGarganta() { return DolorGarganta; }
    public boolean isDificultadRespiratoria() { return DificultadRespiratoria; }
}
