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
import com.company.Util.GeneradorCodigo;
import com.company.Util.Terceto;


public class Main {

    public static void main(String[] args) throws IOException{

        Lexico lexico = Lexico.getInstance();
        lexico.setData(Main.leerArchivo("src/main/resources/programa.txt"));
        // lexico.setData(Main.leerArchivo(args[0]));
        
        Parser parser = new Parser(false);
		parser.yyparse();

        // System.out.println("\n Estructuras detectadas, el numero de linea indica el final de la estructura");
        // System.out.println(" Las estructuras que contienen errores no se muestran");
        // System.out.println("*---------------------------------------------------------------------------*");
        // for(String i: parser.getEstructurasReconocidas()){
        //     System.out.println(i);
        // }

        // // devuelve una lista pero yylex los reconoce a medida que se lo piden
        // System.out.println("\n Tokens Reconocidos (Orden -->)");
        // System.out.println("*------------------------------*");
        // for(Integer i: parser.getTokensReconocidos()){
        //     System.out.print(i + " ");
        // }
        
        System.out.println("\n\n Errores Lexicos");
        System.out.println("*---------------*");
        for(Error e: lexico.getErroresLexicos())
            System.out.println(e.toString());
        
        System.out.println("\n Errores Sintacticos");
        System.out.println("*-------------------*");
        for(Error e: parser.getErroresSintacticos())
            System.out.println(e.toString());

        System.out.println("\n Errores Semanticos");
        System.out.println("*-------------------*");
        for(Error e: parser.getErroresSemanticos())
            System.out.println(e.toString());

        System.out.println("\n Contenido de la tabla de simbolos");
        System.out.println("*---------------------------------*");
        Map<String, Map<String, Object>> tablaSimbolos = lexico.getTablaSimbolos();
        for(String i: tablaSimbolos.keySet()){
            System.out.println("[" + i + "]:");
            Map<String, Object> atributos = tablaSimbolos.get(i);
            for(String j: atributos.keySet())
                System.out.println("    " + j + ": " + atributos.get(j));
        }

        if(!parser.getError()){
            System.out.println("\n Tercetos - [Nro.Terceto][Tipo](Terceto)");
            System.out.println("*-------------------*");
            Map<String,List<Terceto>> listasTercetos = parser.getTercetos();
            for(String s : listasTercetos.keySet()){
                List<Terceto> tercetos = listasTercetos.get(s);
                System.out.println("---" + s + "---");
                for(Terceto t: tercetos)
                    System.out.println("    [" +tercetos.indexOf(t) + "] " + t.toString());
            }
            System.out.println("\n   Codigo generado");
            System.out.println("*-------------------*");
            GeneradorCodigo.setTablaSimbolos(tablaSimbolos);
            GeneradorCodigo.setVariables(parser.getVariablesFunciones());
            GeneradorCodigo.setCadenas(parser.getCadenas());
            GeneradorCodigo.generar(listasTercetos, "Mi_Progama"); // desharcodear id main
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
