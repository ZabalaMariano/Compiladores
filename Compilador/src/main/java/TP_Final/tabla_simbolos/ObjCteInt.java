/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_Final.tabla_simbolos;

/**
 *
 * @author Ledesma Damian, Zabala Mariano
 */
public class ObjCteInt extends ObjetoTabla{
    String valor;
    
    public ObjCteInt (String nombre, String token) {
        this.nombre = "_"+nombre;
        this.token = token;
        nombre = nombre.replace("@", "");
        this.valor = nombre;
    }
    
    public ObjCteInt() { }
    
    /* getters */
    public String getNombre() {
        return nombre;
    }
    
    public String getToken() {
        return token;
    }
    
    public String getValor() {
        return valor;
    }
    
    /* setters */
    /*public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setToken (String token) {
        this.token = token;
    } 

    public void setValor(int valor) {
        this.valor = valor;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }*/
    /*public String getTipo() {
        return tipo;
    }*/
    
}
