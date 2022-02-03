package com.example.kaswarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // method saat tombol setor di tekan
    public void setor(View view){
        int id=1;
        Intent move = new Intent(MainActivity.this ,Transaksi.class);
        move.putExtra("id",id);
        startActivity(move);
    }
    // method saat tombol tarik ditekan
    public void tarik(View view){
        int id=2;
        Intent move = new Intent(MainActivity.this ,Transaksi.class);
        move.putExtra("id",id);
        startActivity(move);
    }
    // method saat tombol semua member di tekan
    public void allmember(View view){
        Intent move =new Intent(MainActivity.this,AllData.class);
        startActivity(move);
    }
    // method saat tombol detik transaksi di tekan
    public void detil(View view){
        Intent move =new Intent(MainActivity.this,ReportTransaction.class);
        startActivity(move);
    }

}