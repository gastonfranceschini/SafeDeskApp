package com.ort.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
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
import com.ort.myapplication.Interface.GetEdificios;
import com.ort.myapplication.Interface.GetTurnos;
import com.ort.myapplication.Interface.GetTurnosHistoricos;
import com.ort.myapplication.Model.Edificio;
import com.ort.myapplication.Model.Turnos;
import com.ort.myapplication.Model.UsuarioDep;
import com.ort.myapplication.utils.ApiUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private String[] users1 = {"Suresh Dasari", "Rohini Alavala", "Trishika Dasari", "Praveen Alavala", "Madav Sai", "Hamsika Yemineni"};
    private String[] users2 = {"Maria Jose", "Alan Muskat", "Ariel Sapir", "Praveen Alavala", "Madav Sai", "Hamsika Yemineni"};
    private List<Turnos> ListaTurnos;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private void getTurnos(){

        GetTurnos getTurnos = (GetTurnos) ApiUtils.getAPI(GetTurnos.class);

        Call<List<Turnos>> call = getTurnos.getTurnos();

        call.enqueue(new Callback<List<Turnos>>() {
            @Override
            public void onResponse(Call<List<Turnos>> call, Response<List<Turnos>> response) {

                if (response.isSuccessful()) {
                    //List<Turnos> ListaTurnos = response.body();
                    ListaTurnos = response.body();

                    List<String> ListaTurnosS = new ArrayList<String>();

                    for(Turnos t : ListaTurnos){
                        ListaTurnosS.add(t.getFechaTurno() + " - " + t.getEdificio());

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
                        ListaTurnosHS.add(t.getFechaTurno() + " - " + t.getEdificio());



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
                    codigoQR.setVisibility(View.VISIBLE);
                    reservasPasadas.setVisibility(View.GONE);
                    reservaElegida.setText((String) parent.getItemAtPosition(position));
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
                } else {
                    codigoQR.setVisibility(View.GONE);
                    reservasPasadas.setVisibility(View.VISIBLE);
                }
            }

        });

    }


}