/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_Final.tabla_simbolos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TP_Final.compilador.Assembler;

/**
 *
 * @author Ledesma Damian, Zabala Mariano
 */
public class TablaSimbolos {
    static ArrayList <ObjCteInt> objetosTabla_int; /* arrayList que almacena ObjCteInt */
    static ArrayList <ObjCteString> objetosTabla_string; /* arrayList que almacena ObjCteString */
    static ArrayList <ObjCteReal> objetosTabla_real; /* arrayList que almacena ObjCteReal */
    public static ArrayList <ObjVariable> objetosTabla_var;
    
    static ArrayList <String> identificadoresUsados; /* me guardo los id que voy recibiendo, si viene un id y está aca -> error! */
    
    public TablaSimbolos ( ) {
        objetosTabla_int = new ArrayList<ObjCteInt> ();
        objetosTabla_string = new ArrayList<ObjCteString> ();
        objetosTabla_real = new ArrayList<ObjCteReal> ();
        objetosTabla_var = new ArrayList<ObjVariable> ();
        
        identificadoresUsados = new ArrayList<String> ();
    }
    
    
    /* metodo para agregar objetos a la tabla */
    public static void agregarObjeto (String nombre, String token, boolean esVariable) {
    
        /* lo buscamos, si no esta -> lo agregamos a la tabla */
        
        if (identificadoresUsados.contains(nombre)){ 
            if (esVariable) /* vemos si es variable, para lanzar error, si leemos otro 5 no lanzamos error pero tampoco lo agregamos a la tabla*/
                throw new RuntimeException("ERROR variable  '"+ nombre +"'  declarada mas de 1 vez");
        }else{
            
            identificadoresUsados.add(nombre);
            
            if (esVariable) {
           
                ObjVariable objT = TablaSimbolos.buscarVar(nombre);
                if (objT == null) {
                    ObjVariable objVar = new ObjVariable(nombre, token);
                    objetosTabla_var.add(objVar);
                }
            } else {
                ObjCteInt objT = null;
                ObjCteString objT2;
                ObjCteReal objT3;
                    
            
            /* busqueda del objeto en tabla */
            switch (token){  
                case "CTE_E":
                    objT = TablaSimbolos.buscarCteInt(nombre); 
                break;

                case "CTE_STR":
                    objT2 = TablaSimbolos.buscarCteString(nombre);
                break;

                case "CTE_F":
                    objT3 = TablaSimbolos.buscarCteReal(nombre);
                break;
           
            } 
           

            if (objT == null) { /* agregación del objeto a la tabla */

                if ("CTE_E".equals(token)) {
                    ObjCteInt objInt = new ObjCteInt(nombre, token);
                    objetosTabla_int.add( objInt );    
                }
                if ("CTE_STR".equals(token)) {
                    ObjCteString objString = new ObjCteString(nombre, token);
                    objetosTabla_string.add( objString );
                }
                if ("CTE_F".equals(token)) {
                    ObjCteReal objReal = new ObjCteReal(nombre,token);
                    objetosTabla_real.add( objReal );
                }
            } else {
                System.out.println("El simbolo: " + objT.nombre + " ya existe en la tabla [lo salteamos] ");
            }
                
          }
        }
    }
    
    /* Setear valores a constantes en la tabla */
    /*public void setValor_ObjInt ( String nombre, int valor) {
        (this.buscarCteInt(nombre)).setValor(valor);
    }
    
    public void setValor_ObjString ( String nombre, String valor ) {
        (this.buscarCteString(nombre)).setValor(valor);
    }
    
    public void setValor_ObjReal ( String nombre, float valor ) {
        (this.buscarCteReal(nombre)).setValor(valor);
    }*/


 /* buscar un objeto dentro de la tabla de simbolos */
    public static ObjCteInt buscarCteInt (String nombre) {


        ObjCteInt objT = new ObjCteInt();
        for (int i=0;i<objetosTabla_int.size();i++) {
            objT = objetosTabla_int.get(i);
            if (nombre.equals(objT.getNombre()))
                return objT;
        }
       return null;
    }
    
    public static ObjCteString buscarCteString (String nombre) {
        
        ObjCteString objT2 = new ObjCteString();
        for (int i=0;i<objetosTabla_string.size();i++){
            objT2 = (ObjCteString) objetosTabla_string.get(i);
            if (nombre.equals(objT2.getNombre()))
                return objT2;
        }
       return null;
    }
    
    public static ObjCteReal buscarCteReal (String nombre) {
        
        ObjCteReal objT3 = new ObjCteReal();
         for (int i=0; i<objetosTabla_real.size();i++){
             objT3 = (ObjCteReal) objetosTabla_real.get(i);
             if (nombre.equals(objT3.getNombre()))
                 return objT3;
        }
       return null; 
    }
    
    public static ObjVariable buscarVar (String nombre) {
        
        ObjVariable objT4 = new ObjVariable();
        for ( int i=0; i<objetosTabla_var.size(); i++) {
            objT4 = (ObjVariable) objetosTabla_var.get(i);
            if (nombre.equals(objT4.getNombre()))
                return objT4;
        }
        return null;
    }
    
    public String buscarTipoVar (String nombre) {
        for ( int i=0; i<objetosTabla_var.size(); i++) {
            if (nombre.equals(objetosTabla_var.get(i).getNombre()))
                return objetosTabla_var.get(i).getTipo();
        }
        return null;
    }

    /* imprimimos en pantalla la tabla de simbolos */     
    public String getTS () {
    	String ts = "";
        ts = ts + String.format("%1$31s %2$10s %3$10s %4$30s %5$10s", "Nombre", "Token", "Tipo", "Valor", "Longitud");
        ts = ts + "\n";
        
        for (ObjCteInt i : objetosTabla_int){
            ts = ts + String.format("%1$31s %2$10s %3$10s %4$30s %5$10s", i.getNombre(), i.getToken(), "---", i.getValor(), "---");
            ts = ts + "\n";
        }
        
        for (ObjCteString s : objetosTabla_string) {
        	ts = ts + String.format("%1$31s %2$10s %3$10s %4$30s %5$10s", s.getNombre(), s.getToken(), "---", s.getValor(), s.getLongitud());
            ts = ts + "\n";     
        }
        
        for (ObjCteReal r : objetosTabla_real) {
        	ts = ts + String.format("%1$31s %2$10s %3$10s %4$30s %5$10s", r.getNombre(), r.getToken(), "---", r.getValor(), "---");
            ts = ts + "\n";
        }
        
        for (ObjVariable ov : objetosTabla_var) {
        	ts = ts + String.format("%1$31s %2$10s %3$10s %4$30s %5$10s", ov.getNombre(), ov.getToken(), ov.getTipo(), "---", "---");
            ts = ts + "\n";
        }
        
        return ts;
    }
    
    /* guardamos la tabla de archivos en ts.txt */
    public void escribirTabla () throws IOException{
        
        File archivo = new File("ts.txt");
        BufferedWriter bw;
        
        bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(getTS());
            
        bw.close();
        
        //#######################//
        
        archivo = new File("readFile.txt");
        
        String no_aplica = " - - - ";
        
        bw = new BufferedWriter(new FileWriter(archivo));
        bw.write("NOMBRE|TOKEN|TIPO|VALOR|LONGITUD"); bw.newLine();
        
        for (ObjCteInt i : objetosTabla_int){
        	bw.write(String.format("%s|%s|%s|%s|%s", i.getNombre(), i.getToken(), no_aplica, i.getValor(), no_aplica));
            bw.newLine();
        }
        
        for (ObjCteString i : objetosTabla_string){
        	bw.write(String.format("%s|%s|%s|%s|%s", i.getNombre(), i.getToken(),no_aplica, i.getValor(), i.getLongitud()));
            bw.newLine();
        }
        
        for (ObjCteReal i : objetosTabla_real){
        	bw.write(String.format("%s|%s|%s|%s|%s", i.getNombre(), i.getToken(),no_aplica, i.getValor(), no_aplica));
            bw.newLine();
        }
        
        for (ObjVariable i : objetosTabla_var){
            bw.write(String.format("%s|%s|%s|%s|%s", i.getNombre(), i.getToken(), i.getTipo(), no_aplica, no_aplica));
            bw.newLine();
        }
            
        bw.close();
    }
    
    // Nombre de archivo
 	public static final String FILENAME = "readFile.txt";
    
    // Columnas de la tabla de simbolos
    public enum Columna {
    	NOMBRE(1),
    	TOKEN(2),
    	TIPO(3),
    	VALOR(4),
    	LONGITUD(5);

    	private int numCol;

    	private Columna(int numCol) {
    		this.numCol = numCol;
    	}

    	public int getNumCol() {
    		return numCol;
    	}

    	@Override
    	public String toString() {
    		return this.name();
    	}
    }
    
    public static List<Map<Columna, String>> readFile(String filename) {
		if (filename == null || "".equals(filename)) filename = FILENAME;
		List<Map<Columna, String>> lines = new ArrayList<Map<Columna, String>>();
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			in.readLine(); // Cabeceras
			String linea;
			while ((linea = in.readLine()) != null) {
				String[] splitLine = linea.split("\\|");
				Map<Columna, String> line = new HashMap<Columna, String>();
				for (Columna c : Columna.values()) {
					line.put(c, splitLine[c.getNumCol()-1].trim());
				}
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		System.out.println(lines);
		return lines;
	}
    
    public static String generarCodigoASM(String filename) {
		List<Map<Columna, String>> lines = readFile(filename);
		List<String> lineas = new ArrayList<String>();
		lineas.add(".DATA");
		for (Map<Columna, String> f : lines) {
			String nuevoNombre = configurarNombre(f.get(Columna.NOMBRE));
			switch(f.get(Columna.TOKEN)) {
			case "CTE_E":
			case "CTE_F":
				lineas.add("\t" + nuevoNombre + "\tdd\t" + f.get(Columna.VALOR));
				break;
			case "CTE_STR":
				lineas.add("\t" + nuevoNombre + "\tdb\t" + configurarString(f.get(Columna.VALOR)) + ",'$'");
				break;
			default://TOKEN == ID
				if(f.get(Columna.TIPO).equals("string")) {//es STRING
					lineas.add("\t" + nuevoNombre + "\tdb\t?,'$'");	
				} else {//es INT o REAL
					lineas.add("\t" + nuevoNombre + "\tdd\t?");
				}				
				break;
			}
		}
		return Assembler.escribirAssembler(lineas, null, true);
	}
    
    public static String configurarNombre(String nombre) {
                System.out.println("Nombre: " + nombre);
                String nuevoNombre = "";
		for (int i = 0; i < nombre.length(); i++) {
			String a = nombre.substring(i, i+1);
			if (!a.matches("[A-Za-z0-9@_]"))
				a = "%x" + Integer.toHexString((int) a.charAt(0));
			nuevoNombre += a;
		}
                System.out.println(nuevoNombre);
		return nuevoNombre;
	}
	
    public static String configurarString(String vString) {
		if (vString.contains("\"")) return "'" + vString + "'";
		return "\"" + vString + "\"";
    }
}