package com.ort.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MisReservasActivity extends AppCompatActivity {
    private ListView mListView1, mListView2;
    private ArrayAdapter aAdapter1, aAdapter2;
    private String[] users1 = { "Suresh Dasari", "Rohini Alavala", "Trishika Dasari", "Praveen Alavala", "Madav Sai", "Hamsika Yemineni"};
    private String[] users2 = { "Maria Jose", "Alan Muskat", "Ariel Sapir", "Praveen Alavala", "Madav Sai", "Hamsika Yemineni"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misreservas_new);
        mListView1 = (ListView) findViewById(R.id.userlist1);
        aAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users1);
        mListView1.setAdapter(aAdapter1);
        mListView2 = (ListView) findViewById(R.id.userlist2);
        aAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users2);
        mListView2.setAdapter(aAdapter2);
    }
}