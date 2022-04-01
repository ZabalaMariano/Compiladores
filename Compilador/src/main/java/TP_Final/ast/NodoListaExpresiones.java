package TP_Final.ast;

import java.util.List;

public class NodoListaExpresiones extends NodoExpresion{
	private final List<NodoExpresion> lista;
	
	public NodoListaExpresiones(List<NodoExpresion> l) {
		super("lista");
		this.lista = l;
	}
	
	@Override
	public String graficar(String idPadre) {
		
		StringBuilder resultado = new StringBuilder();

		resultado.append(super.graficar(idPadre));
		final String miId = super.getIdNodo();
		
		//Recorro las expresiones de la lista
		for (NodoExpresion expresion : this.lista) {
			resultado.append(expresion.graficar(miId));
		}
		return resultado.toString();
	}

}
