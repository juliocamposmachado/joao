package com.example.lavarapido;

public class Fechamento {
    private int id;
    private String data;
    private double total;

    public Fechamento() {
    }

    public Fechamento(String data, double total) {
        this.data = data;
        this.total = total;
    }

    public Fechamento(int id, String data, double total) {
        this.id = id;
        this.data = data;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return data + " - R$ " + String.format("%.2f", total);
    }
}
