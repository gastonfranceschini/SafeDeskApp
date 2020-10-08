package com.ort.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.ort.myapplication.Interface.GetEdificios;
import com.ort.myapplication.Interface.Login;
import com.ort.myapplication.Model.Edificio;
import com.ort.myapplication.Model.Token;
import com.ort.myapplication.utils.ApiUtils;
import com.ort.myapplication.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import android.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView myJsonTxtView;

    private CardView autoDiagnostico;
    private CardView reservaJornada;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoDiagnostico = findViewById(R.id.cardView1);
        reservaJornada = findViewById(R.id.cardView2);
        //myJsonTxtView = findViewById(R.id.jsonText);
        //GET
        //getPost();

       autoDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessMainApp(DiagnosticoActivity.class);
            }
        });

        reservaJornada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessMainApp(ReservaTurno.class);
            }
        });
    }

    private void getPost(){

        //GetEdificios getEdificios = RetrofitClient.getClient("http://10.0.2.2:3000/").create(GetEdificios.class);

        GetEdificios getEdificios = (GetEdificios)ApiUtils.getAPI(GetEdificios.class);

        Call<List<Edificio>> call = getEdificios.getEdificios();

        call.enqueue(new Callback<List<Edificio>>() {
            @Override
            public void onResponse(Call<List<Edificio>> call, Response<List<Edificio>> response) {

                if (response.isSuccessful()) {
                    List<Edificio> EdificiosList = response.body();
                    for (Edificio edi: EdificiosList) {
                        String content = "";
                        content += "id: " + edi.getId() + "\n";
                        content += "nombre: " + edi.getNombre() + "\n";
                        myJsonTxtView.append(content);
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
            public void onFailure(Call<List<Edificio>> call, Throwable t) {
                myJsonTxtView.setText(t.getMessage());
            }
        });
    }

    private void accessMainApp(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
