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
import com.ort.SafeDesk.Interface.APILogin;
import com.ort.SafeDesk.Model.Token;
import com.ort.SafeDesk.Model.UserDTO;
import com.ort.SafeDesk.Utils.ApiUtils;
import com.ort.SafeDesk.Utils.Global;
import com.ort.SafeDesk.Utils.SettingPreferences;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        if (settingPreferences.getTokenSaved().getTokenKey() != null)
        {
            accessMainApp();
        }

        logButton = findViewById(R.id.btn_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
    }
    private void LogIn()
    {
        EditText inputUser = username.getEditText();
        EditText inputPass = password.getEditText();
        sendLoginPost(new UserDTO(inputUser.getText().toString(), inputPass.getText().toString()));
    }

    private void sendLoginPost(UserDTO usuario) {

        APILogin Login = (APILogin) ApiUtils.getAPISinToken(APILogin.class);

        Call<Token> call = Login.login(usuario);
        call.enqueue(new Callback<Token>()  {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {
                    Token token = response.body();
                    settingPreferences.saveToken(token);
                    accessMainApp();
                }
                else
                {
                    showDefaultError(response);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast. makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDefaultError(Response response)
    {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void accessMainApp() {
        Toast.makeText(getApplicationContext(),"Bienvenido a Safe Desk " + Global.token.getNombre(),Toast. LENGTH_SHORT).show();
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