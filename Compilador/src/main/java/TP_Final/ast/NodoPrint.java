package TP_Final.ast;

import java.util.ArrayList;
import java.util.List;

import TP_Final.compilador.Assembler;
import TP_Final.tabla_simbolos.TablaSimbolos;

public class NodoPrint extends NodoSentencia {
    private final NodoExpresion texto;
    
    public NodoPrint(NodoExpresion s) {
        super("print");
        this.texto = s;
    }
    
    @Override
    public String graficar(String idPadre) {
		final String miId = this.getIdNodo();
    	return super.graficar(idPadre) +
    			texto.graficar(miId);
    }
    
    public String getSalida() {
    	NodoConstanteString t = (NodoConstanteString) texto;
    	String aux = t.getDescripcion();
        aux = aux.replace(" ", "_");
        return "_"+aux;
    }
	
	@Override
	public void generarAssembler() {
		List<String> lineas = new ArrayList<String>();
		lineas.add("mov dx, OFFSET " + TablaSimbolos.configurarNombre(getSalida()));
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
