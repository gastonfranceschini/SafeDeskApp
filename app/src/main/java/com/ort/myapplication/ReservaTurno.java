package com.ort.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.ort.myapplication.Interface.UsuariosDependiente;
import com.ort.myapplication.Model.UsuarioDep;
import com.ort.myapplication.utils.ApiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaTurno extends AppCompatActivity implements View.OnClickListener {

    private Spinner usuariosDP;
    private Spinner edificiosDP;
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
        usuariosDP = findViewById(R.id.spinner1);
        edificiosDP = findViewById(R.id.spinner2);
        pisosDP = findViewById(R.id.spinner3);
        horasDP = findViewById(R.id.spinner4);
        imageButton.setOnClickListener(this);

        //se llenan los dropdowns con datos
        configAllSpinners();

        usuariosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String usuarioSelected;
                usuarioSelected = usuariosDP.getSelectedItem().toString();
                System.out.println(usuarioSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edificiosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String gerenciaSelected;
                gerenciaSelected = edificiosDP.getSelectedItem().toString();
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
        configUsuariosSpinner();
        configEdificiosSpinner();
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

    private void configEdificiosSpinner(){
            List<String> edificios = new ArrayList<String>();


            ArrayAdapter<String> gerenciasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, edificios);
            gerenciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edificiosDP.setAdapter(gerenciasAdapter);
        }

    private void configUsuariosSpinner(){
         List<String> usuariosList = new ArrayList<String>();
        UsuariosDependiente getUsuarios = (UsuariosDependiente)ApiUtils.getAPI(UsuariosDependiente.class);

        Call<List<UsuarioDep>> call = getUsuarios.getUsuarios();

        call.enqueue(new Callback<List<UsuarioDep>>() {
            @Override
            public void onResponse(Call<List<UsuarioDep>> call, Response<List<UsuarioDep>> response) {
                List<UsuarioDep> usuarios = response.body();
                List<String> usuariosList = new ArrayList<String>();
                for(UsuarioDep u : usuarios){
                    usuariosList.add(u.getNombre());
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioDep>> call, Throwable t) {

            }
        });
        ArrayAdapter<String> usuariosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usuariosList);
        usuariosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usuariosDP.setAdapter(usuariosAdapter);
        }

}