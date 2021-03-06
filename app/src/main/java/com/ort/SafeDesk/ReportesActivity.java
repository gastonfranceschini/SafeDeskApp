package com.ort.SafeDesk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ort.SafeDesk.Interface.APIReportes;
import com.ort.SafeDesk.Interface.APITurnos;
import com.ort.SafeDesk.Interface.ISpinnereable;
import com.ort.SafeDesk.Interface.APIUsuarios;
import com.ort.SafeDesk.Model.Edificio;
import com.ort.SafeDesk.Model.Gerencia;
import com.ort.SafeDesk.Model.Hora;
import com.ort.SafeDesk.Model.Piso;
import com.ort.SafeDesk.Model.Reporte;
import com.ort.SafeDesk.Model.ReporteDTO;
import com.ort.SafeDesk.Model.Usuario;
import com.ort.SafeDesk.Utils.ApiUtils;
import com.ort.SafeDesk.Utils.Commons;
import com.ort.SafeDesk.Utils.Global;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.FileProvider.getUriForFile;

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
    private List<Usuario> usuarios;

    private List<Gerencia> gerencias;
    private List<Reporte> reportes;
    private Switch formatoAlternativo;
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
        formatoAlternativo = (Switch)findViewById(R.id.switch9);
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
                    Log.d("Test",edificiosDP.getSelectedItem().toString());
                    int EdificioId = 0;

                    if (edificiosDP.getSelectedItem().toString() != "-TODOS-")
                        EdificioId = edificios.get((int) edificiosDP.getSelectedItemId()).getId();

                    configPisosSpinner(EdificioId);
                    configHorasSpinner(EdificioId);

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
                    fecha.setText("Ingresar Fecha");
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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

    Drawable definirDraw(int valor)
    {
        if (valor == 1)//activo
            return ContextCompat.getDrawable(this,R.drawable.spinner_background_enabled);
        else if (valor == 2) //obligatorio
            return ContextCompat.getDrawable(this,R.drawable.spinner_background_mandatory);
        else //desactivado
            return ContextCompat.getDrawable(this,R.drawable.spinner_background_disabled);
    }

    private void EvaluarActivacionSpinner(Spinner spinner, int valor)
    {
        //spinner.setBackgroundColor(definirColor(valor));
        spinner.setBackground(definirDraw(valor));
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
            datePicker.getDatePicker();
            // AR convierto el datePicker en espa??ol (lo tengo que hacer en el contexto del datePicker para que funcione)
            Commons.cambiarContextoEspanol(datePicker.getContext());
            datePicker.show();
        }
        else if(reporte.equals(view)) {
            validarYConstruirReporte();
        }
        else{

        }
    }

    private void accessMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private boolean validarYAgregarSpinner(List<String> campos, List<String> valores, List<? extends ISpinnereable> datos, Spinner spinner, int valorRep, String valorBack)
    {
        if (valorRep == 1)//activo
        {
            campos.add(valorBack);
            if (datos == null || (spinner.getSelectedItem().toString() == "-TODOS-")) {
                valores.add("NULL");
            } else {
                valores.add("" + datos.get((int) spinner.getSelectedItemId()).getId());
            }
        }
        else if (valorRep == 2) //obligatorio
        {
            if (datos == null || (spinner.getSelectedItem().toString() == "-TODOS-")) {
                //Toast.makeText(getApplicationContext(), "El parametro " + valorBack + " es obligatorio!", Toast.LENGTH_SHORT).show();
                mostrarToast(2,"El parametro " + valorBack + " es obligatorio!");
                return false; //aca no vale NULL, devuelvo invalido
            }
            else {
                campos.add(valorBack);
                valores.add("" + datos.get((int) spinner.getSelectedItemId()).getId());
            }
        }

        return true;
    }
    private void validarYConstruirReporte() {
        List<String> campos = new ArrayList<String>();
        List<String> valores = new ArrayList<String>();

        if (!validarYAgregarSpinner(campos, valores, usuarios ,usuariosDP,reporteSeleccionado.isSelUsuario(),"usuario"))
            return;

        if (!validarYAgregarSpinner(campos, valores, edificios ,edificiosDP,reporteSeleccionado.isSelEdificio(),"edificio"))
            return;

        if (!validarYAgregarSpinner(campos, valores, pisos ,pisosDP,reporteSeleccionado.isSelPiso(),"piso"))
            return;

        if (!validarYAgregarSpinner(campos, valores, horas ,horasDP,reporteSeleccionado.isSelHorario(),"horario"))
            return;

        if (!validarYAgregarSpinner(campos, valores, gerencias ,gerenciasDP,reporteSeleccionado.isSelGerencia(),"gerencia"))
            return;

        if (reporteSeleccionado.isSelFecha() == 2 && fechaSelected == "NULL") //obligatorio
        {
                //Toast.makeText(getApplicationContext(), "El parametro fecha es obligatorio!", Toast.LENGTH_SHORT).show();
                mostrarToast(2, "El parametro fecha es obligatorio!");
                return; //aca no vale NULL, devuelvo invalido
        }

        campos.add("fecha");
        valores.add("" + fechaSelected);

        Log.e("xx",campos.toString());

        postReporte(campos,valores);

    }

    private void postReporte(List<String> campos, List<String> valores){
        APIReportes reportes = (APIReportes) ApiUtils.getAPI(APIReportes.class);

        Call<ResponseBody> call = reportes.getReporteDinamico(new ReporteDTO(campos,valores,formatoAlternativo.isChecked()) ,reporteSeleccionado.getId());
        call.enqueue(new Callback<ResponseBody>()  {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast. makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void envioEmail(Uri uri)
    {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"" + Global.token.getEmail()});
        emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Reporte " + reporteSeleccionado.getNombre());
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Nuevo Reporte Adjunto: " + reporteSeleccionado.getNombre() +  " \n Fecha: " + Calendar.getInstance().getTime());
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

        getPermisos(emailIntent,uri);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar Correo..."));
        } catch (android.content.ActivityNotFoundException ex) {
            //Toast.makeText(this, "No hay ningun cliente de correo instalado.", Toast.LENGTH_SHORT).show();
            mostrarToast(2, "No hay ningun cliente de correo instalado");
        }
    }
    private void getPermisos(Intent intent, Uri selectedUri)
    {
        List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {

            String packageName = resolveInfo.activityInfo.packageName;
            Log.d("grantUriPermission" , packageName);
            this.grantUriPermission(packageName, selectedUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }
    private void Visualizar(Uri selectedUri)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW); //ACTION_GET_CONTENT,
        intent.setDataAndType(selectedUri, "text/csv");
        getPermisos(intent,selectedUri);

        try {
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(intent, "Seleccionar Visualizador"));
            }
            else
            {
                Toast.makeText(this, "No hay ningun visualizador de CSV instalado, te recomendamos instalar Google Sheets y reintentar generar el reporte.", Toast.LENGTH_SHORT).show();
                final String appPackageName = "com.google.android.apps.docs.editors.sheets";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }

        } catch (ActivityNotFoundException e) {
            //Toast.makeText(getApplicationContext(), "ActivityNotFound" , Toast.LENGTH_LONG).show();
            mostrarToast(3,"ActivityNotFound");
        }


    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            final File reporte = new File(getExternalFilesDir(null) + File.separator + "Reporte.csv"); //getFilesDir

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(reporte);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("File Download: " , fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

                if(reporte.exists()) {
                    final Uri uriFile;

                    if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
                        uriFile = getUriForFile(getApplicationContext(), "com.ort.SafeDesk.fileprovider", reporte);
                    } else{
                        uriFile = Uri.fromFile(reporte);
                    }

                    Log.d("Uri ToString" , reporte.toString());

                    AlertDialog.Builder builder = null;
                    builder = new AlertDialog.Builder(this);

                    String msj = "??Desea envia un mail con el Reporte?";
                    builder.setMessage(msj);
                    builder.setTitle("Reporte Generado");
                    builder.setPositiveButton("Enviar email", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            envioEmail(uriFile);
                        }
                    }).setNegativeButton("Visualizar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Visualizar(uriFile);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }else{
                    //Toast.makeText(getApplicationContext(), "No se encontro el reporte..." , Toast.LENGTH_LONG).show();
                    mostrarToast(2,"No se encontro el reporte...");
                }

            }
        } catch (IOException e) {
            return false;
        }
    }
    private void configPisosSpinner(long idEdificio){
        if (idEdificio == 0)
        {
            llenarSpinnersString(pisosDP, new ArrayList<String>(),reporteSeleccionado.isSelPiso());
        }

        APITurnos getPisos = (APITurnos)ApiUtils.getAPI(APITurnos.class);
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
        if (idEdificio == 0)
        {
            llenarSpinnersString(horasDP, new ArrayList<String>(),reporteSeleccionado.isSelHorario());
        }

        APITurnos getHora = (APITurnos)ApiUtils.getAPI(APITurnos.class);
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
                llenarSpinnersString(edificiosDP, edificiosList,reporteSeleccionado.isSelEdificio());
            }

            @Override
            public void onFailure(Call<List<Edificio>> call, Throwable t) {

            }
        });
    }


    private void configGerenciasSpinner(){
        APIUsuarios getGerencias = (APIUsuarios)ApiUtils.getAPI(APIUsuarios.class);

        Call<List<Gerencia>> call = getGerencias.getGerencias();

        call.enqueue(new Callback<List<Gerencia>>() {
            @Override
            public void onResponse(Call<List<Gerencia>> call, Response<List<Gerencia>> response) {
                gerencias = response.body();
                List<String> gerenciasList = new ArrayList<String>();

                for(Gerencia u : gerencias){
                    gerenciasList.add(u.getNombre());
                }

                llenarSpinnersString(gerenciasDP, gerenciasList,reporteSeleccionado.isSelGerencia());
            }

            @Override
            public void onFailure(Call<List<Gerencia>> call, Throwable t) {

            }
        });

    }


    private void configReportesSpinner(){
        APIReportes getReportes = (APIReportes) ApiUtils.getAPI(APIReportes.class);

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
                llenarSpinnersString(usuariosDP, usuariosList,reporteSeleccionado.isSelUsuario());
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

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
            spinner.setSelection(list.size()-1);

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