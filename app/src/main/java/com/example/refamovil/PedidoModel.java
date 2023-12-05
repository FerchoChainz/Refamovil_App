package com.example.refamovil;

public class PedidoModel {
    private String usuario;
    private String titulo;
    private String descripcion;
    private String total;

    public PedidoModel() {}

    public PedidoModel(String usuario, String titulo, String descripcion, String total) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.total = total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
