package com.ort.SafeDesk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;
import com.ort.SafeDesk.Interface.APIDiagnosticos;
import com.ort.SafeDesk.Interface.APITurnos;
import com.ort.SafeDesk.Interface.APIUsuarios;
import com.ort.SafeDesk.Model.Edificio;
import com.ort.SafeDesk.Model.Hora;
import com.ort.SafeDesk.Model.Piso;
import com.ort.SafeDesk.Model.TurnoDTO;
import com.ort.SafeDesk.Model.Usuario;
import com.ort.SafeDesk.Utils.ApiUtils;
import com.ort.SafeDesk.Utils.Commons;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaTurnosActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner usuariosDP;
    private Spinner edificiosDP;
    private Spinner pisosDP;
    private Spinner horasDP;
    private ImageButton imageButton;
    private Button reserva;
    private EditText fecha;
    private TextView cupoE;
    private TextView cupoP;
    private TextView cupoH;
    private int dia, mes, ano;
    private String fechaSelected;

    private List<Piso> pisos;
    private List<Hora> horas;
    private List<Edificio> edificios;
    private List<Usuario> usuarios;
    //LoginActivity ct = new LoginActivity();

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_turno);
        isAutoDiagActive();
        fecha = findViewById(R.id.editTextFecha1);
        cupoE = findViewById(R.id.textView5);
        cupoP = findViewById(R.id.textView6);
        cupoH = findViewById(R.id.textView7);
        imageButton = findViewById(R.id.imageButton1);
        reserva = findViewById(R.id.btn_reserva);
        usuariosDP = findViewById(R.id.spinner1);
        edificiosDP = findViewById(R.id.spinner2);
        pisosDP = findViewById(R.id.spinner3);
        horasDP = findViewById(R.id.spinner4);

        configUsuariosSpinner();
        imageButton.setOnClickListener(this);
        reserva.setOnClickListener(this);

        context = this.context;

       List<String> selFecha = new ArrayList<String>();
        selFecha.add("Selecciona Fecha");
        llenarSpinnersString(edificiosDP, selFecha);

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
                    setCuposView(cupoE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pisosDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setCuposView(cupoP);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        horasDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setCuposView(cupoH);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void isAutoDiagActive(){
        APIDiagnosticos userDiag = (APIDiagnosticos) ApiUtils.getAPI(APIDiagnosticos.class);

        Call<Boolean> call = userDiag.getUserDiagnostico();
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()){
                        //todo legal sigo sacando el turno
                    }else{
                        //obligo a realizar el autodiagnostico
                        accessMainApp(DiagnosticoActivity.class);
                        StyleableToast.makeText(getApplicationContext(),"Deber??s actualizar tu autodiagn??stico, antes de sacar un nuevo turno.", R.style.diagnostico).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setCuposView(TextView view){
        if(cupoE.equals(view)){
            view.setText("Cupo: " + (edificios.get((int) edificiosDP.getSelectedItemId()).getCupo()));
        }else if(cupoP.equals(view) && pisos != null){
            view.setText("Cupo: " + (pisos.get((int) pisosDP.getSelectedItemId()).getCupo()));
        }else if(cupoH.equals(view) && horas != null){
            view.setText("Cupo: " + (horas.get((int) horasDP.getSelectedItemId()).getCupo()));
        }
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
                    //String currentDateandTime = sdf.format(new Date());
                    //String fecha = sdf.format(new Date(year, month, day));
                    fechaSelected = year + "-" + (month +1) + "-" + day;
                    //Date fecha = new Date(year, month, day);
                    configEdificiosSpinner(fechaSelected);
                    isAutoDiagActive();
                }
            }
                    ,ano, mes, dia);

            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            // AR convierto el datePicker en espa??ol (lo tengo que hacer en el contexto del datePicker para que funcione)
            Commons.cambiarContextoEspanol(datePicker.getContext());
            datePicker.show();
        }
        else if(reserva.equals(view)) {

            if (edificios != null && usuarios != null && pisos != null && horas != null) {
                saveTurno(new TurnoDTO(usuarios.get((int) usuariosDP.getSelectedItemId()).getDni(),
                        fechaSelected, horas.get((int) horasDP.getSelectedItemId()).getId(),
                        pisos.get((int) pisosDP.getSelectedItemId()).getId(),
                        edificios.get((int) edificiosDP.getSelectedItemId()).getId()));
            }
            else
            {
                //Toast.makeText(getApplicationContext(),"Faltan parametros por seleccionar!",Toast. LENGTH_SHORT).show();
                mostrarToast(2, "Faltan parametros por seleccionar!");
            }

        }
        else{

        }
    }

    private void accessMainApp(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
    private void generarAgenda(final TurnoDTO turnoNuevo)
    {
        AlertDialog.Builder builder = null;
        builder = new AlertDialog.Builder(this);

        String msj = "??Deseas crear un nuevo evento en tu calendario?";
        builder.setMessage(msj);
        builder.setTitle("Nueva Reserva");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
            Calendar beginTime = Calendar.getInstance();
            Date fechaSel = null;
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

            try
            {
                fechaSel = inputFormat.parse(fechaSelected + " " + horasDP.getSelectedItem().toString());
            } catch (ParseException e)
            {
                e.printStackTrace();
            }

            beginTime.set(fechaSel.getYear() + 1900,fechaSel.getMonth(),fechaSel.getDate(),fechaSel.getHours(),fechaSel.getMinutes());
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
            //Calendar endTime = Calendar.getInstance();
            // endTime.set(2012, 0, 19, 10, 30);
            //calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
            calendarIntent.putExtra(CalendarContract.Events.TITLE, "Jornada Laboral - " + pisosDP.getSelectedItem().toString());
            //calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, edificiosDP.getSelectedItem().toString());
            calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, edificios.get((int) edificiosDP.getSelectedItemId()).getDireccion());
            startActivity(Intent.createChooser(calendarIntent, "Agendar Nueva Jornada:"));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                accessMainApp(MainActivity.class);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void saveTurno(final TurnoDTO turnoNuevo){
        APITurnos postTurno = (APITurnos) ApiUtils.getAPI(APITurnos.class);

        Call<TurnoDTO> call = postTurno.saveTurno(turnoNuevo);
        call.enqueue(new Callback<TurnoDTO>()  {
            @Override
            public void onResponse(Call<TurnoDTO> call, Response<TurnoDTO> response) {
                if(response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(),"Tu turno fue registrado correctamente!",Toast. LENGTH_LONG).show();
                    mostrarToast(1, "Tu turno fue registrado correctamente!");
                    generarAgenda(turnoNuevo);
                    //accessMainApp(MainActivity.class);
                    //onBackPressed();
                }
                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                        mostrarToast(3,jObjError.getString("error"));
                    } catch (Exception e) {
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        mostrarToast(2,e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TurnoDTO> call, Throwable t) {
                //Toast. makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                mostrarToast(3,t.getMessage());
            }
        });
    }

    private void configPisosSpinner(long idEdificio){
        APITurnos getPisos = (APITurnos)ApiUtils.getAPI(APITurnos.class);

        Call<List<Piso>> call = getPisos.getPisos(fechaSelected, idEdificio);

        call.enqueue(new Callback<List<Piso>>() {
            @Override
            public void onResponse(Call<List<Piso>> call, Response<List<Piso>> response) {
                pisos = response.body();
                List<String> pisosList = new ArrayList<String>();
                for (Piso p : pisos) {
                    pisosList.add(p.getNombre());
                }
                llenarSpinnersString(pisosDP, pisosList);
            }

            @Override
            public void onFailure(Call<List<Piso>> call, Throwable t) {

            }
        });
        }

    private void configHorasSpinner(long idEdificio){
        APITurnos getHora = (APITurnos)ApiUtils.getAPI(APITurnos.class);

        Call<List<Hora>> call = getHora.getHoras(idEdificio, fechaSelected);

        call.enqueue(new Callback<List<Hora>>() {
            @Override
            public void onResponse(Call<List<Hora>> call, Response<List<Hora>> response) {
                horas = response.body();
                List<String> horasList = new ArrayList<String>();
                for (Hora h : horas) {
                    horasList.add(h.getHora());
                }
                llenarSpinnersString(horasDP, horasList);
            }

            @Override
            public void onFailure(Call<List<Hora>> call, Throwable t) {

            }
        });
    }

    private void configEdificiosSpinner(String fechaParam){

        APITurnos getEdificios = (APITurnos)ApiUtils.getAPI(APITurnos.class);
            Call<List<Edificio>> call = getEdificios.getEdificios(fechaParam);

            call.enqueue(new Callback<List<Edificio>>() {
                @Override
                public void onResponse(Call<List<Edificio>> call, Response<List<Edificio>> response) {
                    edificios = response.body();
                    List<String> edificiosList = new ArrayList<String>();
                    for (Edificio e : edificios) {
                        edificiosList.add(e.getNombre() + " - " + e.getDireccion());
                    }
                    llenarSpinnersString(edificiosDP, edificiosList);
                }

                @Override
                public void onFailure(Call<List<Edificio>> call, Throwable t) {

                }
            });
        }

    private void configUsuariosSpinner(){
        APIUsuarios getUsuarios = (APIUsuarios)ApiUtils.getAPI(APIUsuarios.class);

        Call<List<Usuario>> call = getUsuarios.getUsuariosDependientes();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                usuarios = response.body();
                List<String> usuariosList = new ArrayList<String>();
                for(Usuario u : usuarios){
                    usuariosList.add(u.getNombre());
                }
                llenarSpinnersString(usuariosDP, usuariosList);
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });

        }

        private void llenarSpinnersString(Spinner spinner, List<String> list){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

    public void mostrarToast(int estado, String textoT){
        LayoutInflater inflater = getLayoutInflater();
        View layout;

        switch (estado){
            case 1:
                layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.container_toast));
                break;
            case 2:
                layout = inflater.inflate(R.layout.custom_toast2, (ViewGroup)findViewById(R.id.container_toast2));
                break;
            case 3:
                layout = inflater.inflate(R.layout.custom_toast3, (ViewGroup)findViewById(R.id.container_toast3));
                break;
            default:
                throw new IllegalStateException("Error de tipo: " + textoT);
        }
        TextView textView = layout.findViewById(R.id.toast_text);
        textView.setText(textoT);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}