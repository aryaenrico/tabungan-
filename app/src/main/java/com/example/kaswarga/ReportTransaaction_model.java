package com.example.kaswarga;

public class ReportTransaaction_model {
    private String nama_user,jenis_transaksi,tanggal;
    public int transaksi;

    public String getNama_user() {
        return nama_user;
    }

    public String getjenis_transaksi() {
        return jenis_transaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getTransaksi() {
        return transaksi;
    }

    ReportTransaaction_model(String id_user, String id_transaksi , String tanggal, int total ){
        this.nama_user =id_user;
        this.jenis_transaksi =id_transaksi;
        this.tanggal =tanggal;
        this.transaksi =total;
    }
}
