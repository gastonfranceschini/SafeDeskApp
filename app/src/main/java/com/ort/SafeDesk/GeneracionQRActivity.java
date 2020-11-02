package com.ort.SafeDesk;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.ort.SafeDesk.Interface.GetTurnos;
import com.ort.SafeDesk.Interface.GetTurnosHistoricos;
import com.ort.SafeDesk.Model.Turnos;
import com.ort.SafeDesk.utils.ApiUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GeneracionQRActivity extends AppCompatActivity implements View.OnClickListener{

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr);

        //getTurno(Integer.parseInt("1"));

        scanCode();

        button = (Button)findViewById(R.id.scanQR);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        scanCode();
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escaneando ...");
        integrator.initiateScan();
    }
    private void MostrarTurno(Turnos turno)
    {
        AlertDialog.Builder builder = null;
        builder = new AlertDialog.Builder(this);

        String msj =
                "Fecha: " + turno.getFechaTurno() + "\n" +
                "Edificio: " + turno.getEdificio() + "\n" +
                "Piso: " + turno.getPiso() + "\n" +
                "Horario: " + turno.getHorario() + "\n" +
                "";
        builder.setMessage(msj);
        builder.setTitle("Turno Encontrado");
        builder.setPositiveButton("Escanear nuevamente.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scanCode();
            }
        }).setNegativeButton("Finalizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void chequeoTurno(Turnos turno){
        //ZoneId z = ZoneId.of("America/Argentina/Buenos_Aires");
        //LocalTime now = LocalTime.now(z);
        //LocalTime limit = LocalTime.parse( turno.getHorario() );
        Date fHoy = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fFinal = df.format(fHoy);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(fFinal.equals(turno.getFechaTurno())){
            builder.setTitle("Turno Confirmado!");
            builder.setMessage("El turno se confirmo con éxito");
            builder.setPositiveButton("Ok", null);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
            dialog.show();
        }else {
            builder.setTitle("Turno Inválido!");
            builder.setMessage("El turno no corresponde a la fecha de hoy");
            builder.setPositiveButton("Ok", null);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
            dialog.show();
        }
    }

    private void getTurno(int idTurno){
        GetTurnos getTurno = (GetTurnos) ApiUtils.getAPI(GetTurnos.class);
        Call<Turnos> call = getTurno.getTurno(idTurno);
        call.enqueue(new Callback<Turnos>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Turnos> call, Response<Turnos> response) {

                if (response.isSuccessful()) {
                    Turnos turno = response.body();
                    //MostrarTurno(turno);
                    chequeoTurno(turno);
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
            public void onFailure(Call<Turnos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() != null) {
                getTurno(Integer.parseInt(result.getContents()));

            } else {
                Toast.makeText( this, "Sin resultados", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
