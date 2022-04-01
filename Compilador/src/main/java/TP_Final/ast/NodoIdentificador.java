package TP_Final.ast;

import java.util.Arrays;

import TP_Final.compilador.Assembler;

public class NodoIdentificador extends NodoExpresionBinaria implements NodoHoja{
    private final String identificador;

    public NodoIdentificador(String identificador) {
        super("ID",null,null);
        this.identificador = identificador;
    }

    public NodoIdentificador(NodoIdentificador nodoAComparar) {
		this(nodoAComparar.getVariable());
	}
    
    @Override
    public String getDescripcionNodo() {
        return "ID: " + identificador;
    }
    
    public String getVariable() {
    	return this.identificador;
    }
    
    @Override
    public void generarAssembler() {
    	Assembler.escribirAssembler(Arrays.asList("fld " +identificador), null, true);
    }
}
