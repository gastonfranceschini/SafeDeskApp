package com.ort.SafeDesk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.ort.SafeDesk.Interface.GetTurnos;
import com.ort.SafeDesk.Interface.GetTurnosHistoricos;
import com.ort.SafeDesk.Model.Turnos;
import com.ort.SafeDesk.utils.ApiUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisReservasActivity extends AppCompatActivity {
    private ListView mListView1, mListView2;
    private ImageView imageViewQR;
    private TextView reservaElegida;
    private LinearLayout reservasPasadas, codigoQR;
    private ArrayAdapter aAdapter1, aAdapter2;
    private String[] users1 = {"test"};
    private List<Turnos> ListaTurnos;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private void getTurnos(){

        GetTurnos getTurnos = (GetTurnos) ApiUtils.getAPI(GetTurnos.class);

        Call<List<Turnos>> call = getTurnos.getTurnos();

        call.enqueue(new Callback<List<Turnos>>() {
            @Override
            public void onResponse(Call<List<Turnos>> call, Response<List<Turnos>> response) {

                if (response.isSuccessful()) {
                    ListaTurnos = response.body();
                    List<String> ListaTurnosS = new ArrayList<String>();
                    for(Turnos t : ListaTurnos){
                        try {
                            ListaTurnosS.add(t.getFechaTurno() + " - " + t.getEdificio() + " - " + t.getPiso());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    LlenarTurnos(ListaTurnosS);
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
            public void onFailure(Call<List<Turnos>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTurnosHistorico(){
        GetTurnosHistoricos getTurnosHistoria = (GetTurnosHistoricos) ApiUtils.getAPI(GetTurnosHistoricos.class);
        Call<List<Turnos>> call = getTurnosHistoria.getTurnos();
        call.enqueue(new Callback<List<Turnos>>() {
            @Override
            public void onResponse(Call<List<Turnos>> call, Response<List<Turnos>> response) {

                if (response.isSuccessful()) {
                    List<Turnos> ListaTurnosH = response.body();

                    List<String> ListaTurnosHS = new ArrayList<String>();
                    for(Turnos t : ListaTurnosH){
                        try {
                            ListaTurnosHS.add(t.getFechaTurno() + " - " + t.getEdificio() + " - " + t.getPiso());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    LlenarTurnosHistoricos(ListaTurnosHS);
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
            public void onFailure(Call<List<Turnos>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void LlenarTurnosHistoricos(List<String> ListaTurnosHS){
        mListView2 = (ListView) findViewById(R.id.userlist2);
        aAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaTurnosHS);
        mListView2.setAdapter(aAdapter2);
    }

    private void LlenarTurnos(List<String> ListaTurnos){
        mListView1 = (ListView) findViewById(R.id.userlist1);
        aAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaTurnos);
        mListView1.setAdapter(aAdapter1);
    }
    private String construirMsgTurno(Turnos turno) throws ParseException {
        return
                "Fecha: " + turno.getFechaTurno() + "\n" +
                        "Edificio: " + turno.getEdificio() + " " + turno.getPiso() + "\n" +
                        "Horario: " + turno.getHorario() ;
    }

    private void PreguntarQRoMaps(final int position){
        AlertDialog.Builder builder = null;
        builder = new AlertDialog.Builder(this);

        String msj = "¿Generar QR?";
        builder.setMessage(msj);
        builder.setTitle("Mis Turnos");
        builder.setPositiveButton("Generar QR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                codigoQR.setVisibility(View.VISIBLE);
                reservasPasadas.setVisibility(View.GONE);

                try {
                    reservaElegida.setText(construirMsgTurno(ListaTurnos.get(position)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //reservaElegida.setText((String) parent.getItemAtPosition(position));
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(Integer.toString(ListaTurnos.get(position).getTurnoId()), BarcodeFormat.QR_CODE, 500, 500);
                    //BitMatrix bitMatrix = multiFormatWriter.encode((String) parent.getItemAtPosition(position), BarcodeFormat.QR_CODE, 500, 500);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageViewQR.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).setNegativeButton("Ir al Edificio", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String geoPos = ListaTurnos.get(position).getGeoPos();
                Uri location = Uri.parse("geo:" + geoPos + "?z=16"); // z param is zoom level
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(Intent.createChooser(mapIntent, "Ir al edificio con:"));
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misreservas_new);

        mListView1 = (ListView) findViewById(R.id.userlist1);
        aAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users1);
        mListView1.setAdapter(aAdapter1);

        getTurnos();
        getTurnosHistorico();

        reservaElegida = (TextView) findViewById(R.id.txt_reservaElegida);
        reservasPasadas = (LinearLayout) findViewById(R.id.reservasPasadas);
        codigoQR = (LinearLayout) findViewById(R.id.codigoQR);
        imageViewQR = (ImageView) findViewById(R.id.imageviewQR);

        mListView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (reservasPasadas.getVisibility() == View.VISIBLE) {
                    PreguntarQRoMaps(position);

                } else {
                    codigoQR.setVisibility(View.GONE);
                    reservasPasadas.setVisibility(View.VISIBLE);
                }
            }

        });

    }


}