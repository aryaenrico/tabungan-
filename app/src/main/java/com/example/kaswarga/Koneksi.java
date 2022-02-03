package com.example.kaswarga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Koneksi extends SQLiteOpenHelper {

    // perubahan yang ada
    Context context;
    SQLiteDatabase db;

    // deklarasi variabel String
    static String Database_name ="tabungan";
    static String table_warga   ="warga";
    static String table_transaksi   ="transaksi";
    static String table_detil_transaksi   ="detil_transaksi";
    static String table_rekening   ="rekening";

    //constructor
    public Koneksi(Context context) {
        super(context, Database_name, null, 1);
        this.context =context;
        db = getReadableDatabase();
        db =getWritableDatabase();
    }

    @Override
    // method oncreate sekaligus membuat table rekening dan table warga
    public void onCreate(SQLiteDatabase db) {
        String sqlrek ="CREATE TABLE IF NOT EXISTS "+table_rekening+"(id varchar(6),saldo int (10),FOREIGN KEY (id) REFERENCES warga(id))";
        db.execSQL(sqlrek);
        String sql =" CREATE TABLE IF NOT EXISTS " +table_warga+"(id varchar(6) primary key  ,nama varchar(50),no_telp varchar(13))";
        db.execSQL(sql);
    }

    // membuat tabel transaksi
    public void CreateTransaksi(){
        String sql2 = " CREATE TABLE IF NOT EXISTS " +table_transaksi+"(id_transaksi varchar(6) primary key  ,jenis varchar(2))";
        db.execSQL(sql2);
    }
    // membuat detil transaksi
    public void Create_Detil_Transaksi(){
        String sql2 = " CREATE TABLE IF NOT EXISTS " +table_detil_transaksi+"(id_transak varchar(6) ,id_warga varchar(6),transaksi int (10),tanggal_transaksi DATETIME DEFAULT CURRENT_TIMESTAMP," +
                " FOREIGN KEY (id_transak) REFERENCES transaksi(id_transaksi),FOREIGN KEY (id_warga) REFERENCES warga(id))";
        db.execSQL(sql2);
    }
    /*
   public void delete_transaksi(){
        String sql ="DELETE FROM "+table_transaksi;
        db.execSQL(sql);
   }

     */
   // insert data transaksi

    public void  dataTransaksi(){
        String sql ="INSERT INTO "+table_transaksi+" (id_transaksi,jenis) VALUES ('2','Penarikan'),('1','Setor')";
        db.execSQL(sql);
    }




    // method untuk table warga

    // menampilkan semua user yang ada pada tabel warga
    public List<WargaModel>show_data(){
        List<WargaModel> data = new ArrayList<WargaModel>();
        Cursor cursor;
        cursor =db.rawQuery("SELECT * FROM "+table_warga,null);
        if (cursor.getCount()>0 ){
            while (cursor.moveToNext()&& cursor!=null){
                String id   = cursor.getString(0);
                String nama = cursor.getString(1);
                String no   = cursor.getString(2);
                data.add(new WargaModel(id,nama,no));
            }
        }
        return data;
    }

    // method untuk insert data untuk table warga dan rekening
    public void SaveData(String Id , String Nama ,String Telepon ){
        int angka =0;
       String sql ="INSERT INTO "+table_warga+"(id,nama,no_telp)"+" VALUES " +"('"+Id+"','"+Nama+"','"+Telepon+"')";
        String sql_rek ="INSERT INTO "+table_rekening+"(id,saldo)"+" VALUES " +"('"+Id+"','"+ angka+"')";
       db.execSQL(sql);
       db.execSQL(sql_rek);
        Toast.makeText(context,"Data Tersimpan",Toast.LENGTH_SHORT).show();
    }

    // menghapus anggota dan rekening sesuai id
    public void delete(String id){
        db.execSQL("DELETE FROM "+table_warga+" WHERE id ='"+id+"'");
        db.execSQL("DELETE FROM "+table_rekening+" WHERE id ='"+id+"'");
        Toast.makeText(context,"Data Sudah Dihapus",Toast.LENGTH_SHORT).show();
    }

    // method untuk ubah pada tabel warga
    public void Ubah(String id_lama,String id_baru,String Nama,String telepon){
        db.execSQL("UPDATE "+table_warga+" "+
                " SET id ='"+id_baru+"',nama='"+Nama+"', no_telp='"+telepon+"'"
                +" WHERE id = '"+id_lama+"'");
        Toast.makeText(context,"Data Sudah Update",Toast.LENGTH_SHORT).show();
    }

    // mengecek apakah ada user dengan id sesuai paramter
    public Cursor getnama(String param_id){
        String sql ="SELECT nama FROM  "+table_warga+" WHERE id ='"+param_id+"'";
        Cursor cursor =db.rawQuery(sql,null);
        return cursor;

    }

    // method untuk data tabel rekening
    // method untuk menampilkan semua data pada tabel rekening
    public List<Table_rekening_model>show_table_rek(){
        List<Table_rekening_model> data = new ArrayList<Table_rekening_model>();
        Cursor cursor;
        cursor =db.rawQuery("SELECT * FROM "+table_rekening,null);
        if (cursor.getCount()>0 && cursor.moveToFirst()){
            while (!cursor.isAfterLast()){

                String id   = cursor.getString(0);
                int param   =cursor.getInt(1);

                data.add(new Table_rekening_model(id,param));
                cursor.moveToNext();
            }
        }
        return data;
    }

    // method mendapat saldo saat ini
    public Cursor get_recent_saldo(String Id){
        Cursor cursor;
        String sql ="SELECT saldo FROM "+" "+table_rekening +" WHERE id ='"+Id+"'";
        cursor = db.rawQuery(sql,null);
        return cursor;
    }

    // method untuk update saldo saat melakukan penyimpanan dan penarikan
    public void Update_Saldo(int param,String id ,int jumlah,int current_saldo){
        if (param==1){
            String sql =" UPDATE "+table_rekening +" SET saldo =saldo+'"+jumlah+"' WHERE id ='"+id+"'";
            String sql_detil_transaksi ="INSERT INTO "+table_detil_transaksi+"(id_transak,id_warga,transaksi,tanggal_transaksi) VALUES ('"+param+"','"+id+"','"+jumlah+"','"+getDateTime()+"')";
            db.execSQL(sql);
            db.execSQL(sql_detil_transaksi);

        }else{
            if (current_saldo - jumlah < 0){
                Toast.makeText(context,"Saldo Tidak Cukup",Toast.LENGTH_SHORT).show();
            }else{
                String sql =" UPDATE "+table_rekening +" SET saldo =saldo - '"+jumlah+"' WHERE id ='"+id+"'";
                String sql_detil_transaksi ="INSERT INTO "+" "+table_detil_transaksi+"(id_transak,id_warga,transaksi,tanggal_transaksi) VALUES ('"+param+"','"+id+"','"+jumlah+"','"+getDateTime()+"')";
                db.execSQL(sql);
                db.execSQL(sql_detil_transaksi);
            }

        }

    }
    // method untuk detil transaction
    // get detil transaction
    public List<ReportTransaaction_model> report_transaction (String id ){
        String sql ="SELECT warga.nama ,transaksi.jenis,detil_transaksi.tanggal_transaksi ,detil_transaksi.transaksi FROM detil_transaksi" +
                " JOIN warga ON warga.id = detil_transaksi.id_warga " +
                "JOIN transaksi ON transaksi.id_transaksi = detil_transaksi.id_transak" +
                " WHERE detil_transaksi.id_warga = '"+id+"'";

        Cursor cursor =db.rawQuery(sql,null);
        List<ReportTransaaction_model> data =new ArrayList<ReportTransaaction_model>();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String nama    = cursor.getString(0);
                String jenis   = cursor.getString(1);
                String tanggal = cursor.getString(2);
                int total      =cursor.getInt(3);
                data.add(new ReportTransaaction_model(nama,jenis,tanggal,total));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return data;

    }

    // method mendapat tanggal dan waktu saat ini
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // cek table transaksi berhasil dibuat
    public Cursor Cek_Table_Transaksi(String param_id){
        String sql ="SELECT jenis FROM "+table_transaksi+" WHERE id_transaksi ='"+param_id+"'";
        Cursor cursor =db.rawQuery(sql,null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
