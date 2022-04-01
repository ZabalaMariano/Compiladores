package TP_Final.ast;

import java.util.Arrays;

import TP_Final.compilador.Assembler;
import TP_Final.tabla_simbolos.TablaSimbolos;

public class NodoConstanteReal extends NodoExpresionBinaria implements NodoHoja{
    private final String valor;

    public NodoConstanteReal(String valor) {
        super("CTE_F",null,null);
        this.valor = valor;
    }

    @Override
    public String getDescripcionNodo() {
        return "CTE_F: " + valor;
    }
    
    @Override
    public void generarAssembler() {
    	
        String s = "_" + valor;
        s = TablaSimbolos.configurarNombre( s );
        Assembler.escribirAssembler(Arrays.asList("fld " + s), null, true);
    }
}
