package com.company.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import com.company.Analizadores.Lexico;

public class GeneradorCodigo {

    private static int tabs = 1;
    private static Map<String,Map<String,Object>> tablaSimbolos;
    private static List<String> variablesFuncion;
    private static List<String> cadenas;
    private static Map<String,Integer> cadenasMapeadas = new HashMap<String,Integer>();
    private static List<String> js = new ArrayList<String>();
    private static String mainWat = "";
    private static String mainWatAux = "";
    private static String mainJs = "";
    private static List<String> variablesAuxiliares = new ArrayList<String>();
    private static int contador;
    public static List<Terceto> tercetosActual;
    public static boolean flagRepeat = false;
    public static boolean flagElse = false;
    public static Terceto condicionRepeat;
    public static List<String> operacionesExpresion = new ArrayList<String>(Arrays.asList("+","-","*","/","CONV"));
    private static String mensajeError = "%Error - No se puede dividir por cero%";

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
        // Como los nombres de las variables en los tercetos conservan el ambito, se generaran solo variables globales
        // como salida por cuestiones de simplicidad
        mainWat = mainWat.concat("(module\n");
        generarVariables();
        
        mainWat = mainWat.concat("\n;;  Funciones\n");
        mainWat = mainWat.concat(";; ----------------------------------------");
        
        for (String nombreFuncion: tercetos.keySet()){
            GeneradorCodigo.tercetosActual = tercetos.get(nombreFuncion);
            generarCodigoFuncion(tercetos.get(nombreFuncion), nombreFuncion, identificadorMain);
        }
        mainWat = mainWat.concat(")\n");
        
        generarJs(identificadorMain);

        // System.out.println("              main.wat"); 
        // System.out.println("+--------------------------------------+\n");
        // System.out.println(mainWat);
        try {
            BufferedWriter brWat = new BufferedWriter(new FileWriter(new File("./wasm/main.wat")));
            brWat.write(mainWat);
            brWat.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("              main.js"); 
        // System.out.println("+--------------------------------------+\n");
        // System.out.println(mainJs);
        try {
            BufferedWriter brJs = new BufferedWriter(new FileWriter(new File("./wasm/main.js")));
            brJs.write(mainJs);
            brJs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
    
    public static void generarCodigoFuncion(List<Terceto> tercetos, String nombreFuncion, String identificadorMain){
        if (!nombreFuncion.equals(identificadorMain)){
            String tipoRetorno = (tablaSimbolos.get(nombreFuncion).get("TIPO").equals("INT"))?"i32":"f32";
            String parametro = (String)(tablaSimbolos.get(nombreFuncion).get("NOMBRE_PARAMETRO"));
            String tipoParametro = (tablaSimbolos.get(nombreFuncion).get("TIPO_PARAMETRO").equals("INT"))?"i32":"f32";
            mainWat = mainWat.concat("\n\t".repeat(tabs) + "(func $" + nombreFuncion + " (param $" + parametro + " " + tipoParametro + ") (result " + tipoRetorno + ")\n");           
        } else{
            mainWat = mainWat.concat("\n\t".repeat(tabs) + "(func $" + nombreFuncion + " (export \"" + nombreFuncion + "\")\n"); // tiene que devolver algo si o si           
        }

        tabs++;        
        contador = 0;
        variablesAuxiliares.clear();
        mainWatAux = "";
        for(Terceto t: tercetos){
            if(!operacionesExpresion.contains(t.getOperador())){
                mainWatAux = mainWatAux.concat("\t" + ";; [" + contador + "] " + t.toString() + "\n");
                getCodigoTerceto(t, nombreFuncion, "[" + contador + "]");
            }
            contador++;
        }
        mainWat = mainWat.concat(mainWatAux);
        if (nombreFuncion.equals(identificadorMain))
            mainWat = mainWat.concat("\t".repeat(tabs-1) + ")\n"); 
        
        tabs--;
    }

    private static void getCodigoTerceto(Terceto t, String nombreFuncion, String numeroTerceto){
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
                generarCodigoOperacionAritmetica("add", t);
                break;
            
            case "-":
                generarCodigoOperacionAritmetica("sub", t);
                break;
            
            case "*":
                generarCodigoOperacionAritmetica("mul", t);
                break;
            
            case "/":
                generarCodigoOperacionAritmetica("div", t);
                break;
            
            case "CONV":
                generarCodigoConversion(t, numeroTerceto);
                break;
            
            case "PRINT":
                generarCodigoPrint(t);
                break;
            
            case "REPEAT":
                flagRepeat = true;
                generarCodigoInicioRepeat();
                break;
            
            case "END_REPEAT":
                generarCodigoEndRepeat(t, numeroTerceto);
                break;
            
            case "END_IF":
                generarCodigoEndIf();
                break;
            
            case "BF":
                generarCodigoBF(t);
                break;
        
            case "BI":
                generarCodigoBI(t);
                break;
            
            case "||":
                generarCodigoOperacionLogica("or",t, numeroTerceto);
                break;
            case "&&":
                generarCodigoOperacionLogica("and",t, numeroTerceto);
                break;

            case "<":
            case ">":
            case "<=":
            case ">=":
            case "==":
            case "<>":
                if(flagRepeat){
                    condicionRepeat = t; // Para comprobar la condicion dentro del loop
                }
                generarCodigoComparacion(t, false, numeroTerceto);
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
        mainWat = mainWat.concat("\t".repeat(tabs) + "(import \"js\" \"error_div_cero\" (func $error_div_cero))\n");
        
        
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
            cadenasMapeadas.put(cadena, sumaCadenas);
            mainWat = mainWat.concat("\t".repeat(tabs) + "(data (i32.const " + sumaCadenas + ") \"" + cadena.substring(1,cadena.length()-1) + "\")\n");
            sumaCadenas += cadena.length() - 2; // quito los %
        }
        cadenasMapeadas.put(mensajeError, sumaCadenas);
                
    }

    private static String getModoObjeto(String operando){
        return tablaSimbolos.get(operando).get("USO").equals("ID_VARIABLE")?"global":"local";
    }

    private static void generarCodigoAsignacion(Terceto t){
        String tipo;
                if (t.getOperando2().startsWith("[")){
                    rearmarCadenaTercetos(t.getOperando2());
                    mainWatAux = mainWatAux.concat("\t".repeat(2) +";; Operaciones de la propia asignacion\n");
                    checkAux(t.getOperando2());
                } else {
                    if (Character.isDigit(t.getOperando2().charAt(0)) || t.getOperando2().startsWith(".")){
                        tipo = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando2() + "\n");
                    } else {
                        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando2()) + ".get $" + t.getOperando2() + "\n");
                    }
                }
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando1()) + ".set $" + t.getOperando1() + "\n");
    }

    private static void generarCodigoLlamadoFuncion(Terceto t){
        if (!t.getOperando2().startsWith("[")){ // en caso de ser un terceto, el operando ya esta en la pila
            if (Character.isDigit(t.getOperando2().charAt(0)) || t.getOperando2().startsWith(".")){
                String tipo = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando2() + "\n");
            } else {
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando2()) + ".get $" + t.getOperando2() + "\n");
            }
        }
        
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "call $" + t.getOperando1() + "\n");
    }
    
    private static void generarCodigoReturn(Terceto t){
        if (!t.getOperando1().startsWith("[")){ // en caso de ser un terceto, el ooperando ya esta en la pila
            if (Character.isDigit(t.getOperando1().charAt(0)) || t.getOperando1().startsWith(".")){
                String tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando1() + "\n");
            } else {
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando1()) + ".get $" + t.getOperando1() + "\n");
            }
        } else{
            checkAux(t.getOperando1());
        }
        mainWatAux = mainWatAux.concat("\t".repeat(tabs-1) + "  )\n");
    }
    
    private static void generarCodigoOperacionAritmetica(String operador, Terceto t ){
        String tipo = (t.getTipo().equals("INT"))?"i32":"f32";
        String tipo1,tipo2; // Redundante, ya se chequea durante la generacion de tercetos
        // String op1 = "";

        if (!t.getOperando1().startsWith("[")){
            if (Character.isDigit(t.getOperando1().charAt(0)) || t.getOperando1().startsWith(".")){
                tipo1 = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
                // if(!operador.equals("div"))
                    mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo1 + ".const " + t.getOperando1() + "\n");
                // if(operador.equals("div"))
                //     op1 = op1.concat("\t".repeat(tabs) + tipo1 + ".const " + t.getOperando1() + "\n");
                
            } else {
                // if(!operador.equals("div"))
                    mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando1()) + ".get $" + t.getOperando1() + "\n");
                // if(operador.equals("div"))
                //     op1 = op1.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando1()) + ".get $" + t.getOperando1() + "\n");
            }
        } else {
            // if(!operador.equals("div"))
                checkAux(t.getOperando1());
            // if(operador.equals("div"))
            //     op1 = op1.concat("\t".repeat(tabs) + "local.get $" + "aux.".concat(t.getOperando1().replace("[", "").replace("]", "") + "\n"));
        }

        if(operador.equals("div"))
            generarVariableAuxiliar(tipo, "op1." + tipo);
        
        if (!t.getOperando2().startsWith("[")){
            if (Character.isDigit(t.getOperando2().charAt(0)) || t.getOperando2().startsWith(".")){
                tipo2 = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo2 + ".const " + t.getOperando2() + "\n");
            } else {
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando2()) + ".get $" + t.getOperando2() + "\n");
            }
            if(operador.equals("div"))
                generarCodigoComprobacionDivCero(tipo,"op1." + tipo);
        } else{
            checkAux(t.getOperando2());
            if(operador.equals("div"))
                generarCodigoComprobacionDivCero(tipo,"op1." + tipo);
        }

        if(tipo.equals("i32") && operador.equals("div"))
            operador = operador + "_s";
        
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + "." + operador + "\n");
    }

    public static void generarCodigoComprobacionDivCero(String tipo, String op1){
        String nombreVarAuxiliar = tipo.equals("i32")?"div.i32":"div.f32";
        generarVariableAuxiliar(tipo,nombreVarAuxiliar); // guardo del divisor

        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "(block\n");
        tabs++;

        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "local.get $aux." + nombreVarAuxiliar + "\n");
        if(tipo.equals("i32"))
            mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.const 0\n");
        else 
            mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "f32.const 0.0\n");
        
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".eq\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.const 1\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.xor\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "br_if 0\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "call $error_div_cero\n");
        tabs--;
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + ")\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "local.get $aux." + op1 +"\n"); // restauro el valor del operando 1
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "local.get $aux." + nombreVarAuxiliar + "\n"); // restauro el valor del operando 2
    }

    public static void generarCodigoConversion(Terceto t, String numeroTerceto){
        if (!t.getOperando1().startsWith("[")){
            if (Character.isDigit(t.getOperando1().charAt(0)) || t.getOperando1().startsWith(".")){
                String tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando1() + "\n");
            } else {
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando1()) + ".get $" + t.getOperando1() + "\n");
            }
        } 
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "f32.convert_s/i32\n");
        generarVariableAuxiliar("f32", numeroTerceto);
    } 
    
    public static void generarCodigoComparacion(Terceto t, boolean repeat, String numeroTerceto){
        // El parser ya comprueba que los tipos de o1 y o2 sean el mismo
        String tipo;
        if (!t.getOperando1().startsWith("[")){
            tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
        } else{
            tipo = getTerceto(t.getOperando1()).getTipo();
        }

        if (t.getOperando1().startsWith("[")){
            rearmarCadenaTercetos(t.getOperando2());
            generarVariableAuxiliar(tipo, t.getOperando1());
        }
        if (t.getOperando2().startsWith("[")){
            rearmarCadenaTercetos(t.getOperando2());
            generarVariableAuxiliar(tipo, t.getOperando2());
        }
        // String tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
        
        if (!t.getOperando1().startsWith("[")){
            if (Character.isDigit(t.getOperando1().charAt(0)) || t.getOperando1().startsWith(".")){
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando1() + "\n");
            } else {
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando1()) + ".get $" + t.getOperando1() + "\n");
            }
        } else {
            // rearmarCadenaTercetos(t.getOperando1());
            // mainWatAux = mainWatAux.concat("\t".repeat(2) +";; Operaciones de la propia asignacion\n");
            // checkAux(t.getOperando1());
        }
        
        if (!t.getOperando2().startsWith("[")){
            if (Character.isDigit(t.getOperando2().charAt(0)) || t.getOperando2().startsWith(".")){
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando2() + "\n");
            } else {
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando2()) + ".get $" + t.getOperando2() + "\n");
            }
        } else{
            // rearmarCadenaTercetos(t.getOperando2());
            // mainWatAux = mainWatAux.concat("\t".repeat(2) +";; Operaciones de la propia asignacion\n");
            // checkAux(t.getOperando2());
        }

        if (t.getOperando1().startsWith("["))
            checkAux(t.getOperando1());

        if (t.getOperando2().startsWith("["))
            checkAux(t.getOperando2());

        
        String signed = "";
        if(tipo.equals("i32"))
            signed = "_s";
           
        // 0 = false, 1 = true
        // Las operaciones dan como resultado un i32
        switch (t.getOperador()) {
            case "<":
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo +".lt" + signed + "\n");
                break;
            case ">":
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo +".gt" + signed + "\n");
                break;
            case "<=":
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo +".le" + signed + "\n");
                break;
            case ">=":
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo +".ge" + signed + "\n");
                break;
            case "==":
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo +".eq\n");
                break;
            case "<>":
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo +".ne\n");
                break;
            
            default:
            break;
        }
        if(!repeat)
            generarVariableAuxiliar("i32", numeroTerceto);
    }
    
    public static void generarCodigoOperacionLogica(String op, Terceto t, String numeroTerceto){
        String tipo;
        // Va a generar problemas cuando tenga como operador a una constante, variable o expresion de tipo SINGLE
        // No asi con los tercetos de comparaciones u operaciones logicas ya que se almacenan 
        // en variables auxiliares de tipo i32
        if (!t.getOperando1().startsWith("[")){
            tipo = (tablaSimbolos.get(t.getOperando1()).get("TIPO").equals("INT"))?"i32":"f32";
            if (Character.isDigit(t.getOperando1().charAt(0)) || t.getOperando1().startsWith(".")){
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando1() + "\n");
            } else {
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando1()) + ".get $" + t.getOperando1() + "\n");
            }
        } else {
            checkAux(t.getOperando1());
        }
        
        if (!t.getOperando2().startsWith("[")){
            tipo = (tablaSimbolos.get(t.getOperando2()).get("TIPO").equals("INT"))?"i32":"f32";
            if (Character.isDigit(t.getOperando2().charAt(0)) || t.getOperando2().startsWith(".")){
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + tipo + ".const " + t.getOperando2() + "\n");
            } else {
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + getModoObjeto(t.getOperando2()) + ".get $" + t.getOperando2() + "\n");
            }
        } else{
            checkAux(t.getOperando2());
        }
        // Las operaciones son bit a bit
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32." + op + "\n");
        generarVariableAuxiliar("i32", numeroTerceto);
    }

    public static void generarCodigoPrint(Terceto t){
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.const " + cadenasMapeadas.get(t.operando1) + "\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.const " + (cadenasMapeadas.get(t.operando1) + t.getOperando1().length() - 2) + "\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "call $decode_print\n");
    }

    public static void generarCodigoInicioRepeat(){
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "(block\n");
        tabs++;
    }

    public static void generarCodigoEndRepeat(Terceto t, String numeroTerceto){
        // Rearmar la cadena de tercetos del operando 2 (la expresion de la comparacion)
        // en caso de ser necesario
        if(condicionRepeat.getOperando2().startsWith("["))
            rearmarCadenaTercetos(condicionRepeat.getOperando2());
        generarCodigoComparacion(condicionRepeat, true, numeroTerceto);
        
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.eqz\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "br_if 1\n");
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "br 0\n");
        tabs--;
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + ")\n");
        tabs--;
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + ")\n");
    }
 
    public static void generarCodigoEndIf(){
        tabs--;
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + ")\n");
    }
    
    public static void generarCodigoBF(Terceto t){
        
        if(flagRepeat){ // Para saber si se trata de la condicion de un repeat o no
            checkAux(t.getOperando1());
            mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.eqz\n");
            mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "br_if 0\n");
            mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "(loop\n");
            flagRepeat = false;    
            tabs++;
        } else{
            mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "(block\n");
            tabs++;
            if(t.getOperando2() == null){
                checkAux(t.getOperando1());
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.eqz\n");
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "br_if 0\n");
            } else{
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "(block\n");
                tabs++;
                checkAux(t.getOperando1());
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "i32.eqz\n");
                mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "br_if 0\n");
                flagElse = true;
            }
        }
    }
    public static void generarCodigoBI(Terceto t){
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "br 1\n");
        tabs--;
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + ")\n");
    }

    public static void generarVariableAuxiliar(String tipo, String terceto){
        // String nombreAux = "aux.".concat("[" + contador + "]").replace("[", "").replace("]", "");
        String nombreAux = "aux.".concat(terceto).replace("[", "").replace("]", "");
        if(!variablesAuxiliares.contains(nombreAux)){
            variablesAuxiliares.add(nombreAux);
            mainWat = mainWat.concat("\t".repeat(2) + "(local $" + nombreAux + " " + tipo + ")\n");
        }
        mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "local.set $" + nombreAux + "\n");
    }
    
    public static void checkAux(String operando){
        if(variablesAuxiliares.contains("aux.".concat(operando).replace("[", "").replace("]", "")))
            mainWatAux = mainWatAux.concat("\t".repeat(tabs) + "local.get $" + "aux.".concat(operando).replace("[", "").replace("]", "") + "\n");
    }

    public static Terceto getTerceto(String terceto){
        return GeneradorCodigo.tercetosActual.get(Integer.parseInt(terceto.substring(1, terceto.length() - 1)));
    }

    public static void rearmarCadenaTercetos(String terceto){
        // Tengo que recorrer recursivamente el terceto y rearmar las operaciones
        // llamando a getCodigoTerceto(Terceto t, String nombreFuncion)
        Terceto t = getTerceto(terceto);
        if(t.getOperando1() != null)
            if(t.getOperando1().startsWith("["))
                rearmarCadenaTercetos(t.getOperando1());
        if(t.getOperando2() != null)
            if(t.getOperando2().startsWith("["))
                rearmarCadenaTercetos(t.getOperando2());
        
        // if(t.getOperando1() != null)     
        //     checkAux(t.getOperando1());
        // if(t.getOperando2() != null)     
        //     checkAux(t.getOperando2());
        
        mainWatAux = mainWatAux.concat("\t\t" + ";; " + terceto + " " + t.toString() + "\n");
        getCodigoTerceto(t, null, terceto);
    }
    
    public static void generarJs(String identificadorMain){
        mainJs = mainJs.concat(""
        .concat("let import_object = {\n")
        .concat("    \"js\":{\n")
        .concat("          \"mem\": new WebAssembly.Memory({initial: 1}),\n")
        .concat("          \"decode_print\": (start, end) => {\n")
        .concat("              let typed_array = new Uint8Array(import_object.js.mem.buffer, start, end);\n")
        .concat("              document.writeln(new TextDecoder(\"utf-8\").decode(typed_array) + \"<br>\");\n")
        .concat("              },\n")
        .concat("          \"error_div_cero\": () => {\n")
        .concat("              document.writeln(\"Error - Dvision por cero<br>\");\n")
        .concat("              throw new WebAssembly.RuntimeError(\"Error - Division por cero\");\n")
        
        .concat("          },\n")
        );
        
        for(String i: js.subList(0, js.size() - 1)){
            mainJs = mainJs.concat("          " + i + ",\n");
        }
        mainJs = mainJs.concat("          " + js.get(js.size() - 1) + "\n");
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
