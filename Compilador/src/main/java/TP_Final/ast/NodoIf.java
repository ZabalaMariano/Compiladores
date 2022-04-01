package TP_Final.ast;

import java.util.Arrays;
import java.util.List;

import TP_Final.compilador.Assembler;

public class NodoIf extends NodoSentencia {
    //private final NodoExpresion condicion;
    private static int contadorIf = 0;
    private final NodoCondicion condicion;
    private final NodoThen sentencias;
    private final NodoElse sentencias2;

    public NodoIf(NodoCondicion c, NodoThen ls, NodoElse ls2) { //NodoExpresion c, NodoThen ls, NodoElse ls2) {
        //super("if");
        super("IF"+ "_Nro" + ++contadorIf);
        this.condicion = c;
        this.sentencias = ls;
        this.sentencias2 = ls2;
    }

    @Override
	public String graficar(String idPadre) {
        final String miId = this.getIdNodo();
        if (sentencias2!=null)
        {
        return super.graficar(idPadre) +
                condicion.graficar(miId) +
                sentencias.graficar(miId) +
                sentencias2.graficar(miId);
        }
        else
        {
        	return super.graficar(idPadre) +
                    condicion.graficar(miId) +
                    sentencias.graficar(miId);
        }
    }
    
    @Override
	public void generarAssembler() {
		String elseTag = null;
		
		if (sentencias2 != null)
			elseTag = getDescripcionNodo() + "ELSE";
		
		String endTag = getDescripcionNodo() + "END";
		
		if (elseTag==null)
			condicion.generarAssembler(endTag);
		else
			condicion.generarAssembler(elseTag);
		
		List<NodoSentencia> sentenciasThen = sentencias.getSentencias();
		for (NodoSentencia s : sentenciasThen) {
			s.generarAssembler();
		}
		
		Assembler.escribirAssembler(Arrays.asList("jmp " + endTag), null, true);
		
		if (elseTag != null) {
			Assembler.escribirAssembler(Arrays.asList(elseTag + ":"), null, true);
			List<NodoSentencia> sentenciasElse = sentencias2.getSentencias();
			for (NodoSentencia s : sentenciasElse) {
				s.generarAssembler();
			}
		}
		
		Assembler.escribirAssembler(Arrays.asList(endTag + ":"), null, true);
	}

}
