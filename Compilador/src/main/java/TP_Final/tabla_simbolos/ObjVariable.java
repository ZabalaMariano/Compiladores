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
public class ObjVariable extends ObjetoTabla{

    public ObjVariable (String nombre, String tipo) {
        this.nombre = nombre;
        this.token = "ID";
        this.tipo = tipo;
    }
    
    public ObjVariable() { }
    
    /* getters */
    public String getNombre() {
        return nombre;
    }
    
    public String getToken() {
        return token;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    
    /* setters */
   /* public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }*/
    
}
