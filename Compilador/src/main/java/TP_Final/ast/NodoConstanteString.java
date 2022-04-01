package TP_Final.ast;

import java.util.Arrays;

import TP_Final.compilador.Assembler;

public class NodoConstanteString extends NodoExpresion {
    private final String valor;

    public NodoConstanteString(String valor) {
    	super("CTE_STR");
    	String v = valor.replace("\"", "\\\"");
        this.valor = v;
    }

    @Override
    public String getDescripcionNodo() {
        return "CTE_STR: " + valor;
    }
    
    public String getDescripcion() {
    	String s = valor;
    	s = s.replace("\\", "");
    	s = s.replace("\"", "");
    	return s;
    }

}
