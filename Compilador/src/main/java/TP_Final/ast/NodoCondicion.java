package TP_Final.ast;

import java.util.ArrayList;
import java.util.List;


import TP_Final.compilador.Assembler;

public class NodoCondicion extends NodoExpresion {
    private final NodoExpresion izquierda;
    private final NodoExpresion derecha;

    public NodoCondicion(String nombre, NodoExpresion izquierda, NodoExpresion derecha) {
        super(nombre);
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
	public String graficar(String idPadre) {
        final String miId = this.getIdNodo();
        return super.graficar(idPadre) +
                izquierda.graficar(miId) +
                derecha.graficar(miId);
    }
    
    private String getOperador(String descripcionNodo) {
    	String rta = "";
    	switch(descripcionNodo)
    	{
    	case "==":
    		rta = "jne";
    		break;
    	case "!=":
    		rta = "je";
    		break;
    	case ">":
    		rta = "jna";
    		break;
    	case "<":
    		rta = "jnb";
    		break;
    	case ">=":
    		rta = "jnae";
    		break;
    	case "<=":
    		rta = "jnbe";
    		break;
    	}
    	return rta;
	}
    
    public void generarAssembler(String endTag) {
    	List<String> lineas = new ArrayList<String>();
    	if (izquierda instanceof NodoHoja) {
    		derecha.generarAssembler();
			izquierda.generarAssembler();
		} else {
			izquierda.generarAssembler();
			derecha.generarAssembler();
			lineas.add("fxch");
		}
    	lineas.add("fcomp");
    	lineas.add("fstsw ax");
    	lineas.add("sahf");
    	lineas.add(getOperador(getDescripcionNodo()) + " " + endTag);
    	Assembler.escribirAssembler(lineas, null, true);
    }

}
