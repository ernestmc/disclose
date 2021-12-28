package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.DatagramPacket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> hostArrayList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private static final int servicePort = 5775;
    private PacketListener listener;
    private static final int updatePeriod = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.hostList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hostArrayList);
        listView.setAdapter(adapter);
        listener = new PacketListener(servicePort);

        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            @Override
            public void run() {
                if (listener.countPackets() > 0) {
                    DatagramPacket p = listener.popPacket();
                    addHost(p.getAddress().toString());
                }
                h.postDelayed(this, updatePeriod);
            }
        }, updatePeriod);
    }

    private void addHost(String name) {
        hostArrayList.add(name);
        adapter.notifyDataSetChanged();
    }
}
