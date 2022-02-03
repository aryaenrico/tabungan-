package com.example.kaswarga;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

public class Detail extends AppCompatActivity {
    //objek edittext
    EditText id,nama,saldo;

    //objek koneksi
    Koneksi koneksi;

    //cursor untuk mendapat saldo saat ini
    Cursor cursor;

    int saldo_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        // deklarasi objek koneksi
        koneksi =new Koneksi(getApplicationContext());

        id.setText(getIntent().getStringExtra("id_saldo"));
        nama.setText(getIntent().getStringExtra("nama_details"));

        //memanggil method get_recent_saldo yang ada di class koneksi
        cursor =koneksi.get_recent_saldo(id.getText().toString());
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                 saldo_detail =cursor.getInt(0);
                cursor.moveToNext();
            }
            cursor.close();
        }
        saldo.setText(""+saldo_detail);
    }
    public void init(){
        id    = findViewById(R.id.detail_id_warga);
        nama  = findViewById(R.id.detail_nama_warga);
        saldo = findViewById(R.id.detail_saldo);
    }
}