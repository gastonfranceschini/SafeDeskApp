package com.ort.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.ort.myapplication.Interface.JsonPlaceholderApi;
import com.ort.myapplication.Interface.Login;
import com.ort.myapplication.Model.Post;
import com.ort.myapplication.Model.Token;
import com.ort.myapplication.Model.UserDTO;

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
                .baseUrl("http://10.0.2.2:3000/")
                //.baseUrl("https://127.0.0.1:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Login Login = retrofit.create(Login.class);

        EditText inputUser = username.getEditText();
        EditText inputPass = password.getEditText();

        /*JsonObject paramObject = new JsonObject();
        paramObject.addProperty("email", inputUser.getText().toString());
        paramObject.addProperty("password", inputPass.getText().toString());*/

        //Call<Token> call = Login.login(new UserDTO(inputUser.getText().toString(), inputPass.getText().toString()));
        //Call<Token> call = Login.login(paramObject.toString());
        Call<Token> call = Login.loginTest();
        call.enqueue(new Callback<Token>()  {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {
                    Toast toast=Toast. makeText(getApplicationContext(),response.body().toString(),Toast. LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    accessMainApp();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast toast=Toast. makeText(getApplicationContext(),t.getMessage(),Toast. LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        });
    }

    private void accessMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}