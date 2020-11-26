package com.ort.SafeDesk;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ort.SafeDesk.Interface.APITurnos;
import com.ort.SafeDesk.Model.Turno;
import com.ort.SafeDesk.Utils.ApiUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GeneracionQRActivity extends AppCompatActivity implements View.OnClickListener{

    //Button button;
    CardView boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr);

        //getTurno(Integer.parseInt("1"));

        scanCode();

        //button = (Button)findViewById(R.id.scanQR);
        //button.setOnClickListener(this);
        boton = (CardView)findViewById(R.id.scanQR);
        boton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        scanCode();
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CapturesActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escaneando...");
        integrator.initiateScan();
    }
    private String construirMsgTurno(Turno turno) throws ParseException {
        return
                "Fecha: " + turno.getFechaTurno() + "\n" +
                "Edificio: " + turno.getEdificio() + "\n" +
                "Piso: " + turno.getPiso() + "\n" +
                "Horario: " + turno.getHorario() + "\n" +
                "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void chequeoTurno(Turno turno) throws ParseException {
        //ZoneId z = ZoneId.of("America/Argentina/Buenos_Aires");
        //LocalTime now = LocalTime.now(z);
        //LocalTime limit = LocalTime.parse( turno.getHorario() );

        Date fHoy = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fFinal = df.format(fHoy);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int colorBackground;

        if(fFinal.equals(turno.getFechaTurno())){
            builder.setTitle("Turno Confirmado!");
            builder.setMessage("El turno se confirmo con éxito \n" + construirMsgTurno(turno));
            colorBackground = Color.GREEN;
        }else {
            builder.setTitle("Turno Inválido!");
            builder.setMessage("El turno no corresponde a la fecha de hoy \n" + construirMsgTurno(turno));
            colorBackground = Color.RED;
        }

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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(colorBackground));
        dialog.show();

    }

    private void getTurno(int idTurno){
        APITurnos getTurno = (APITurnos) ApiUtils.getAPI(APITurnos.class);
        Call<Turno> call = getTurno.getTurno(idTurno);
        call.enqueue(new Callback<Turno>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Turno> call, Response<Turno> response) {

                if (response.isSuccessful()) {
                    Turno turno = response.body();
                    try {
                        chequeoTurno(turno);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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
            public void onFailure(Call<Turno> call, Throwable t) {
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
