package com.ort.SafeDesk.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public String getFechaTurno() throws ParseException {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);
        Date date = inputFormat.parse(FechaTurno);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fFinal = df.format(date);

        return fFinal; //FechaTurno;
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
