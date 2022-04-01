package TP_Final.ast;

import java.util.Arrays;

import TP_Final.compilador.Assembler;

public class NodoConstante extends NodoExpresionBinaria implements NodoHoja{
    private final String valor;

    public NodoConstante(String valor) {
        super("CTE_E",null,null);
        this.valor = valor;
    }

    @Override
    public String getDescripcionNodo() {
        return "CTE_E: " + valor;
    }
    
    @Override
    public void generarAssembler() {
    	Assembler.escribirAssembler(Arrays.asList("fild " + "_" + valor), null, true);
    }
}

