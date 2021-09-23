package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.company.Analizadores.Lexico;
import com.company.Util.Error;


public class Main {

    public static void main(String[] args) throws IOException{

        
        Lexico lexico = Lexico.getInstance();
        lexico.setData(Main.leerArchivo("src/main/resources/programa.txt"));
        // lexico.setData(Main.leerArchivo(args[0]));

        System.out.println("Tokens");
        Integer token = 1;

        while(token != 0){
            token = lexico.yylex();
            System.out.println(token);
        }
        
        System.out.println("\nErrores Lexicos");
        for(Error e: Lexico.getInstance().getErroresLexicos())
            System.out.println(e.toString());
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
