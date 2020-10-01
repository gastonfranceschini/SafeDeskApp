package com.ort.myapplication.Model;

public class Diagnostico {

    private float temperatura;
    private boolean perdioGusto;
    private String contactoCercano;
    private boolean estoyEmbarazada;
    private boolean cancer;
    private boolean diabetes;
    private boolean hepatica;
    private boolean perdioOlfato;
    private boolean dolorGarganta;
    private boolean dificultadRespiratoria;


    public float getTemperatura(){ return temperatura; }
    public boolean isPerdioGusto() { return perdioGusto; }
    public String getContactoCercano() { return contactoCercano; }
    public boolean isEstoyEmbarazada() { return estoyEmbarazada; }
    public boolean isCancer() { return cancer; }
    public boolean isDiabetes() { return diabetes; }
    public boolean isHepatica() { return hepatica; }
    public boolean isPerdioOlfato() { return perdioOlfato; }
    public boolean isDolorGarganta() { return dolorGarganta; }
    public boolean isDificultadRespiratoria() { return dificultadRespiratoria; }
}
