package com.example.kaswarga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UbahData extends AppCompatActivity {
    // objek edittext
    EditText ub_nama,ub_id,ub_telp;

    //objek button
    Button ubahdata;

    // objek class koneksi
    Koneksi koneksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data);
        init();
        // deklarasi objek koneksi
        koneksi =new Koneksi(getApplicationContext());

        // untuk menampung id yang lama
        String id_lama =getIntent().getStringExtra("id");

        ub_id.setText(getIntent().getStringExtra("id"));
        ub_nama.setText(getIntent().getStringExtra("nama"));
        ub_telp.setText(getIntent().getStringExtra("telepon"));
        ubahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // memanggil method Ubah yang ada pada class koneksi
                koneksi.Ubah(id_lama,ub_id.getText().toString(),ub_nama.getText().toString(),ub_telp.getText().toString());
                finish();
            }
        });
    }

    // method inisialisasi
    public void init(){
        ub_id   = findViewById(R.id.edit_id_warga);
        ub_nama = findViewById(R.id.edit_nama_warga);
        ub_telp = findViewById(R.id.edit_notelp);
        ubahdata = findViewById(R.id.btnEditdata);
    }
}