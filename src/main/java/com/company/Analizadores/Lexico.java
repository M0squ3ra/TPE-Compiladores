package com.company.Analizadores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.AccionesSemanticas.AccionSemantica;
import com.company.AccionesSemanticas.AccionSemantica1;
import com.company.AccionesSemanticas.AccionSemantica2;
import com.company.AccionesSemanticas.AccionSemantica3;
import com.company.AccionesSemanticas.AccionSemantica4;
import com.company.AccionesSemanticas.AccionSemantica5;
import com.company.AccionesSemanticas.AccionSemantica6;
import com.company.AccionesSemanticas.AccionSemantica7;
import com.company.AccionesSemanticas.AccionSemantica8;
import com.company.AccionesSemanticas.AccionSemantica9;
import com.company.AccionesSemanticas.AccionSemanticaError;
import com.company.Util.TokensID;
import com.company.Util.Error;


public class Lexico {
    private static Map<Character, Integer> columnaSimbolo;
    private static int[][] matrizTransicion;
    private static AccionSemantica[][] matrizAccionesSemanticas;
    private static Lexico lexico;
    private static int linea;
    private static String buffer;
    public static final int TAMANO_BUFFER = 22;
    private static List<Character> data;
    private static Map<String, Map<String, Object>> tablaSimbolos;
    private static List<Error> errores;
    
    
    public static Lexico getInstance(){
        if(Lexico.lexico == null)
            Lexico.lexico = new Lexico();
        return Lexico.lexico;
    }

    public void setData(List<Character> d) {
        Lexico.data = d;
    }

    private Lexico(){

        Lexico.linea = 1;
        Lexico.buffer = null;
        Lexico.buffer = "";
        Lexico.data = null;

        Lexico.columnaSimbolo = new HashMap<Character, Integer>();
        Lexico.tablaSimbolos = new HashMap<String, Map<String, Object>>();
        Lexico.errores = new ArrayList<Error>();
        
        
        char[] letras = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','t','u','v','w','x','y','z'};
        Lexico.columnaSimbolo.put('s', 0);
        for(char i: letras){
            Lexico.columnaSimbolo.put(i, 0);
            Lexico.columnaSimbolo.put(Character.toUpperCase(i),0);
        }

        char[] digitos = {'0','1','2','3','4','5','6','7','8','9'};
        for(char i: digitos){
            Lexico.columnaSimbolo.put(i, 1);
        }

        Lexico.columnaSimbolo.put('_',2);
        Lexico.columnaSimbolo.put('.', 3);
        Lexico.columnaSimbolo.put('-', 4);
        Lexico.columnaSimbolo.put('+', 5);
        Lexico.columnaSimbolo.put('*', 6);
        Lexico.columnaSimbolo.put('/', 7);
        Lexico.columnaSimbolo.put('%', 8);
        Lexico.columnaSimbolo.put('\n', 9);
        Lexico.columnaSimbolo.put(':', 10);
        Lexico.columnaSimbolo.put('=', 11);
        Lexico.columnaSimbolo.put('<', 12);
        Lexico.columnaSimbolo.put('>', 13);
        Lexico.columnaSimbolo.put('&', 14);
        Lexico.columnaSimbolo.put('(', 15);
        Lexico.columnaSimbolo.put(')', 16);
        Lexico.columnaSimbolo.put(',', 17);
        Lexico.columnaSimbolo.put(';', 18);
        Lexico.columnaSimbolo.put('|', 19);
        Lexico.columnaSimbolo.put('\t', 20);
        Lexico.columnaSimbolo.put(' ', 21);
        Lexico.columnaSimbolo.put('S', 22);
        Lexico.columnaSimbolo.put('$', 23);
        
        
        Lexico.matrizTransicion = new int[][]
        {
            {1,2,1,3,18,18,18,9,8,0,12,14,13,15,16,18,18,18,18,17,0,0,1,18,18},
            {1,1,1,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,1,18,18},
            {18,2,18,3,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18},
            {18,4,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,5,18,18},
            {18,4,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,5,18,18},
            {-1,7,-1,-1,6,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,18,18},
            { -1,7,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,18,18},
            {18,7,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18},
            {8,8,8,8,8,8,8,8,18,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
            {18,18,18,18,18,18,18,10,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18},
            {10,10,10,10,10,10,10,11,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
            {10,10,10,10,10,10,10,0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,18,18},
            {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18},
            {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,18,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18},
            {-1,-1,-1,-1,-1,-1,-1,-1,18,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,18,-1,-1,-1,18,-1}
        };

        AccionSemantica1 as1 = new AccionSemantica1();
        AccionSemantica2 as2 = new AccionSemantica2();
        AccionSemantica3 as3 = new AccionSemantica3();
        AccionSemantica4 as4 = new AccionSemantica4();
        AccionSemantica5 as5 = new AccionSemantica5();
        AccionSemantica6 as6 = new AccionSemantica6();
        AccionSemantica7 as7 = new AccionSemantica7();
        AccionSemantica8 as8 = new AccionSemantica8();
        AccionSemantica9 as9 = new AccionSemantica9();
        AccionSemanticaError ase = new AccionSemanticaError();
        
        
    
                Lexico.matrizAccionesSemanticas = new AccionSemantica[][]
        {
            {as2,as2,as2,as2,as1,as1,as1,as2,as2,as8,as2,as2,as2,as2,as2,as1,as1,as1,as1,as2,as8,as8,as2,as1,as1},  //0
            {as3,as3,as3,as4,as4,as4,as4,as3,as4,as4,as4,as4,as3,as4,as4,as4,as4,as4,as4,as4,as4,as4,as3,as4,as4},  //1
            {as6,as3,as6,as3,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},  //2
            {as5,as3,as5,as3,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as3,as5,as5},  //3
            {as5,as3,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as3,as5,as5},  //4
            {ase,as3,ase,ase,as3,as3,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase},  //5
            {ase,as3,ase,ase,as3,as3,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase},  //6
            {as5,as3,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5},  //7
            {as3,as3,as3,as3,as3,as3,as3,as3,as1,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3},  //8
            {as7,as7,as7,as7,as7,as7,as7,as8,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},  //9 
            {as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8},  //10
            {as8,as8,as8,as8,as8,as8,as8,as9,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8},  //11 
            {as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as1,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},  //12
            {as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as1,as1,as1,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},  //13
            {ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,as1,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase},  //14
            {as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as1,as7,as1,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},   //15
            {ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,as1,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase},   //16
            {ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,as1,ase,ase,ase,ase,ase},   //17
        }; 
    }
    
    public int getColumnaSimbolo(char c) {
        
        if(Lexico.columnaSimbolo.get(c) == null)
        return 24;
        
        return Lexico.columnaSimbolo.get(c);
    }
    
    public void vaciarBuffer() {
        Lexico.buffer = "";
    }
    
    public String getBuffer() {
        return Lexico.buffer;
    }
    
    public void concatenarBuffer(char c) {
        Lexico.buffer += String.valueOf(c);
    }
    
    
    public int yylex() throws IOException{

        Character c;
        Integer estado = 0;
        Integer token = null;

        while(token == null){
                            
            c = getSimboloEntrada();

            if (c == null) {
                Error error = new Error("Error de sintaxis. Programa mal finalizado.", false, Lexico.linea);
                Lexico.errores.add(error);
                return 0;
            }

            AccionSemantica as = Lexico.matrizAccionesSemanticas[estado][getColumnaSimbolo(c)];
            token = as.aplicarAccionSemantica(c);

            estado = matrizTransicion[estado][getColumnaSimbolo(c)];
            
            if (estado == -1 && token == null)
                token = TokensID.ERROR;
            
        }
        return token;
    }
    
    public int getLinea() {
        return Lexico.linea;
    }

    public void addLinea() {
        Lexico.linea++;
    }

    // para yylval en Parser.java
    public ParserVal getyylval(){
        return new ParserVal(Lexico.buffer);
    }

    public Map<String, Object> getAtributosLexema(String lexema){
        return Lexico.tablaSimbolos.get(lexema);
    }

    public void addLexemaTablaSimbolos(Map<String, Object> propiedadesLexema) {
        /* agrega el buffer actual a la tabla de símbolos */
        Lexico.tablaSimbolos.put(Lexico.buffer, propiedadesLexema);
    }

    // Especifico para CADENA
    public void addLexemaTablaSimbolos(Map<String, Object> propiedadesLexema, String lexico) {
        /* agrega el buffer actual a la tabla de símbolos */
        Lexico.tablaSimbolos.put(lexico, propiedadesLexema);
    }

    public void addAtributoLexema(String lexema, String nombreAtributo, Object valorAtributo) {
        /* agregar atributo a un lexema ya existente en la tabla de símbolso */
        if (Lexico.tablaSimbolos.containsKey(lexema)) {
            Lexico.tablaSimbolos.get(lexema).put(nombreAtributo, valorAtributo);
        }
    }

    public boolean containsTablaSimbolos(String lexema) {
        // verifica si el lexema se encuentra en la tabla de símbolos.
        return Lexico.tablaSimbolos.containsKey(lexema);
    }

    // Los dos metodos siguientes son para facilitar la semantica de las operaciones
    // Ej: util en AS5
    // Retorna null en el caso donde no hay mas entradas, ni del codigo fuente 
    // ni por simbolos añadidos por acciones semanticas
    public Character getSimboloEntrada() throws IOException {
        if(Lexico.data.size() > 0)
            return Lexico.data.remove(0);
        return null;
    }

    public List<Error> getErroresLexicos(){
        return Lexico.errores;
    }

    // Esto lo utilizariamos cuando
    // un axioma quiere devolver el simbolo a la entrada
    public void addSimboloEntradaInicio(char c) {
        Lexico.data.add(0,c);
    }

    public void addError(Error e) {
        Lexico.errores.add(e);
    }

    public Map<String, Map<String, Object>> getTablaSimbolos(){
        return Lexico.tablaSimbolos;
    }    

    public void cambiarSimboloConstante(String lexema){
        Map<String,Object> atributos = getAtributosLexema(lexema);
        Lexico.tablaSimbolos.remove(lexema);
        Lexico.tablaSimbolos.put("-"+lexema, atributos);
    }
    
    public void addAmbitoIdentificador(String lexema, String ambito){
        Map<String,Object> atributos = getAtributosLexema(lexema);
        Lexico.tablaSimbolos.put(lexema + ambito, atributos);   
        Lexico.tablaSimbolos.remove(lexema);
    }

    public void recortarBuffer(){
        Lexico.buffer = Lexico.buffer.substring(0, 21);
    }
}
