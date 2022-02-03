package com.example.kaswarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AllData extends AppCompatActivity {
    // membuat objek listview
    ListView listView;

    // membuat objek tambah
    Button tambah;

    // objek class custom_list untuk mengisi listview
    Custom_list tampilan;

    // objek class Koneksi (open helper)
    Koneksi koneksi;

    // list yang akan di isi kedalam listview
    List<WargaModel> data_show;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);
        init();
        // deklarasi objek koneksi
        koneksi   = new Koneksi(getApplicationContext());

        // mengisi list view dengan method show data yang ada di class koneksi
        data_show =  koneksi.show_data();

        tampilan =new Custom_list(getApplicationContext(),data_show);

        // menset tampilan yang ada di listview dengan array list
        listView.setAdapter(tampilan);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllData.this,TambahData.class));
            }
        });

    }

    // method inisiasi
    public void init(){
        listView = findViewById(R.id.all_warga_view);
        tambah   = findViewById(R.id.btnTambahData);
    }

    // untuk menampilkan data secara langsung saat menambah data
    public void onResume(){
        super.onResume();
        koneksi =new Koneksi(getApplicationContext());
        data_show =  koneksi.show_data();
        tampilan =new Custom_list(getApplicationContext(),data_show);
        listView.setAdapter(tampilan);
    }

}

// kodingan untuk menampilkan listview
class Custom_list extends BaseAdapter {

    Context context;

    List<WargaModel>DataWarga;

    LayoutInflater layoutInflater;

    TextView nama,id,notelp;

    Button hapus,edit,detail;

    Koneksi koneksi;

    Custom_list(Context context ,List<WargaModel> param_data){
        this.context =context;
        this.DataWarga =param_data;
        layoutInflater =LayoutInflater.from(context);
        koneksi =new Koneksi(context);
    }
    @Override
    public int getCount() {
        return DataWarga.size();
    }

    @Override
    public Object getItem(int position) {
        return DataWarga.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view1 =layoutInflater.inflate(R.layout.alldatawarga,parent,false);
       nama   = view1.findViewById(R.id.res_nama);
       notelp = view1.findViewById(R.id.res_no_telp);

       nama.setText(DataWarga.get(position).getNama());
      // notelp.setText(""+DataWarga.get(position).getSaldo());
       notelp.setText(DataWarga.get(position).getNo_telp());

       hapus =view1.findViewById(R.id.res_btn_hapus);
       hapus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                koneksi.delete(DataWarga.get(position).getId());
           }
       });

       edit =view1.findViewById(R.id.res_btn_ubah);
       edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent param_ubah =new Intent(context , UbahData.class);
               param_ubah.putExtra("id",DataWarga.get(position).getId());
              // param_ubah.putExtra("id",DataWarga.get(position).getSaldo());
               param_ubah.putExtra("nama",DataWarga.get(position).getNama());
               param_ubah.putExtra("telepon",DataWarga.get(position).getNo_telp());
               context.startActivity(param_ubah);
           }
       });

       detail = view1.findViewById(R.id.res_btn_detail);
       detail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent details =new Intent(context,Detail.class);
               details.putExtra("id_saldo",DataWarga.get(position).getId());
               details.putExtra("nama_details",DataWarga.get(position).getNama());
               context.startActivity(details);
           }
       });
       return view1;

    }
}