package com.example.managestudent_android_sqllite.models;

public class Product {
    private String ma;
    private String nameProduct;
    private int soLuong;

    public Product(String ma, String nameProduct, int soLuong) {
        this.ma = ma;
        this.nameProduct = nameProduct;
        this.soLuong = soLuong;
    }

    public Product() {
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ma=" + ma +
                ", nameProduct='" + nameProduct + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }
}
