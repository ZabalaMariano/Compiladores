package TP_Final.ast;

import java.util.List;

public class NodoThen extends Nodo {
	private final List<NodoSentencia> sentencias;
	
	public NodoThen(List<NodoSentencia> ls) {
		super("Then");
		this.sentencias = ls;
	}
	
	 @Override
	public String graficar(String idPadre) {
		 StringBuilder resultado = new StringBuilder();
		 
		 resultado.append(super.graficar(idPadre));
		 final String miId = super.getIdNodo();
		 
		 for (NodoSentencia sentencia : this.sentencias) {
			 resultado.append(sentencia.graficar(miId));
		 }
		return resultado.toString();
	 }

	public List<NodoSentencia> getSentencias() {
		return sentencias;
	}

}
