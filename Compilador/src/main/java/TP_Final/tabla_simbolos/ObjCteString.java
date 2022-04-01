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
public class ObjCteString extends ObjetoTabla{
    String valor;
    int longitud;
    
    public ObjCteString (String nombre, String token) {
        /* limpiamos las comillas */
       String idstring = nombre.substring(1,nombre.length()-1);
                    
        this.nombre = "_"+idstring;
        this.nombre = this.nombre.replaceAll(" ", "_");
        this.token = token;
        this.valor = idstring;
        this.longitud = idstring.length();
    }
    
    public ObjCteString() { }
    
    
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
    
    public int getLongitud() {
        return longitud;
    }
    
    
    /* setters */
    /*public void setNombre(String nombre) {
       String idstring = nombre.substring(1,nombre.length()-1);
                    
        this.nombre = "_"+idstring;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

    public void setLongitud (int longitud) {
        this.longitud = longitud;
    }

   
    public void setValor(String valor) {
       String valorstring = valor.substring(1,valor.length()-1);
                    
        this.valor = valorstring;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo() {
        return tipo;
    }*/
}
