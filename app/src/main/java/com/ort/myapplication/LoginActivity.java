package com.ort.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ort.myapplication.Interface.APIDiagnostico;
import com.ort.myapplication.Interface.Login;
import com.ort.myapplication.Model.Diagnostico;
import com.ort.myapplication.Model.Token;
import com.ort.myapplication.Model.UserDTO;
import com.ort.myapplication.utils.ApiUtils;
import com.ort.myapplication.utils.Global;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Button logButton;
    private TextInputLayout username;
    private TextInputLayout password;
    //private TextView myJsonTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logButton = findViewById(R.id.btn_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
            }
        });
    }

    public void sendPost() {
               Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.190.67.223/")
                //.baseUrl("https://10.0.2.2:3000/")
               // .baseUrl("http://192.168.0.21:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Login Login = retrofit.create(Login.class);

        //Login Login = (Login) ApiUtils.getAPI(Login.class);

        EditText inputUser = username.getEditText();
        EditText inputPass = password.getEditText();


        Call<Token> call = Login.login(new UserDTO(inputUser.getText().toString(), inputPass.getText().toString()));
        //Call<Token> call = Login.login(paramObject.toString());
        //Call<Token> call = Login.loginTest();
        call.enqueue(new Callback<Token>()  {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),response.body().toString(),Toast. LENGTH_SHORT).show();
                    Token token = response.body();
                    Global.userId = token.getUserId();
                    Global.token = token.getToken();
                    accessMainApp();
                }
                else
                {
                    //response.code() == 422
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast. makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void accessMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}