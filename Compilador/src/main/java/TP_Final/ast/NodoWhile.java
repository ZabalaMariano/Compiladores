package TP_Final.ast;

import java.util.Arrays;

import java.util.List;
import TP_Final.compilador.Assembler;

public class NodoWhile extends NodoSentencia {
    //private final NodoExpresion condicion;
    private static int contadorWhile = 0;
    private final NodoCondicion condicion;
    private final NodoDo sentencias;

    public NodoWhile(NodoCondicion c, NodoDo ls) {//NodoExpresion c, NodoDo ls) {
        super("WHILE"+ "_Nro" + ++contadorWhile);
        this.condicion = c;
        this.sentencias = ls;
    }

    @Override
	public String graficar(String idPadre) {
        final String miId = this.getIdNodo();
        return super.graficar(idPadre) +
                condicion.graficar(miId) +
                sentencias.graficar(miId);
    }

    public void generarAssembler() {
		String startTag = getDescripcionNodo() + "START";
		String endTag = getDescripcionNodo() + "END";
		
		Assembler.escribirAssembler(Arrays.asList(startTag + ":"), null, true);
		
		condicion.generarAssembler(endTag);
		
		List<NodoSentencia> sentenciasDo = sentencias.getSentencias();
		for (NodoSentencia s : sentenciasDo) {
			s.generarAssembler();
		}
		
		Assembler.escribirAssembler(Arrays.asList(
				"jmp " + startTag,
				endTag + ":"
				), null, true);
	}
}
