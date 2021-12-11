package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> hostArrayList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.hostList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hostArrayList);
        listView.setAdapter(adapter);
        for (int i=1; i<12; i++) {
            addHost("Item " + i);
        }
    }

    private void addHost(String name) {
        hostArrayList.add(name);
        adapter.notifyDataSetChanged();
    }
}