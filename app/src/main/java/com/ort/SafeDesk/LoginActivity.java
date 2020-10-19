package com.ort.SafeDesk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ort.SafeDesk.Interface.GetTurnosHistoricos;
import com.ort.SafeDesk.Interface.Login;
import com.ort.SafeDesk.Model.Token;
import com.ort.SafeDesk.Model.Turnos;
import com.ort.SafeDesk.Model.UserDTO;
import com.ort.SafeDesk.utils.ApiUtils;
import com.ort.SafeDesk.utils.Global;
import com.ort.SafeDesk.utils.SettingPreferences;

import org.json.JSONObject;

import java.util.List;

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
    private SettingPreferences settingPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settingPreferences = new SettingPreferences(getApplicationContext());

        //traigo el token almacenado y me fijo si tiene algo
        if (settingPreferences.getTokenSaved().getToken() != null)
        {
            Toast.makeText(getApplicationContext(),"Bienvenido a Safe Desk " + Global.token.getNombre(),Toast. LENGTH_SHORT).show();
            accessMainApp();
        }

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

        Login Login = (Login) ApiUtils.getAPI(Login.class);

        /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://safedesk.apiexperta.com.ar")
                        //.baseUrl("https://10.0.2.2:3000/%22)
                        // .baseUrl("http://192.168.0.21:3000/%22)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        Login Login = retrofit.create(Login.class);*/

        EditText inputUser = username.getEditText();
        EditText inputPass = password.getEditText();


        Call<Token> call = Login.login(new UserDTO(inputUser.getText().toString(), inputPass.getText().toString()));
        call.enqueue(new Callback<Token>()  {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {
                    Token token = response.body();
                    settingPreferences.saveToken(token);
                    Toast.makeText(getApplicationContext(),"Bienvenido a Safe Desk " + Global.token.getNombre(),Toast. LENGTH_SHORT).show();
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onBackPressed() {
    }

}