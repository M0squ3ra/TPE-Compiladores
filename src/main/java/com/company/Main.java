package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.Analizadores.Lexico;
import com.company.Analizadores.Parser;
import com.company.Util.Error;


public class Main {

    public static void main(String[] args) throws IOException{

        Lexico lexico = Lexico.getInstance();
        // lexico.setData(Main.leerArchivo("src/main/resources/programa.txt"));
        lexico.setData(Main.leerArchivo(args[0]));
        
        System.out.println("Estructuras detectadas, el numero de linea indica el final de la estructura");
        System.out.println("Las estructuras que contienen errores no se muestran");
        Parser parser = new Parser(false);
		parser.yyparse();

        // devuelve una lista pero yylex los reconoce a medida que se lo piden
        System.out.println("\nTokens Reconocidos (Orden -->)");
        for(Integer i: parser.getTokensReconocidos()){
            System.out.print(i + " ");
        }
        
        System.out.println("\n\nErrores Lexicos");
        for(Error e: lexico.getErroresLexicos())
            System.out.println(e.toString());
        
        System.out.println("\nErrores Sintacticos");
        for(Error e: parser.getErroresSintacticos())
            System.out.println(e.toString());

        System.out.println("\nContenido de la tabla de simbolos");
        Map<String, Map<String, Object>> tablaSimbolos = lexico.getTablaSimbolos();
        for(String i: tablaSimbolos.keySet()){
            System.out.println(i);
            Map<String, Object> atributos = tablaSimbolos.get(i);
            for(String j: atributos.keySet())
                System.out.println("    " + j + ": " + atributos.get(j));
        }

    }

    public static List<Character> leerArchivo(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        int c = 0;
        List<Character> data = new ArrayList<Character>();

        while(c != -1){
            c = reader.read();
            if(c != -1)
                data.add((char) c);
        }

        reader.close();

        return data;

    } 

}
