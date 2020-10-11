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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MisReservasActivity extends AppCompatActivity {
    private ListView mListView1, mListView2;
    private CardView cardViewReserva2;
    private ImageView imageViewQR;
    private TextView reservaElegida;
    private LinearLayout reservasPasadas, codigoQR;
    private ArrayAdapter aAdapter1, aAdapter2;
    private String[] users1 = {"Suresh Dasari", "Rohini Alavala", "Trishika Dasari", "Praveen Alavala", "Madav Sai", "Hamsika Yemineni"};
    private String[] users2 = {"Maria Jose", "Alan Muskat", "Ariel Sapir", "Praveen Alavala", "Madav Sai", "Hamsika Yemineni"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misreservas_new);
        cardViewReserva2 = (CardView) findViewById(R.id.cardViewReserva2);
        mListView1 = (ListView) findViewById(R.id.userlist1);
        aAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users1);
        mListView1.setAdapter(aAdapter1);
        mListView2 = (ListView) findViewById(R.id.userlist2);
        aAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users2);
        mListView2.setAdapter(aAdapter2);

        reservaElegida = (TextView) findViewById(R.id.txt_reservaElegida);
        reservasPasadas = (LinearLayout) findViewById(R.id.reservasPasadas);
        codigoQR = (LinearLayout) findViewById(R.id.codigoQR);
        imageViewQR = (ImageView) findViewById(R.id.imageviewQR);

        mListView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (reservasPasadas.getVisibility() == View.VISIBLE) {
                    codigoQR.setVisibility(View.VISIBLE);
                    reservasPasadas.setVisibility(View.GONE);
                    reservaElegida.setText((String) parent.getItemAtPosition(position));
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try{
                        BitMatrix bitMatrix = multiFormatWriter.encode((String) parent.getItemAtPosition(position),BarcodeFormat.QR_CODE,500,500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageViewQR.setImageBitmap(bitmap);
                    }catch (Exception e){
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