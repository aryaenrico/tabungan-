package com.example.kaswarga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TambahData extends AppCompatActivity {
    // objek koneksi
    Koneksi koneksi;

    // objek edittext
    EditText ednama,ednotelp,edid;

    //objek button
    Button tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        init();
        //deklarasi objek koneksi
        koneksi =new Koneksi(getApplicationContext());

        //saat tombol tambah di klik
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method savedata yang ada pada class koneksi
                koneksi.SaveData(edid.getText().toString(),ednama.getText().toString(),ednotelp.getText().toString());
                finish();
            }
        });
    }

    // method inisialisasi
    public void init(){
        ednama =findViewById(R.id.add_nama_warga);
        ednotelp =findViewById(R.id.add_notelp);
        edid =findViewById(R.id.add_id_warga);
        tambah = findViewById(R.id.btnAdddata);
    }


}