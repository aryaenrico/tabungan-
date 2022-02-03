package com.example.kaswarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Transaksi extends AppCompatActivity {
    // objek edittext
    EditText nama,ID,total;

    //objek button
    Button cek_id,proses,Dummy;

    // cursor untuk menampilkan nama sesuai id
    Cursor cursor;

    //cursor untuk mendapat saldo di table rekening
    Cursor cursor_saldo;

    // objek koneksi
    Koneksi koneksi;

    // untuk menampung kodetransaksi 1 =setor ,2 =penarikan
    static int kode_transaksi;
    String Nama;
    int saldo;
    SQLiteDatabase db;
    String wk;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        init();

        koneksi = new Koneksi(getApplicationContext());

        // membuat table transaksi
        koneksi.CreateTransaksi();

        //mengisi data ke table transaksi
        koneksi.dataTransaksi();

        // membuat tabel_detil_transaksi
        koneksi.Create_Detil_Transaksi();


        kode_transaksi =getIntent().getIntExtra("id",0);


        //saat tombol cekid ditekan
       cek_id.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // cursor disii dengan method getnama pada class koneksi
                cursor =koneksi.getnama(ID.getText().toString());
                if (cursor.moveToFirst()){
                    while(!cursor.isAfterLast()){
                         Nama =cursor.getString(0);
                         nama.setText(Nama);
                        cursor.moveToNext();
                    }
                }else{
                    nama.setText("Tidak ada");
                }
                cursor.close();
           }
       });

       // saat tombol proses ditekan
        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cursor_saldo disii dengan method get_recent_saldo pada class koneksi
                cursor_saldo = koneksi.get_recent_saldo(ID.getText().toString());
                if (cursor_saldo.moveToFirst()){
                    while(!cursor_saldo.isAfterLast()){
                        saldo =cursor_saldo.getInt(0);
                        cursor_saldo.moveToNext();
                    }
                    cursor_saldo.close();
                }
                //memanggil method update saldo pada class koneksi
            koneksi.Update_Saldo(kode_transaksi,ID.getText().toString(),Integer.parseInt(total.getText().toString()),saldo);
            finish();

            }
        });

        Dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursorcek;
                 cursorcek = koneksi.Cek_Table_Transaksi(ID.getText().toString());
                 if (cursorcek.moveToFirst()){
                     while (!cursorcek.isAfterLast()){
                            wk =cursorcek.getString(0);
                            cursorcek.moveToNext();
                     }
                     cursorcek.close();
                     total.setText(wk);
                 }
            }
        });


    }

    // method inisialisasi
    public void init(){
        ID     = findViewById(R.id.id_warga);
        cek_id = findViewById(R.id.btnCek_Id);
        nama   = findViewById(R.id.id_nama_warga);
        proses =findViewById(R.id.btnProses);
        total  = findViewById(R.id.id_jumlah);
        Dummy  = findViewById(R.id.btnCek_dummy);

    }

}