package com.company.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneradorCodigo {

    private static int tabs = 0;
    private static Map<String,Map<String,Object>> tablaSimbolos;
    private static Map<String,List<String>> variables;
    // System.out.println("\t".repeat(tabs) + t.toString());

    public static void setTablaSimbolos(Map<String,Map<String,Object>> tablaSimbolos){
        GeneradorCodigo.tablaSimbolos = tablaSimbolos;
    }

    public static void setVariables(Map<String,List<String>> variables){
        GeneradorCodigo.variables = variables;
    }

    public static void generar(Map<String,List<Terceto>> tercetos, String identificadorMain){
        // Se necesita generar primero porque es el que declara las variables globales
        // Hay que ver el tema de las variables locales y globales, por ahora son todas locales,
        // creo que como las funciones se declaran dentro del main, las variables del main son alcanzables
        // dentro de la funcion, lo mismo para funciones adentro de funciones. En todo caso hay que 
        // ver como anidar las funciones, ahora son todas hermanas. Creo que se podria generar un arbol de anidamiento
        System.out.println(identificadorMain);
        generarCodigoFuncion(tercetos.get(identificadorMain), identificadorMain, identificadorMain); 

        for (String nombreFuncion: tercetos.keySet()){
            if(!nombreFuncion.equals(identificadorMain))
                generarCodigoFuncion(tercetos.get(nombreFuncion), nombreFuncion, identificadorMain);
        }
    }

    public static void generarCodigoFuncion(List<Terceto> tercetos, String nombreFuncion, String identificadorMain){
        if (!nombreFuncion.equals(identificadorMain)){
            String tipoRetorno = (tablaSimbolos.get(nombreFuncion).get("TIPO").equals("INT"))?"i32":"f32";
            String parametro = (String)(tablaSimbolos.get(nombreFuncion).get("NOMBRE_PARAMETRO"));
            String tipoParametro = (tablaSimbolos.get(nombreFuncion).get("TIPO_PARAMETRO").equals("INT"))?"i32":"f32";
            System.out.println("(func $" + nombreFuncion + "(param $" + parametro + " " + tipoParametro + ")(result " + tipoRetorno + ")");           
        }

        tabs++;        
        generarVariables(nombreFuncion);
        int contador = 0;

        for(Terceto t: tercetos){
            System.out.println("---------------------------------------- [" + contador + "] " + t.toString());
            getCodigoTerceto(t, nombreFuncion);
            contador++;
        }
        tabs--;
        System.out.println("*************************************");
    }

    private static void getCodigoTerceto(Terceto t, String nombreFuncion){
        switch (t.getOperador()) {
            case ":=":
               String tipo;
                if (t.getOperando2().startsWith("[")){
                    // System.out.println("\t".repeat(tabs) + "local.get $" + t.getOperando2());
                    System.out.println("\t".repeat(tabs) + "local.set $" + t.getOperando1());
                } else {
                    tipo = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                    System.out.println("\t".repeat(tabs) + tipo + ".const " + t.getOperando2());
                    System.out.println("\t".repeat(tabs) + "local.set $" + t.getOperando1());
                }
                break;
            
            case "CALL_FUNC":
                generarCodigoLlamadoFuncion(t);
                break;
            
            case "RETURN_FUNC":
                generarCodigoReturn(t);
                break;
            
            case "+":
                generarCodigoOperacionTermino("add", t);
                break;
            case "-":
                generarCodigoOperacionTermino("sub", t);
                break;
            
            case "CONV":
                generarCodigoConversion(t);
                break;
            
            default:
                System.out.println("\t".repeat(tabs) + t.toString());
                break;
        }
    }

    private static void generarVariables(String nombreFuncion){
        List<String> variablesFuncion = GeneradorCodigo.variables.get(nombreFuncion);

        System.out.println("---------------------------------------- Declaracion de variables locales");
        if(variablesFuncion != null){
            for(String v: variablesFuncion){
                String tipo = (tablaSimbolos.get(v).get("TIPO").equals("INT"))?"i32":"f32";
                System.out.println("\t".repeat(tabs) + "(local $" + v + " " + tipo  +")");
            }
        }
        
    }

    private static void generarCodigoLlamadoFuncion(Terceto t){
        if (!t.getOperando2().startsWith("[")){ // en caso de ser un terceto, el ooperando ya esta en la pila
            if (Character.isDigit(t.getOperando2().charAt(0))){
                String tipo = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                System.out.println("\t".repeat(tabs) + tipo + ".const " + t.getOperando2());
            } else {
                System.out.println("\t".repeat(tabs) + "local.get $" + t.getOperando2());
            }
        }

        System.out.println("\t".repeat(tabs) + "call $" + t.getOperando1());
    }

    private static void generarCodigoReturn(Terceto t){
        if (!t.getOperando1().startsWith("[")){ // en caso de ser un terceto, el ooperando ya esta en la pila
            if (Character.isDigit(t.getOperando1().charAt(0))){
                String tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
                System.out.println("\t".repeat(tabs) + tipo + ".const " + t.getOperando1());
            } else {
                System.out.println("\t".repeat(tabs) + "local.get $" + t.getOperando1());
            }
        }
        System.out.println("\t".repeat(tabs-1) + "  )");
    }

    private static void generarCodigoOperacionTermino(String operador, Terceto t ){
        String tipo = (t.getTipo().equals("INT"))?"i32":"f32";
        String tipo1,tipo2;

        if (!t.getOperando1().startsWith("[")){
            if (Character.isDigit(t.getOperando1().charAt(0))){
                tipo1 = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                System.out.println("\t".repeat(tabs) + tipo1 + ".const " + t.getOperando1());
            } else {
                System.out.println("\t".repeat(tabs) + "local.get $" + t.getOperando1());
            }
        }

        if (!t.getOperando2().startsWith("[")){
            if (Character.isDigit(t.getOperando2().charAt(0))){
                tipo2 = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                System.out.println("\t".repeat(tabs) + tipo2 + ".const " + t.getOperando2());
            } else {
                System.out.println("\t".repeat(tabs) + "local.get $" + t.getOperando2());
            }
        }

        System.out.println("\t".repeat(tabs) + tipo + "." + operador);
    }

    public static void generarCodigoConversion(Terceto t){
        if (!t.getOperando1().startsWith("[")){
            if (Character.isDigit(t.getOperando1().charAt(0))){
                String tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
                System.out.println("\t".repeat(tabs) + tipo + ".const " + t.getOperando1());
            } else {
                System.out.println("\t".repeat(tabs) + "local.get $" + t.getOperando1());
            }
        } 
        System.out.println("\t".repeat(tabs) + "f32.convert_s/i32");
    }
    
}
