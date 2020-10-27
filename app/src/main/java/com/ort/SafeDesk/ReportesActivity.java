package com.ort.SafeDesk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ort.SafeDesk.Interface.GetEdificios;
import com.ort.SafeDesk.Interface.GetHora;
import com.ort.SafeDesk.Interface.GetPisos;
import com.ort.SafeDesk.Interface.PostTurno;
import com.ort.SafeDesk.Interface.Reportes;
import com.ort.SafeDesk.Interface.Spinnereable;
import com.ort.SafeDesk.Interface.Usuarios;
import com.ort.SafeDesk.Model.Edificio;
import com.ort.SafeDesk.Model.Gerencias;
import com.ort.SafeDesk.Model.Hora;
import com.ort.SafeDesk.Model.Piso;
import com.ort.SafeDesk.Model.Reporte;
import com.ort.SafeDesk.Model.TurnoBody;
import com.ort.SafeDesk.Model.UsuarioDep;
import com.ort.SafeDesk.utils.ApiUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportesActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner usuariosDP;
    private Spinner edificiosDP;
    private Spinner pisosDP;
    private Spinner horasDP;

    private Spinner gerenciasDP;
    private Spinner reportesDP;

    private ImageButton imageButton;
    private Button reporte;
    private EditText fecha;
    private TextView cupoE;
    private TextView cupoP;
    private TextView cupoH;
    private int dia, mes, ano;
    private String fechaSelected;

    private List<Piso> pisos;
    private List<Hora> horas;
    private List<Edificio> edificios;
    private List<UsuarioDep> usuarios;

    private List<Gerencias> gerencias;
    private List<Reporte> reportes;

    private Reporte reporteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        fecha = findViewById(R.id.editTextFecha2);
        imageButton = findViewById(R.id.imageButton2);
        reporte = findViewById(R.id.btn_reporte);
        usuariosDP = findViewById(R.id.spinner7);
        edificiosDP = findViewById(R.id.spinner8);
        pisosDP = findViewById(R.id.spinner9);
        horasDP = findViewById(R.id.spinner10);

        reportesDP = findViewById(R.id.spinner5);
        gerenciasDP = findViewById(R.id.spinner6);

        configReportesSpinner();

        imageButton.setOnClickListener(this);
        reporte.setOnClickListener(this);

        //seteo defaults
        List<String> selRep = new ArrayList<String>();
        selRep.add("Selecciona Reporte");
        llenarSpinnersString(edificiosDP, selRep);
        llenarSpinnersString(gerenciasDP, selRep);
        llenarSpinnersString(usuariosDP, selRep);

        List<String> selEdi = new ArrayList<String>();
        selEdi.add("Selecciona Edificio");
        llenarSpinnersString(pisosDP, selEdi);
        llenarSpinnersString(horasDP, selEdi);

        usuariosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        edificiosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (edificios != null) {
                    configPisosSpinner(edificios.get((int) edificiosDP.getSelectedItemId()).getId());
                    configHorasSpinner(edificios.get((int) edificiosDP.getSelectedItemId()).getId());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        pisosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        horasDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //setCuposView(cupoH);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        reportesDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (reportes != null) {
                    configBotonesActivos(reportes.get((int) reportesDP.getSelectedItemId()));
                    configGerenciasSpinner();
                    configUsuariosSpinner();
                    configEdificiosSpinner("2099-1-1");
                    fechaSelected = "NULL";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    int definirColor(int valor)
    {
        if (valor == 1)//activo
            return getResources().getColor(R.color.colorEnabled);
        else if (valor == 2) //obligatorio
            return getResources().getColor(R.color.colorMandatory);
        else //desactivado
            return getResources().getColor(R.color.colorDisabled);
    }

    private void EvaluarActivacionSpinner(Spinner spinner, int valor)
    {
        spinner.setBackgroundColor(definirColor(valor));
        spinner.setEnabled(valor > 0);
    }

    private void configBotonesActivos(Reporte reporteSel)
    {
        reporteSeleccionado = reporteSel;
        EvaluarActivacionSpinner(gerenciasDP,reporteSel.isSelGerencia());
        EvaluarActivacionSpinner(usuariosDP,reporteSel.isSelUsuario());
        EvaluarActivacionSpinner(edificiosDP,reporteSel.isSelEdificio());
        EvaluarActivacionSpinner(horasDP,reporteSel.isSelHorario());
        EvaluarActivacionSpinner(pisosDP,reporteSel.isSelPiso());

        imageButton.setBackgroundColor(definirColor(reporteSel.isSelFecha()));
        imageButton.setEnabled(reporteSel.isSelFecha() > 0);

    }

    @Override
    public void onClick(View view) {
        if(imageButton.equals(view)){
            Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    fecha.setText(day + "/" + (month+1) + "/" + year);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    fechaSelected = year + "-" + (month +1) + "-" + day;
                }
            }
                    ,ano, mes, dia);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.show();
        }
        else if(reporte.equals(view)) {

            if (edificios != null && usuarios != null && pisos != null && horas != null) {
                saveTurno(new TurnoBody(usuarios.get((int) usuariosDP.getSelectedItemId()).getDni(),
                        fechaSelected, horas.get((int) horasDP.getSelectedItemId()).getId(),
                        pisos.get((int) pisosDP.getSelectedItemId()).getId(),
                        edificios.get((int) edificiosDP.getSelectedItemId()).getId()));
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Faltan seleccionar parametros!",Toast. LENGTH_SHORT).show();
            }

        }
        else{

        }
    }

    private void accessMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void saveTurno(TurnoBody turnoNuevo){
        PostTurno postTurno = (PostTurno) ApiUtils.getAPI(PostTurno.class);

        Call<TurnoBody> call = postTurno.saveTurno(turnoNuevo);
        call.enqueue(new Callback<TurnoBody>()  {
            @Override
            public void onResponse(Call<TurnoBody> call, Response<TurnoBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Turno registrado correctamente!",Toast. LENGTH_SHORT).show();
                    accessMainApp();
                }
                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TurnoBody> call, Throwable t) {
                Toast. makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configPisosSpinner(long idEdificio){
        GetPisos getPisos = (GetPisos)ApiUtils.getAPI(GetPisos.class);

        Call<List<Piso>> call = getPisos.getPisos("2099-1-1", idEdificio);

        call.enqueue(new Callback<List<Piso>>() {
            @Override
            public void onResponse(Call<List<Piso>> call, Response<List<Piso>> response) {
                pisos = response.body();
                List<String> pisosList = new ArrayList<String>();
                for (Piso p : pisos) {
                    pisosList.add(p.getNombre());
                }
                llenarSpinnersString(pisosDP, pisosList,reporteSeleccionado.isSelPiso());
            }

            @Override
            public void onFailure(Call<List<Piso>> call, Throwable t) {

            }
        });
    }

    private void configHorasSpinner(long idEdificio){
        GetHora getHora = (GetHora)ApiUtils.getAPI(GetHora.class);

        Call<List<Hora>> call = getHora.getHoras(idEdificio, "2099-1-1");

        call.enqueue(new Callback<List<Hora>>() {
            @Override
            public void onResponse(Call<List<Hora>> call, Response<List<Hora>> response) {
                horas = response.body();
                List<String> horasList = new ArrayList<String>();
                for (Hora h : horas) {
                    horasList.add(h.getHora());
                }
                llenarSpinnersString(horasDP, horasList,reporteSeleccionado.isSelHorario());
            }

            @Override
            public void onFailure(Call<List<Hora>> call, Throwable t) {

            }
        });
    }

    private void configEdificiosSpinner(String fechaParam){

        GetEdificios getEdificios = (GetEdificios)ApiUtils.getAPI(GetEdificios.class);
        Call<List<Edificio>> call = getEdificios.getEdificios(fechaParam);

        call.enqueue(new Callback<List<Edificio>>() {
            @Override
            public void onResponse(Call<List<Edificio>> call, Response<List<Edificio>> response) {
                edificios = response.body();
                List<String> edificiosList = new ArrayList<String>();
                for (Edificio e : edificios) {
                    edificiosList.add(e.getNombre() + " - " + e.getDireccion());
                }
                llenarSpinnersString(edificiosDP, edificiosList,reporteSeleccionado.isSelEdificio());
            }

            @Override
            public void onFailure(Call<List<Edificio>> call, Throwable t) {

            }
        });
    }


    private void configGerenciasSpinner(){
        Usuarios getGerencias = (Usuarios)ApiUtils.getAPI(Usuarios.class);

        Call<List<Gerencias>> call = getGerencias.getGerencias();

        call.enqueue(new Callback<List<Gerencias>>() {
            @Override
            public void onResponse(Call<List<Gerencias>> call, Response<List<Gerencias>> response) {
                gerencias = response.body();
                List<String> gerenciasList = new ArrayList<String>();

                for(Gerencias u : gerencias){
                    gerenciasList.add(u.getNombre());
                }

                llenarSpinnersString(gerenciasDP, gerenciasList,reporteSeleccionado.isSelGerencia());
            }

            @Override
            public void onFailure(Call<List<Gerencias>> call, Throwable t) {

            }
        });

    }


    private void configReportesSpinner(){
        Reportes getReportes = (Reportes) ApiUtils.getAPI(Reportes.class);

        Call<List<Reporte>> call = getReportes.getReportes();

        call.enqueue(new Callback<List<Reporte>>() {
            @Override
            public void onResponse(Call<List<Reporte>> call, Response<List<Reporte>> response) {
                reportes = response.body();
                List<String> reportesList = new ArrayList<String>();
                for(Reporte u : reportes){
                    reportesList.add(u.getNombre());
                }
                llenarSpinnersString(reportesDP, reportesList);
            }

            @Override
            public void onFailure(Call<List<Reporte>> call, Throwable t) {

            }
        });

    }

    private void configUsuariosSpinner(){
        Usuarios getUsuarios = (Usuarios)ApiUtils.getAPI(Usuarios.class);

        Call<List<UsuarioDep>> call = getUsuarios.getUsuariosDependientes();

        call.enqueue(new Callback<List<UsuarioDep>>() {
            @Override
            public void onResponse(Call<List<UsuarioDep>> call, Response<List<UsuarioDep>> response) {
                usuarios = response.body();
                List<String> usuariosList = new ArrayList<String>();
                for(UsuarioDep u : usuarios){
                    usuariosList.add(u.getNombre());
                }
                llenarSpinnersString(usuariosDP, usuariosList,reporteSeleccionado.isSelUsuario());
            }

            @Override
            public void onFailure(Call<List<UsuarioDep>> call, Throwable t) {

            }
        });

    }
    private void llenarSpinnersString(Spinner spinner, List<String> list)
    {
        llenarSpinnersString(spinner,list,0);
    }

    private void llenarSpinnersString(Spinner spinner, List<String> list,int llevaTodos){
        if (llevaTodos == 1)
            list.add("-TODOS-");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (llevaTodos == 1)
            spinner.setSelection(list.size());

    }

}