package com.company.Analizadores;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
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
import com.company.Util.ParTokenLexema;
import com.company.Util.Error;

import org.apache.logging.log4j.LogManager;

public class Lexico {
    private static Map<Character, Integer> columnaSimbolo;
    private static int[][] matrizTransicion;
    private static AccionSemantica[][] matrizAccionesSemanticas;
    private static Lexico lexico;
    private static int linea;
    private static String buffer;
    public static final int TAMANO_BUFFER = 22;
    private static BufferedReader data;
    private static List<ParTokenLexema> reconocidos;
    private static List<Character> entrada;
    private static Map<String, Map<String, Object>> tablaSimbolos;
    private static List<Error> errores;
    
    
    public static Lexico getInstance(){
        if(Lexico.lexico == null)
            Lexico.lexico = new Lexico();
        return Lexico.lexico;
    }

    public void setData(BufferedReader d) {
        Lexico.data = d;
    }

    private Lexico(){

        Lexico.linea = 1;
        Lexico.buffer = null;
        Lexico.buffer = "";
        Lexico.data = null;
        Lexico.reconocidos = new ArrayList<ParTokenLexema>();
        Lexico.entrada = new ArrayList<Character>();

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
            {8,8,8,8,8,8,8,8,18,8,8,8,8,8,8,8,8,8,8,8,8,8,8,18,18},
            {18,18,18,18,18,18,18,10,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18},
            {10,10,10,10,10,10,10,11,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,18,18},
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
            {as3,as3,as3,as4,as4,as4,as4,as3,as4,as4,as4,as4,as3,as4,as4,as4,as4,as3,as4,as4,as4,as4,as3,as4,as4},  //1
            {as6,as3,as6,as3,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},  //2
            {as6,as3,as6,as3,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as3,as6,as6},  //3
            {as5,as3,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as3,as5,as5},  //4
            {ase,as3,ase,ase,as3,as3,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase},  //5
            {ase,as3,ase,ase,as3,as3,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase,ase},  //6
            {as5,as3,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5},  //7
            {as3,as3,as3,as3,as3,as3,as3,as3,as1,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3},  //8
            {as7,as7,as7,as7,as7,as7,as7,as8,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},  //9 
            {as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8},  //10
            {as8,as8,as8,as8,as8,as8,as8,as9,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8,as8},  //11 
            {as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as1,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},  //12
            {as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as1,as1,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7,as7},  //13
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
    
    public List<ParTokenLexema> getTokens() throws IOException {

        Character c = getSimboloEntrada();

        Integer estado = 0;
        
        while (Lexico.columnaSimbolo.get(c) != null){

            AccionSemantica as = Lexico.matrizAccionesSemanticas[estado][Lexico.columnaSimbolo.get(c)];

            as.aplicarAccionSemantica(c);

            estado = matrizTransicion[estado][Lexico.columnaSimbolo.get(c)];

            if (estado == 18) 
                estado = 0;
            
            if (estado == -1){
                estado = 0;
                Error error = new Error("Error de sintaxis.", false, Lexico.linea);
                Lexico.errores.add(error);
            }

            c = getSimboloEntrada();
        }

        return Lexico.reconocidos;
    }

    public int yylex(){
        return Lexico.reconocidos.remove(0).getToken();
    }


    public void addToken(ParTokenLexema parTokenLexema) {
        Lexico.reconocidos.add(parTokenLexema);
    }
    
    public int getLinea() {
        return Lexico.linea;
    }

    public void addLinea() {
        Lexico.linea++;
    }

    public void addLexemaTablaSimbolos(Map<String, Object> propiedadesLexema) {
        /* agrega el buffer actual a la tabla de símbolos */
        Lexico.tablaSimbolos.put(Lexico.buffer, propiedadesLexema);
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
        // Si no se agrego un simbolo mediante una accion semantica
        // lo lee del codigo fuente
        
        if (Lexico.entrada.isEmpty()){
            try {
                Lexico.entrada.add((char) Lexico.data.read());
            } catch(EOFException e) {
                LogManager.getLogger(Lexico.class).info("Fin archivo");
            }
        }
        
        Character retorno = null;
        
        try {
            retorno = Lexico.entrada.remove(0);
        } catch (IndexOutOfBoundsException e) {
            LogManager.getLogger(Lexico.class).info("No hay mas entrada");
        }
        
        return retorno;
    }

    public List<Error> getErrores(){
        return Lexico.errores;
    }

    // Esto lo utilizariamos cuando
    // un axioma quiere devolver el simbolo a la entrada
    public void addSimboloEntradaInicio(char c) {
        Lexico.entrada.add(0,c);
    }

    public void addError(Error e) {
        Lexico.errores.add(e);
    }


}
