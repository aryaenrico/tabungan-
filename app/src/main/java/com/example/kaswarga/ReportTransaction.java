package com.example.kaswarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReportTransaction extends AppCompatActivity {
    //objek listview
    ListView tampilan;

    //objek edittext
    EditText search_tsk;

    //objek koneksi
    Koneksi koneksi;

    //list yang menampung objek dari class ReportTransaactionModel
    List<ReportTransaaction_model>show_data;

    //objek dari class custom
    Custom adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_transaction);
        init();
        koneksi = new Koneksi(getApplicationContext());
        show_data= new ArrayList<ReportTransaaction_model>();

    }
    public void init(){
        tampilan   = findViewById(R.id.result_transaksi);
        search_tsk = findViewById(R.id.search_id_transaksi);
    }
    public void result_transaction(View view){
        show_data =koneksi.report_transaction(search_tsk.getText().toString());
        adapter = new Custom(getApplicationContext(),show_data);
        // merubah listview dengan value yang sesuai dengan array adapter
        tampilan.setAdapter(adapter);
    }
}

class Custom extends BaseAdapter{

    Context context;
    LayoutInflater layoutInflater;
    List<ReportTransaaction_model>detil_transaksi;
    Koneksi koneksi;
    Custom(Context context , List<ReportTransaaction_model> param_data){

        this.context =context;
        this.detil_transaksi =param_data;
        this.layoutInflater =LayoutInflater.from(context);
        koneksi =new Koneksi(context);
    }

    @Override
    public int getCount() {
        return detil_transaksi.size();
    }

    @Override
    public Object getItem(int position) {
        return detil_transaksi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView tvnama,tvjenis,tvtanggal,tvtotal;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view1 =layoutInflater.inflate(R.layout.all_data_transaction,parent,false);

        tvnama =view1.findViewById(R.id.namatsk);
        tvjenis=view1.findViewById(R.id.jenistsk);
        tvtanggal = view1.findViewById(R.id.tanggaltsk);
        tvtotal =view1.findViewById(R.id.totaltsk);


        tvnama.setText(detil_transaksi.get(position).getNama_user());
        tvjenis.setText(detil_transaksi.get(position).getjenis_transaksi());
        tvtanggal.setText(detil_transaksi.get(position).getTanggal());
        tvtotal.setText(""+detil_transaksi.get(position).getTransaksi());
        return view1;

    }
}
