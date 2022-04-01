/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_Final.compilador;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import TP_Final.ast.NodoPrograma;
import TP_Final.tabla_simbolos.TablaSimbolos;
import TP_Final.observable.Observable;
import TP_Final.observable.Observer;

/**
 *
 * @author Ledesma Damian, Zabala Mariano
 */
public class Main implements Observable {

	private String error = "";
	
	private ArrayList<Observer> observadores = new ArrayList<Observer>();
	
	private final static Main instance = new Main();
    public static Main getInstance() {
        return instance;
    }
    
    //Metodos Observable
  	@Override
  	public void addObserver(Object o) {
  		if (o instanceof Observer)
  		{
  			Observer ob = (Observer) o;
  			observadores.add(ob);
  		}
  	}

  	@Override
  	public void notifyObserver(int op, String log) {
  		for (Observer o: observadores)
  		{
  			//o.update(this, op, log);
  			o.update(op, log);
  		}
  	}
 
    public Main(){}
    
    private parser parseCodigo(String codigo) {
    	Reader targetReader = new StringReader(codigo);
        try {
			
	    	@SuppressWarnings("deprecation")
	    	parser sintactico = new parser (new Lexico(targetReader));
	    	try {
				sintactico.parse();
				return sintactico;
			} catch (Exception e) {
				this.notifyObserver(0, sintactico.listaReglas + sintactico.error_sintaxis);
				e.printStackTrace();
			}
	    	
	    	targetReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public void verTS(String codigo) {
    	parser sintactico = this.parseCodigo(codigo);
    	this.notifyObserver(0, sintactico.tablaSimbolos.getTS());
    }
    
    public void verAST(String codigo) {
    	Reader targetReader = new StringReader(codigo);
        parser sintactico= new parser (new Lexico(targetReader));
        NodoPrograma programa;
		try {
			programa = (NodoPrograma) sintactico.parse().value;
			
	    	try {
	            FileWriter archivo = new FileWriter("AST.dot");
	            PrintWriter pw = new PrintWriter(archivo);
	            pw.println(programa.graficar());       
	            archivo.close();
	            
		    	String cmd = "dot -Tpng AST.dot -o AST.png";
		        try {
					Runtime.getRuntime().exec(cmd);
					this.notifyObserver(1, ""); // Change image to AST.png
				} catch (IOException e) {
					this.notifyObserver(2, ""); // Change image to noAST.png
					e.printStackTrace();
				}
	        } catch (Exception e) {
	        	this.notifyObserver(2, ""); // Change image to noAST.png
	            System.out.println(e);
	        }
		} catch (Exception e1) {
			this.notifyObserver(2, ""); // Change image to noAST.png
			e1.printStackTrace();
		}
    }
    
    public boolean crearASM(String codigo) {
    	Reader targetReader = new StringReader(codigo);
        parser sintactico= new parser (new Lexico(targetReader));
        NodoPrograma programa;
		try {
			programa = (NodoPrograma) sintactico.parse().value;
			
	    	try {
	        	Assembler.escribirAssembler(Arrays.asList(
	        			"include macros2.asm",
	        			"include number.asm",
	        			"",
						".MODEL\tLARGE",
						".386",
						".STACK 200h"
						), null, false);
				
				TablaSimbolos.generarCodigoASM(null);
				
				Assembler.escribirAssembler(Arrays.asList(
						".CODE",
						"",
						"START:",
						"mov ax, @DATA",
						"mov ds, ax",
						"mov es, ax"
						), null, true);
				
				programa.generarAssembler();
				
				Assembler.escribirAssembler(Arrays.asList(
						"mov ax, 4C00h",
						"int 21h",
						"END START"
						), null, true);
				
				return true;
	        } catch (Exception e) {	       
	        	error = sintactico.listaReglas + sintactico.error_sintaxis;
	        	System.err.println(e);
	        }
			
		} catch (Exception e1) {
			error = sintactico.listaReglas + sintactico.error_sintaxis;
			System.err.println(e1);
			//e1.printStackTrace();
		}    	
		return false;
    }
    
    public void verASM(String codigo) {
    	if(this.crearASM(codigo)) {
        	this.notifyObserver(3, ""); // Update CodeArea ASM    		
    	} else {
    		this.notifyObserver(3, error); // Update CodeArea ASM
    	}
    }
    
    public void ejecutarCodigo(String codigo) {
    	if(this.crearASM(codigo)) {
        	this.notifyObserver(4, ""); // Update CodeArea Ejecutar codigo    		
    	} else {
    		this.notifyObserver(4, error); // Update CodeArea Ejecutar codigo
    	}
    }
}