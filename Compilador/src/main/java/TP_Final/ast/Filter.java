package TP_Final.ast;

import java.util.ArrayList;
import java.util.List;

public class Filter {

	public static List<NodoSentencia> sentencias(List<NodoExpresion> le, String condicion) {
		List<NodoSentencia> lsglobal = new ArrayList<NodoSentencia>();
		if (le.size()==0)
		{
			NodoIdentificador c = new NodoIdentificador("@c");
			String s = "0";
			NodoConstante nc = new NodoConstante(s);
			NodoAsignacion na = new NodoAsignacion(c, nc);
			List<NodoSentencia> ls = new ArrayList<NodoSentencia>();
			ls.add(na);
			return ls;
		}
		else
		{
			NodoExpresion e = le.get(0);
			NodoExpresionBinaria neb = (NodoExpresionBinaria) e;
			NodoExpresion ne2 = neb.copy();
			le.remove(0);
			
			//Nodo condicion
			NodoIdentificador aux = new NodoIdentificador("@aux");
			NodoCondicion nc = null;
			//NodoMayor nm = new NodoMayor(ne2,aux);
	        switch(condicion) 
	        { 
	            case "Mayor": 
	            	nc = new NodoMayor(ne2,aux); 
	                break; 
	            case "Menor": 
	            	nc = new NodoMenor(ne2,aux); 
	                break; 
	            case "MayorIgual": 
	            	nc = new NodoMayorIgual(ne2,aux); 
	                break;
	            case "MenorIgual": 
	            	nc = new NodoMenorIgual(ne2,aux); 
	                break;
	            case "Igual": 
	            	nc = new NodoIgual(ne2,aux); 
	                break;
	            case "Distinto": 
	            	nc = new NodoDistinto(ne2,aux); 
	                break;
	        }
			//Nodo then
			NodoIdentificador c = new NodoIdentificador("@c");
			NodoSentencia ns = new NodoAsignacion(c, e);
			List<NodoSentencia> ls = new ArrayList<NodoSentencia>();
			ls.add(ns);
			NodoThen nt = new NodoThen(ls);
			
			//Nodo if
			NodoIf nf = new NodoIf(nc,nt,new NodoElse(sentencias(le,condicion)));
			lsglobal.add(nf);
			return lsglobal;
		}
	}

}
