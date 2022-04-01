package TP_Final.ast;

import java.util.List;

public class NodoElse extends Nodo {
	private final List<NodoSentencia> sentencias;
	
	public NodoElse(List<NodoSentencia> ls) {
		super("Else");
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
