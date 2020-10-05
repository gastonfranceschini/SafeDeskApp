package com.ort.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservaTurno extends AppCompatActivity implements View.OnClickListener {

    private Spinner edificiosDP;
    private ImageButton imageButton;
    private EditText fecha;
    private int dia, mes, ano;
    private String edificioSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_turno);

        fecha = findViewById(R.id.editTextFecha);
        imageButton = findViewById(R.id.imageButton);
        edificiosDP = findViewById(R.id.spinner1);
        imageButton.setOnClickListener(this);

        List<String> edificios = new ArrayList<String>();
        edificios.add("Elegí un edificio");
        edificios.add("RRHH");
        edificios.add("Sistemas");
        edificios.add("Seguridad");

        ArrayAdapter<String> edificiosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, edificios);
        edificiosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edificiosDP.setAdapter(edificiosAdapter);

        edificiosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                edificioSelected = edificiosDP.getSelectedItem().toString();
                System.out.println(edificioSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
            Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    fecha.setText(day + "/" + (month+1) + "/" + year);
                }
            }
                    ,dia, mes, ano);
            datePicker.show();
        }
}