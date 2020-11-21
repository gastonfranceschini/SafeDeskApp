package com.ort.SafeDesk.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Turno {

    private int TurnoId;
    private String FechaTurno;
    private String Piso;
    private String Edificio;
    private String Horario;
    private String GeoPos;

    public Turno(String fecha, String piso, String edificio, String horario){
        this.FechaTurno = fecha;
        this.Piso = piso;
        this.Edificio = edificio;
        this.Horario = horario;
    }

    public int getTurnoId() {
        return TurnoId;
    }

    public String getGeoPos() {
        return GeoPos;
    }

    public String getFechaTurno() throws ParseException {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(inputFormat.parse(FechaTurno));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //c.add(Calendar.DATE, 1);  // number of days to add

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fFinal = df.format(c.getTime());


        return fFinal;
        //return FechaTurno;
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
