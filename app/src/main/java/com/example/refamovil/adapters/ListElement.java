package com.example.refamovil.adapters;


public class ListElement {

    //    public String color;
    public String nombreProducto;
    public String precio;
    public String codigoProducto;

    public ListElement(String nombreProducto, String precio, String codigoProducto) {
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
}
