package com.company.Util;

import java.util.HashMap;
import java.util.Map;

public class TokensID {
    // public static final
    // los ids nos los va a dar la catedra, dicho por la jefa de catedra
    public static final int FIN_PROGRAMA = 0;
    
    public static final int PARENTESIS_ABRE = 40; 
    public static final int PARENTESIS_CIERRA = 41; 
    public static final int POR = 42; 
    public static final int MAS = 43; 
    public static final int COMA = 44; 
    public static final int MENOS = 45; 
    public static final int DIVIDIR = 47;
    public static final int DOS_PUNTOS = 58;
    public static final int PUNTO_Y_COMA = 59; 
    public static final int MENOR = 60;
    public static final int MAYOR = 62;
    public static final int MAYOR_IGUAL = 257;
    public static final int MENOR_IGUAL = 258;
    public static final int IGUALDAD = 259;
    public static final int DIFERENTE = 260;
    public static final int CADENA = 261;
    public static final int AND = 262;
    public static final int OR = 263;
    public static final int IDENTIFICADOR = 264;
    public static final int ASIGNACION = 265;
    public static final int CTE = 266;
    public static final int ERROR = 267;

    private static Map<String,Integer> palabrasReservadas = new HashMap<String, Integer>() {{
        put("INT",268);
        put("IF",269);
        put("THEN",270);
        put("ELSE",271);
        put("ENDIF",272);
        put("PRINT",273);
        put("FUNC",274);
        put("RETURN",275);
        put("BEGIN",276);
        put("END",277);
        put("BREAK",278);  
        put("SINGLE",279);
        put("REPEAT",280);
        put("PRE",281);
        put("FLOAT",282);
    }};

    public static Integer getTokenPalabraReservada(String s){
        return palabrasReservadas.get(s);
    }


}
