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
    private Spinner gerenciasDP;
    private Spinner pisosDP;
    private Spinner horasDP;
    private ImageButton imageButton;
    private EditText fecha;
    private int dia, mes, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_turno);

        fecha = findViewById(R.id.editTextFecha);
        imageButton = findViewById(R.id.imageButton);
        edificiosDP = findViewById(R.id.spinner1);
        gerenciasDP = findViewById(R.id.spinner2);
        pisosDP = findViewById(R.id.spinner3);
        horasDP = findViewById(R.id.spinner4);
        imageButton.setOnClickListener(this);

        //se llenan los dropdowns con datos
        configAllSpinners();

        edificiosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String edificioSelected;
                edificioSelected = edificiosDP.getSelectedItem().toString();
                System.out.println(edificioSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gerenciasDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String gerenciaSelected;
                gerenciaSelected = gerenciasDP.getSelectedItem().toString();
                System.out.println(gerenciaSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pisosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pisoSelected;
                pisoSelected = pisosDP.getSelectedItem().toString();
                System.out.println(pisoSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        horasDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String horaSelected;
                horaSelected = horasDP.getSelectedItem().toString();
                System.out.println(horaSelected);
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
                    ,ano, mes, dia);
            datePicker.show();
    }

    private void configAllSpinners(){
        configEdificiosSpinner();
        configGerenciasSpinner();
        configPisosSpinner();
        configHorasSpinner();
    }

    private void configPisosSpinner(){
            List<String> pisos = new ArrayList<String>();
            pisos.add("*~ Elegí un piso ~*");
            pisos.add("PB");
            pisos.add("Piso 4");
            pisos.add("Piso 5");

            ArrayAdapter<String> pisosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pisos);
            pisosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pisosDP.setAdapter(pisosAdapter);
        }

    private void configHorasSpinner(){
            List<String> horas = new ArrayList<String>();
            horas.add("*~ Elegí una hora ~*");
            horas.add("09:00");
            horas.add("09:30");
            horas.add("10:00");

            ArrayAdapter<String> horasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, horas);
            horasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            horasDP.setAdapter(horasAdapter);
        }

    private void configGerenciasSpinner(){
            List<String> gerencias = new ArrayList<String>();
            gerencias.add("*~ Elegí una gerencia ~*");
            gerencias.add("RRHH");
            gerencias.add("Sistemas");
            gerencias.add("Seguridad");

            ArrayAdapter<String> gerenciasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gerencias);
            gerenciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gerenciasDP.setAdapter(gerenciasAdapter);
        }

    private void configEdificiosSpinner(){
            List<String> edificios = new ArrayList<String>();
            edificios.add("*~ Elegí un edificio ~*");
            edificios.add("Av. Crisólogo Larralde 2055");
            edificios.add("Av. Libertador e Ibera");
            edificios.add("Centro");

            ArrayAdapter<String> edificiosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, edificios);
            edificiosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edificiosDP.setAdapter(edificiosAdapter);
        }
}