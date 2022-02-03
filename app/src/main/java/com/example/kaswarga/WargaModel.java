package com.example.kaswarga;

public class WargaModel {

    private String id,nama,no_telp;

    WargaModel(String id, String nama, String no){
        this.id      = id;
        this.nama    =  nama;
        this.no_telp = no;

    }
    public String getId() {

        return id;
    }

    public String getNama() {

        return nama;
    }

    public String getNo_telp() {

        return no_telp;
    }

}
