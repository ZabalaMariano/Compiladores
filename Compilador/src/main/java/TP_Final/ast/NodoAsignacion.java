package TP_Final.ast;

import java.util.Arrays;
import TP_Final.compilador.Assembler;

public class NodoAsignacion extends NodoSentencia {
	private static int counter = 0;
	
    private final NodoIdentificador identificador;
    private final NodoExpresion expresion;

    public NodoAsignacion(NodoIdentificador identificador, NodoExpresion expresion) {
        super(":=");
        this.identificador = identificador;
        this.expresion = expresion;
    }

    @Override
	public String graficar(String idPadre) {
        final String miId = this.getIdNodo();
        return super.graficar(idPadre) +
                identificador.graficar(miId) +
                expresion.graficar(miId);
    }
    
    @Override
    public void generarAssembler() {
    	String constanteString = ((Nodo) this.expresion).getDescripcionNodo();
    	expresion.generarAssembler();

    	if(constanteString.contains("CTE_STR:") || constanteString.contains("ID:")) {// "CTE_STR:" o "ID:"

        	counter++;
    		String str = "";
    		
    		if(constanteString.contains("CTE_STR:")) {
	        	str = "_" + constanteString.substring(11, constanteString.length()-2);
	        	str = str.replaceAll(" ", "_");
    		} else {
	        	str = constanteString.substring(4, constanteString.length());
    		}
    		
        	Assembler.escribirAssembler(
    			Arrays.asList(
    					"MOV SI, OFFSET " + str,
    					"MOV DI, OFFSET " + identificador.getVariable(),
    					"LOOP"+counter+":",
    					    "\tMOV AX, [SI]",
    					    "\tCMP AL, '$'",
    					    "\tJE EXIT"+counter,
    					    "\tPUSH [SI]",
    					    "\tINC SI",
    					    "\tPOP DX",
    					    "\tXOR DH, DH",
    					    "\tMOV [DI], DX",
    					    "\tINC DI",
    					    "\tJMP LOOP"+counter,
    					"EXIT"+counter+":",
    					"MOV [DI],'$'"
    			), 
        		null, 
        		true
        	);
        	
    	} else {// "CTE_E" o CTE_R"
    		Assembler.escribirAssembler(
				Arrays.asList(
					"fstp " + 
					identificador.getVariable()
				),
				null, 
				true
			);
    	}
    }
    
    public NodoExpresion getNodoExpresion()
    {
    	return this.expresion;
    }
}
