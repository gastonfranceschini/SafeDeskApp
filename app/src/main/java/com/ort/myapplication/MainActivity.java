package com.ort.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.ort.myapplication.Interface.JsonPlaceholderApi;
import com.ort.myapplication.Interface.Login;
import com.ort.myapplication.Model.Post;
import com.ort.myapplication.Model.Token;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myJsonTxtView = findViewById(R.id.jsonText);

        //getPost();
        sendPost();
    }

    public void sendPost() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                //.baseUrl("https://127.0.0.1:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Login Login = retrofit.create(Login.class);

        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("email","aas22");
        paramObject.addProperty("password","Samsung");

        Call<Token> call = Login.login(paramObject.toString());
        call.enqueue(new Callback<Token>()  {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()) {

                    Toast toast=Toast. makeText(getApplicationContext(),response.body().toString(),Toast. LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                    myJsonTxtView.setText("Respuesta OK:" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast toast=Toast. makeText(getApplicationContext(),t.getMessage(),Toast. LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                myJsonTxtView.setText("Respuesta no exitosa:" + t.getMessage());
            }
        });
    }

    private void getPost(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        Call<List<Post>> call = jsonPlaceholderApi.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()) {
                       myJsonTxtView.setText("Respuesta no exitosa:" + response.code());
                       return;
                }

                List<Post> postList = response.body();
                for (Post post: postList) {
                    String content = "";
                    content += "userId: " + post.getUserId() + "\n";
                    content += "id: " + post.getId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Body: " + post.getBody() + "\n\n";
                    myJsonTxtView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                myJsonTxtView.setText(t.getMessage());
            }
        });
    }
}
