package com.company.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneradorCodigo {

    private static int tabs = 1;
    private static Map<String,Map<String,Object>> tablaSimbolos;
    private static List<String> variablesFuncion;
    private static List<String> cadenas;
    private static Map<String,Integer> cadenasMapeadas = new HashMap<String,Integer>();
    private static List<String> js = new ArrayList<String>();
    private static String mainWat = "";
    private static String mainJs = "";
    // System.out.println("\t".repeat(tabs) + t.toString());

    public static void setTablaSimbolos(Map<String,Map<String,Object>> tablaSimbolos){
        GeneradorCodigo.tablaSimbolos = tablaSimbolos;
    }

    public static void setVariables(List<String> variables){
        GeneradorCodigo.variablesFuncion = variables;
    }

    public static void setCadenas(List<String> cadenas){
        GeneradorCodigo.cadenas = cadenas;
    }

    public static void generar(Map<String,List<Terceto>> tercetos, String identificadorMain){
        // Se necesita generar primero porque es el que declara las variables globales
        // En todo caso hay que 
        // ver como anidar las funciones, ahora son todas hermanas. Creo que se podria generar un arbol de anidamiento
        // Como los nombres de las variables en los tercetos conservan el ambito, se generaran solo variables globales
        // como salida por cuestiones de simplicidad
        mainWat = mainWat.concat("(module\n");
        generarVariables();
        
        mainWat = mainWat.concat("\n;;  Funciones\n");
        mainWat = mainWat.concat(";; ----------------------------------------");

        for (String nombreFuncion: tercetos.keySet()){
            generarCodigoFuncion(tercetos.get(nombreFuncion), nombreFuncion, identificadorMain);
        }
        mainWat = mainWat.concat(")\n");

        generarJs(identificadorMain);

        System.out.println(mainWat);
        System.out.println(mainJs);
        

    }
    
    public static void generarCodigoFuncion(List<Terceto> tercetos, String nombreFuncion, String identificadorMain){
        if (!nombreFuncion.equals(identificadorMain)){
            String tipoRetorno = (tablaSimbolos.get(nombreFuncion).get("TIPO").equals("INT"))?"i32":"f32";
            String parametro = (String)(tablaSimbolos.get(nombreFuncion).get("NOMBRE_PARAMETRO"));
            String tipoParametro = (tablaSimbolos.get(nombreFuncion).get("TIPO_PARAMETRO").equals("INT"))?"i32":"f32";
            mainWat = mainWat.concat("\n\t".repeat(tabs) + "(func $" + nombreFuncion + "(param $" + parametro + " " + tipoParametro + ")(result " + tipoRetorno + ")\n");           
        } else{
            mainWat = mainWat.concat("\n\t".repeat(tabs) + "(func $" + nombreFuncion + " (export \"" + nombreFuncion + "\")\n"); // tiene que devolver algo si o si           
        }

        tabs++;        
        int contador = 0;

        for(Terceto t: tercetos){
            mainWat = mainWat.concat("\t".repeat(tabs-1) + ";; [" + contador + "] " + t.toString() + "\n");
            getCodigoTerceto(t, nombreFuncion);
            contador++;
        }
        if (nombreFuncion.equals(identificadorMain))
            mainWat = mainWat.concat("\t".repeat(tabs-1) + "  )\n"); 
        
        tabs--;
    }

    private static void getCodigoTerceto(Terceto t, String nombreFuncion){
        switch (t.getOperador()) {
            case ":=":
               generarCodigoAsignacion(t);
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
            
            case "PRINT":
                generarCodigoPrint(t);
                break;
            
            default:
                break;
        }
    }

    private static void generarVariables(){
    
        mainWat = mainWat.concat(";;  Declaracion de imports\n");
        mainWat = mainWat.concat(";; ----------------------------------------\n");
        mainWat = mainWat.concat("\t".repeat(tabs) + "(import \"js\" \"mem\" (memory 1))\n");
        mainWat = mainWat.concat("\t".repeat(tabs) + "(import \"js\" \"decode_print\" (func $decode_print (param i32 i32)))\n");
       
        
        mainWat = mainWat.concat("\n;;  Declaracion de variables globales\n");
        mainWat = mainWat.concat(";; ----------------------------------------\n");
        for(String v: variablesFuncion){
            String tipo = (tablaSimbolos.get(v).get("TIPO").equals("INT"))?"i32":"f32";
            mainWat = mainWat.concat("\t".repeat(tabs) + "(global $" + v + " (import \"js\" \"global." + v + "\") " + "(mut " + tipo + "))\n");
            js.add("\"global." + v + "\": new WebAssembly.Global({value:\'"+tipo+"\', mutable:true}, 0)");
        }
        
        mainWat = mainWat.concat("\n;;  Cadenas\n");
        mainWat = mainWat.concat(";; ----------------------------------------\n");
        int sumaCadenas = 0; // Marca el inicio de la cadena
        for(String cadena: GeneradorCodigo.cadenas){
            // (data (i32.const 0) "Hello, World!")
            cadenasMapeadas.put(cadena, sumaCadenas);
            mainWat = mainWat.concat("\t".repeat(tabs) + "(data (i32.const " + sumaCadenas + ") \"" + cadena.substring(1,cadena.length()-1) + "\")\n");
            sumaCadenas += cadena.length() - 2; // quito los %
        }
                
    }

    private static void generarCodigoAsignacion(Terceto t){
        String tipo;
                if (t.getOperando2().startsWith("[")){
                    // mainWat = mainWat.concat("\t".repeat(tabs) + "global.get $" + t.getOperando2());
                    mainWat = mainWat.concat("\t".repeat(tabs) + "global.set $" + t.getOperando1() + "\n");
                } else {
                    tipo = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                    mainWat = mainWat.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando2() + "\n");
                    mainWat = mainWat.concat("\t".repeat(tabs) + "global.set $" + t.getOperando1() + "\n");
                }
    }

    private static void generarCodigoLlamadoFuncion(Terceto t){
        if (!t.getOperando2().startsWith("[")){ // en caso de ser un terceto, el ooperando ya esta en la pila
            if (Character.isDigit(t.getOperando2().charAt(0))){
                String tipo = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWat = mainWat.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando2() + "\n");
            } else {
                mainWat = mainWat.concat("\t".repeat(tabs) + "global.get $" + t.getOperando2() + "\n");
            }
        }

        mainWat = mainWat.concat("\t".repeat(tabs) + "call $" + t.getOperando1() + "\n");
    }

    private static void generarCodigoReturn(Terceto t){
        if (!t.getOperando1().startsWith("[")){ // en caso de ser un terceto, el ooperando ya esta en la pila
            if (Character.isDigit(t.getOperando1().charAt(0))){
                String tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWat = mainWat.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando1() + "\n");
            } else {
                mainWat = mainWat.concat("\t".repeat(tabs) + "global.get $" + t.getOperando1() + "\n");
            }
        }
        mainWat = mainWat.concat("\t".repeat(tabs-1) + "  )\n");
    }

    private static void generarCodigoOperacionTermino(String operador, Terceto t ){
        String tipo = (t.getTipo().equals("INT"))?"i32":"f32";
        String tipo1,tipo2;

        if (!t.getOperando1().startsWith("[")){
            if (Character.isDigit(t.getOperando1().charAt(0))){
                tipo1 = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWat = mainWat.concat("\t".repeat(tabs) + tipo1 + ".const " + t.getOperando1() + "\n");
            } else {
                mainWat = mainWat.concat("\t".repeat(tabs) + "global.get $" + t.getOperando1() + "\n");
            }
        }

        if (!t.getOperando2().startsWith("[")){
            if (Character.isDigit(t.getOperando2().charAt(0))){
                tipo2 = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWat = mainWat.concat("\t".repeat(tabs) + tipo2 + ".const " + t.getOperando2() + "\n");
            } else {
                mainWat = mainWat.concat("\t".repeat(tabs) + "global.get $" + t.getOperando2() + "\n");
            }
        }

        mainWat = mainWat.concat("\t".repeat(tabs) + tipo + "." + operador + "\n");
    }

    public static void generarCodigoConversion(Terceto t){
        if (!t.getOperando1().startsWith("[")){
            if (Character.isDigit(t.getOperando1().charAt(0))){
                String tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWat = mainWat.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando1() + "\n");
            } else {
                mainWat = mainWat.concat("\t".repeat(tabs) + "global.get $" + t.getOperando1() + "\n");
            }
        } 
        mainWat = mainWat.concat("\t".repeat(tabs) + "f32.convert_s/i32\n");
    }
    
    public static void generarCodigoPrint(Terceto t){
        mainWat = mainWat.concat("\t".repeat(tabs) + "i32.const " + cadenasMapeadas.get(t.operando1) + "\n");
        mainWat = mainWat.concat("\t".repeat(tabs) + "i32.const " + (cadenasMapeadas.get(t.operando1) + t.getOperando1().length() - 2) + "\n");
        mainWat = mainWat.concat("\t".repeat(tabs) + "call $decode_print\n");
    }

    public static void generarJs(String identificadorMain){
        mainJs = mainJs.concat("\n\n   main.js\n"); // falta el export
        mainJs = mainJs.concat("----------------------------------------\n");
        mainJs = mainJs.concat(""
            .concat("let import_object = {\n")
            .concat("    \"js\":{\n")
            .concat("          \"mem\": new WebAssembly.Memory({initial: 1}),\n")
            .concat("          \"decode_print\": (start, end) => {\n")
            .concat("              let typed_array = new Uint8Array(import_object.js.mem.buffer, start, end);\n")
            .concat("              document.writeln(new TextDecoder(\"utf-8\").decode(typed_array) + \"<br>\");\n")
            .concat("              },\n")
        );

        for(String i: js.subList(0, js.size() - 1)){
            mainJs = mainJs.concat("           " + i + ",\n");
        }
        mainJs = mainJs.concat("           " + js.get(js.size() - 1) + "\n");
        mainJs = mainJs.concat("       }\n");
        mainJs = mainJs.concat("  };\n");
        mainJs = mainJs.concat(""
            .concat("fetch(\"main.wasm\")\n")
            .concat("  .then(response => response.arrayBuffer())\n")
            .concat("  .then(bytes => WebAssembly.instantiate(bytes, import_object))\n")
            .concat("  .then(result =>{\n")
            .concat("      let exports = result.instance.exports;\n")
            .concat("      exports." + identificadorMain + "();\n")
            .concat("}).catch(console.error);\n")
        );
    }
}
