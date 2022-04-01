package TP_Final.ast;

import java.util.ArrayList;
import java.util.List;

import TP_Final.compilador.Assembler;
import TP_Final.tabla_simbolos.TablaSimbolos;

public class NodoPrintIdentificador extends NodoSentencia {
    private final NodoExpresion texto;
    
    public NodoPrintIdentificador(NodoExpresion s) {
        super("print");
        this.texto = s;
    }
    
    @Override
    public String graficar(String idPadre) {
		final String miId = this.getIdNodo();
    	return super.graficar(idPadre) +
    			texto.graficar(miId);
    }
    
    public String getID() {
    	NodoIdentificador t = (NodoIdentificador) texto;
        return t.getVariable();
    }
	
	@Override
	public void generarAssembler() {
		List<String> lineas = new ArrayList<String>();
		lineas.add("mov dx, OFFSET " + this.getID());
		lineas.add("mov ah, 9");
		lineas.add("int 21h");
		lineas.add("mov ah, 2");
		lineas.add("mov dl, 13");
		lineas.add("int 21h");
		lineas.add("mov dl, 10");
		lineas.add("int 21h");
		Assembler.escribirAssembler(lineas, null, true);
	}

}

