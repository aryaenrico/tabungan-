package com.example.kaswarga;

public class Table_rekening_model {
    String id;
   int saldo;

    Table_rekening_model(String id ,int saldo){
        this.id =id;
        this.saldo =saldo;
    }

    public String getId() {
        return id;
    }

    public int getSaldo() {
        return saldo;
    }
}
