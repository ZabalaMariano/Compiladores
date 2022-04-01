package TP_Final.compilador;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class Assembler {
	public static final String FILENAME = "Final.asm";

	public static String escribirAssembler(List<String> lineas, String filename, Boolean append) {
		String codigoASM = "";
		
		if (filename == null || "".equals(filename)) filename = FILENAME;
		if (append == null) append = true;
		try (PrintWriter out = new PrintWriter(new FileWriter(filename, append))) {
			for (String linea: lineas) {
				out.println(linea);
				codigoASM += linea + "\n";
			}
			out.println();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error.";
		}

		return codigoASM; 
	}

}
