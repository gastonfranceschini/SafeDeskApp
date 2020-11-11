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

import com.ort.SafeDesk.Interface.Reportes;
import com.ort.SafeDesk.Interface.Usuarios;
import com.ort.SafeDesk.Model.ChangePassDTO;
import com.ort.SafeDesk.utils.ApiUtils;
import com.ort.SafeDesk.utils.Global;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambioContrasena extends AppCompatActivity implements View.OnClickListener {

    private EditText antigua;
    private EditText nueva;
    private EditText confirma;
    private Button cambiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_contrasena);

        antigua = findViewById(R.id.editpassant);
        nueva = findViewById(R.id.editpassnueva);
        confirma = findViewById(R.id.editconfpassnueva);
        cambiar = findViewById(R.id.btn_fincambio);

        cambiar.setOnClickListener(this);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onClick(View view) {
        if (cambiar.equals(view)){


            if  (nueva.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(), "La contraseña nueva no puede estar vacia.", Toast.LENGTH_LONG).show();
                return;
            }

            if  (!nueva.getText().toString().equals(confirma.getText().toString()))
            {
                Toast.makeText(getApplicationContext(), "La contraseña nueva y la confirmacion no coinciden.", Toast.LENGTH_LONG).show();
                return;
            }

            changePass(new ChangePassDTO(Global.token.getUserId(),antigua.getText().toString(),nueva.getText().toString()));
        }
    }
    private void accessMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void changePass(ChangePassDTO changePass) {

        Usuarios userService = (Usuarios) ApiUtils.getAPI(Usuarios.class);
        Call<ResponseBody> call = userService.changePassword(changePass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Global.token.setCambioPassObligatorio(0);
                    Toast.makeText(getApplicationContext(), "Password cambiada correctamente", Toast.LENGTH_LONG).show();
                    accessMainApp();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void accessMainApp(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}