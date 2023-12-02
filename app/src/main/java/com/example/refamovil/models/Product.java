package com.example.refamovil.models;

public class Product {
    String nameProduct, barCode;
    int price;

    public Product(String nameProduct, String barCode, int price) {
        this.nameProduct = nameProduct;
        this.barCode = barCode;
        this.price = price;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
