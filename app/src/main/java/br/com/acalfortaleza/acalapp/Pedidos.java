package br.com.acalfortaleza.acalapp;

import java.io.Serializable;

/**
 * Created by Administrador on 27/03/2017.
 */
public class Pedidos implements Serializable {

    public int pedido;
    public String status;


    public Pedidos() {


    }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
