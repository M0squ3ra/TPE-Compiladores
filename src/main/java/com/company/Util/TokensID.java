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
    public static final int CADENA = 257;
    public static final int IDENTIFICADOR = 258;
    public static final int ASIGNACION = 259;
    public static final int CTE = 260;
    public static final int ERROR = 261;

    private static Map<String,Integer> palabrasReservadas = new HashMap<String, Integer>() {{
        put("INT",262);
        put("IF",263);
        put("THEN",264);
        put("ELSE",265);
        put("ENDIF",266);
        put("PRINT",267);
        put("FUNC",268);
        put("RETURN",269);
        put("BEGIN",270);
        put("END",271);
        put("BREAK",272);  
        put("SINGLE",273);
        put("REPEAT",274);
        put("PRE",275);
    }};
    public static final int OR = 276;
    public static final int AND = 277;
    public static final int MAYOR_IGUAL = 278;
    public static final int MENOR_IGUAL = 279;
    public static final int IGUALDAD = 280;
    public static final int DIFERENTE = 281;

    public static Integer getTokenPalabraReservada(String s){
        return palabrasReservadas.get(s);
    }


}
