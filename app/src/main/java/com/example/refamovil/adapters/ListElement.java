package com.example.refamovil.adapters;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ListElement implements Serializable {

    //    public String color;
    public String nombreProducto;
    public String precio;
    public String codigoProducto;

    public ListElement(String nombreProducto, String precio, String codigoProducto) {
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.codigoProducto = codigoProducto;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombreProducto);
        map.put("precio", precio);
        map.put("codigo", codigoProducto);
        return map;
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
