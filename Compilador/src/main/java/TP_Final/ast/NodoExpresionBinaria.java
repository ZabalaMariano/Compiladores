package TP_Final.ast;

import java.util.ArrayList;
import java.util.List;

import TP_Final.compilador.Assembler;

public class NodoExpresionBinaria extends NodoExpresion {
    private final NodoExpresion izquierda;
    private final NodoExpresion derecha;
    private final String n;

    public NodoExpresionBinaria(String nombre, NodoExpresion izquierda, NodoExpresion derecha) {
        super(nombre);
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.n = nombre;
    }

    @Override
	public String graficar(String idPadre) {
        final String miId = this.getIdNodo();
        if (this.izquierda != null && this.derecha != null)
        {
        return super.graficar(idPadre) +
                izquierda.graficar(miId) +
                derecha.graficar(miId);
        }
        else
        	return super.graficar(idPadre);
    }
    
    public NodoExpresionBinaria copy() {
    	NodoExpresionBinaria left = null;
    	NodoExpresionBinaria right = null;
    	String naux = n;
    	
        if (this.izquierda != null) {
        	if (this.izquierda instanceof NodoConstanteReal)//Hoja
        		left = new NodoExpresionBinaria(((NodoConstanteReal) this.izquierda).getDescripcionNodo(),null,null);
        	else{
        		if (this.izquierda instanceof NodoConstante)//Hoja
        			left = new NodoExpresionBinaria(((NodoConstante) this.izquierda).getDescripcionNodo(),null,null);
        		else
        		{
        			if (this.izquierda instanceof NodoIdentificador)//Hoja
            			left = new NodoExpresionBinaria(((NodoIdentificador) this.izquierda).getDescripcionNodo(),null,null);
        			else
        			{
        				if (this.izquierda instanceof NodoExpresionBinaria)
        					left = ((NodoExpresionBinaria) this.izquierda).copy();
        			}
        		}
            		
        	}
        }
        
        if (this.derecha != null) {
        	if (this.derecha instanceof NodoConstanteReal)//Hoja
        		right = new NodoExpresionBinaria(((NodoConstante) this.derecha).getDescripcionNodo(),null,null);
        	else{
        		if (this.derecha instanceof NodoConstante)//Hoja
        			right = new NodoExpresionBinaria(((NodoConstante) this.derecha).getDescripcionNodo(),null,null);
        		else
        		{
        			if (this.derecha instanceof NodoIdentificador)//Hoja
            			right = new NodoExpresionBinaria(((NodoIdentificador) this.derecha).getDescripcionNodo(),null,null);
        			else
        			{
        				if (this.derecha instanceof NodoExpresionBinaria)
        					right = ((NodoExpresionBinaria) this.derecha).copy();
        			}	
        		}
            		
        	}
        }
        else //Constante unica
        {
        	if (this instanceof NodoConstanteReal)
                naux = ((NodoConstanteReal) this).getDescripcionNodo();
        	else{
        		if (this instanceof NodoConstante)
                    naux = ((NodoConstante) this).getDescripcionNodo();
        		else{
            		if (this instanceof NodoIdentificador)
                        naux = ((NodoIdentificador) this).getDescripcionNodo();
            	}
        	}
        }
     
        return new NodoExpresionBinaria(naux, left, right);
    }
    
    private String getOperador(String descripcionNodo) {
    	String rta = "";
    	switch(descripcionNodo)
    	{
    	case "+":
    		rta = "fadd";
    		break;
    	case "-":
    		rta = "fsub";
    		break;
    	case "*":
    		rta = "fmulp";
    		break;
    	case "/":
    		rta = "fdiv";
    		break;
    	}
    	return rta;
    }
    
    @Override
	public void generarAssembler() {
		List<String> lineas = new ArrayList<String>();

		if (izquierda instanceof NodoHoja && !(derecha instanceof NodoHoja)) {
			derecha.generarAssembler();
			izquierda.generarAssembler();
			lineas.add("fxch");
		} 
		else 
		{
			if (izquierda==null && derecha==null)
			{
				if (this.n.contains("CTE_E")) { 
					NodoConstante newNodo = new NodoConstante(this.n.replace("CTE_E: ", ""));
					newNodo.generarAssembler();
				}
				else {
					if (this.n.contains("CTE_F")) { 
						NodoConstanteReal newNodo = new NodoConstanteReal(this.n.replace("CTE_F: ", ""));
						newNodo.generarAssembler();
					}
					else
					{ 
						NodoIdentificador newNodo = new NodoIdentificador(this.n.replace("ID: ", ""));
						newNodo.generarAssembler();
					}
				}
			}
			else
			{
				izquierda.generarAssembler();
				derecha.generarAssembler();
			}
		}
		lineas.add(getOperador(n));
		Assembler.escribirAssembler(lineas, null, true);
	}
}
