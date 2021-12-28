package com.example.myapplication;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayDeque;

public class PacketListener {
    private DatagramSocket socket;
    private ArrayDeque<DatagramPacket> packets = new ArrayDeque<DatagramPacket>();
    private static final int bufferSize = 256;
    private byte buffer[] = new byte[bufferSize];

    PacketListener(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        Thread listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, bufferSize);
                    try {
                        socket.receive(packet);
                        pushPacket(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listenThread.start();
    }

    public synchronized void pushPacket(DatagramPacket packet) {
        packets.add(packet);
    }

    public synchronized DatagramPacket popPacket() {
        return packets.pollFirst();
    }

    public synchronized int countPackets() {
        return packets.size();
    }

    public void close() {
        socket.close();
    }
}
