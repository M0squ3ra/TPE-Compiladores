package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.company.Analizadores.Lexico;
import com.company.Util.Error;
import com.company.Util.ParTokenLexema;


public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader r = new BufferedReader(new FileReader("src/main/resources/programa.txt"));
        
        Lexico.getInstance().setData(r);

        System.out.println("Tokens");
        for(ParTokenLexema p: Lexico.getInstance().getTokens())
            System.out.println(p.toString());
        
        System.out.println("\nErrores");
        for(Error e: Lexico.getInstance().getErrores())
            System.out.println(e.toString());
    }
}
