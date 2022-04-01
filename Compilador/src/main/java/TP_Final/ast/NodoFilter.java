package TP_Final.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TP_Final.tabla_simbolos.ObjVariable;
import TP_Final.tabla_simbolos.TablaSimbolos;
import TP_Final.tabla_simbolos.TablaSimbolos.Columna;
import TP_Final.compilador.Assembler;
import java.util.Iterator;

public class NodoFilter extends NodoExpresion{
	//private final NodoAsignacion filterRta;
	//private final NodoAsignacion filterAComparar;
	
	private static int contadorFilter = 0;
    private final NodoIf lista;
    private final NodoAsignacion nodoasignacion;
    
    private String nombreVariable;
    private String tipoVariable;
    private boolean esVariable;
	
	public NodoFilter(NodoAsignacion nodoasignacion, NodoIf nodoIf, List<NodoExpresion> le, String operador) {
		super("Filter"+"_Nro"+ ++contadorFilter);
		this.nodoasignacion = nodoasignacion;
		this.lista = nodoIf;	
		
		TablaSimbolos.agregarObjeto("0","CTE_E",false);
	}
	
	
	
	@Override
	public String graficar(String idPadre) {
		final String miId = this.getIdNodo();
    	return super.graficar(idPadre) +
    			nodoasignacion.graficar(miId) +
    			lista.graficar(miId);
	 }

	@Override
	public void generarAssembler() {
		this.nodoasignacion.generarAssembler();
		lista.generarAssembler();
		Assembler.escribirAssembler(Arrays.asList("fld @c"), null, true);
	}
	
}
