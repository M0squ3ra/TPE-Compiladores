package com.company.Util;

import java.util.HashMap;
import java.util.Map;

public class TokensID {
    // public static final
    // los ids nos los va a dar la catedra, dicho por la jefa de catedra
    public static final int PARENTESIS_ABRE = 1; 
    public static final int PARENTESIS_CIERRA = 1; 
    public static final int COMA = 1; 
    public static final int PUNTO_Y_COMA = 1; 
    public static final int MAS = 1; 
    public static final int POR = 1; 
    public static final int MENOS = 1; 
    public static final int DIVIDIR = 1;
    public static final int MENOR = 1;
    public static final int MAYOR = 1;
    public static final int MAYOR_IGUAL = 1;
    public static final int MENOR_IGUAL = 1;
    public static final int IGUAL = 1;
    public static final int DIFERENTE = 1;
    public static final int STRING = 1;
    public static final int AND = 1;
    public static final int OR = 1;
    public static final int IDENTIFICADOR = 1;
    public static final int ASIGNACION = 1;
    public static final int FLOAT = 1;
    public static final int INTEGER = 1;

    private static Map<String,Integer> palabrasReservadas = new HashMap<String, Integer>() {{
        put("SINGLE",1);
        put("REPEAT",1);
        put("INT",1);
        put("IF",1);
        put("THEN",1);
        put("ELSE",1);
        put("ENDIF",1);
        put("PRINT",1);
        put("FUNC",1);
        put("RETURN",1);
        put("BEGIN",1);
        put("END",1);
        put("BREAK",1);  
    }};

    public static Integer getTokenPalabraReservada(String s){
        return palabrasReservadas.get(s);
    }


}
