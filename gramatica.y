%{
    package com.company.Analizadores;    
    import com.company.Analizadores.Lexico;
    import com.company.Analizadores.ParserVal;
    import com.company.Util.Error;
    import com.company.Util.Terceto;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.io.IOException;
    import java.math.BigDecimal;
    import java.util.Stack;
%}


%token MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE CADENA AND OR IDENTIFICADOR ASIGNACION CTE ERROR INT IF THEN ELSE ENDIF PRINT FUNC RETURN BEGIN END BREAK SINGLE REPEAT PRE

%left '&&'
%left '||'
%left '<' '>' MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE

%%

PROGRAMA:                       PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END
                                | PROGRAMA_ENCABEZADO BEGIN CONJUNTO_SENTENCIAS END 
                                | PROGRAMA_ERROR
				                ;

PROGRAMA_ENCABEZADO:            IDENTIFICADOR ';' {ambitoActual.add(0, $1.sval); addAtributoLexema($1.sval,"USO","Nombre de Programa");}
                                ;

PROGRAMA_ERROR:                 PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA {yyerror("Bloque principal no especificado.");}
                                | PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BLOQUE_SENTENCIA END {yyerror("BEGIN del bloque principal no especificado.");}
                                | PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BEGIN BLOQUE_SENTENCIA {yyerror("END del bloque principal no especificado.");}
                                | PROGRAMA_ENCABEZADO error SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END {yyerror("Falta el nombre del programa.");}
                                ;
                                	
SENTENCIA_DECLARATIVA:          SENTENCIA_DECLARATIVA DECLARACION_VARIABLES
			                    | SENTENCIA_DECLARATIVA DECLARACION_FUNC
                                | SENTENCIA_DECLARATIVA ASIGNACION_FUNC_VAR
			                    | DECLARACION_VARIABLES
			                    | DECLARACION_FUNC
                                | ASIGNACION_FUNC_VAR
                		        ;

ASIGNACION_FUNC_VAR:            TIPO FUNC '(' TIPO ')' VARIABLES ';' {addEstructura("Declaracion de varables");
                                                                        addTipoVariables();
                                                                        }
                                | ASIGNACION_FUNC_VAR_ERROR
                                ;

ASIGNACION_FUNC_VAR_ERROR:      error FUNC '('TIPO ')' VARIABLES ';' {yyerror("Falta el tipo de la funcion.");}
                                | TIPO error '('TIPO ')' VARIABLES ';' {yyerror("Falta la palabra reservada FUNC.");}
                                | TIPO FUNC error TIPO ')' VARIABLES ';' {yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
                                | TIPO FUNC '(' error ')' VARIABLES ';' {yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
                                | TIPO FUNC '('TIPO error VARIABLES ';' {yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
                                | TIPO FUNC '('TIPO')' error ';' {yyerror("Falta el listado de variables en la asignacion de la funcion.");}
                                ;
                                
DECLARACION_FUNC:               ENCABEZADO_FUNC SENTENCIA_DECLARATIVA CUERPO_FUNC  
                                    {addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
                                | ENCABEZADO_FUNC CUERPO_FUNC  
                                    {addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
                                ;           

CUERPO_FUNC:                    BEGIN CONJUNTO_SENTENCIAS RETURN_FUNC ';' END ';'
                                | BEGIN RETURN_FUNC ';' END ';'
                                | BEGIN PRECONDICION_FUNC CONJUNTO_SENTENCIAS RETURN_FUNC ';' END ';'
                                | BEGIN PRECONDICION_FUNC RETURN_FUNC ';' END ';'
                                ;

RETURN_FUNC:                    RETURN '(' EXPRESION ')'
                                ;

PRECONDICION_FUNC:              PRE ':' '(' CONDICION ')' ',' CADENA ';'
                                ;

ENCABEZADO_FUNC:                TIPO FUNC IDENTIFICADOR {addAtributoLexema($3.sval,"TIPO",$1.sval); verificarRedeclaracion($3.sval, "ID_FUNC"); ambitoActual.add(0, $3.sval);} '(' PARAMETRO ')' 
                                | ENCABEZADO_FUNC_ERROR
                                ;                

ENCABEZADO_FUNC_ERROR:          error FUNC IDENTIFICADOR '(' PARAMETRO ')' {yyerror("Falta el tipo de la funcion.");}
                                | TIPO error IDENTIFICADOR '(' PARAMETRO ')' {yyerror("Falta la palabra clave FUNC.");}
                                | TIPO FUNC error '(' PARAMETRO ')'{yyerror("Falta el identificador de la funcion.");}
                                ;

DECLARACION_VARIABLES:          TIPO VARIABLES ';' {addEstructura("Declaracion de variables");
                                                    addTipoVariables();
                                                    }
                                | DECLARACION_VARIABLES_ERROR
                                ;

DECLARACION_VARIABLES_ERROR:    TIPO error ';' {yyerror("Variables mal declaradas.");}
                                ;

VARIABLES:                      VARIABLES ',' IDENTIFICADOR
                                    {verificarRedeclaracion($3.sval, "ID_VARIABLE");}
				                | IDENTIFICADOR {verificarRedeclaracion($1.sval, "ID_VARIABLE");}
                                ;
                
BLOQUE_SENTENCIA:               BEGIN CONJUNTO_SENTENCIAS END {addEstructura("Bloque de sentencias con BEGIN/END");}
                                | SENTENCIA_EJECUTABLE
                                | BLOQUE_SENTENCIA_ERROR
                                ;

BLOQUE_SENTENCIA_ERROR:         error CONJUNTO_SENTENCIAS END {yyerror("Falta el BEGIN del bloque de sentencia.");}
                                | BEGIN CONJUNTO_SENTENCIAS error {yyerror("Falta el END del bloque de sentencia");}
                                ;

CONJUNTO_SENTENCIAS:            CONJUNTO_SENTENCIAS SENTENCIA_EJECUTABLE
                                | SENTENCIA_EJECUTABLE
                                ;

SENTENCIA_EJECUTABLE:           IDENTIFICADOR ASIGNACION EXPRESION ';' {
                                    addEstructura("Asignacion"); 
                                    String id = getAmbitoIdentificador($1.sval);
                                    if(id == null){
                                        yyerrorSemantico("Variable \"" + $1.sval + "\" no declarada.");
                                        addTerceto(new Terceto(":=", $1.sval, $3.sval, "Error - no declarada"));
                                    } 
                                    else{
                                        if(checkTipos(id,$3.sval))
                                            addTerceto(new Terceto(":=", id, $3.sval, getTipo(id)));
                                        else 
                                            addTerceto(new Terceto(":=", id, $3.sval, "Error de tipo"));
                                    }
                                        
                                      
                                }
				                | PRINT '(' CADENA ')' ';' {addEstructura("Sentencia PRINT"); addTerceto(new Terceto("PRINT", $3.sval)); }
				                | BREAK ';' {addEstructura("BREAK"); addTerceto(new Terceto("BREAK"));}
                                | SENTENCIA_IF
                                | SENTENCIA_REPEAT
                                | IDENTIFICADOR '(' EXPRESION ')' ';' {addEstructura("Llamado a funcion");}
                                | ASIGNACION_ERROR
                                | PRINT_ERROR
                                ;

SENTENCIA_IF:                   IF CONDICION_IF THEN CUERPO_IF ENDIF ';'
                                    {addEstructura("Sentencia IF");}
                                ;

CONDICION_IF:                   '(' CONDICION ')' { Terceto terceto = new Terceto("BF", $2.sval, null);
                                                    addTerceto(terceto);
                                                    backpatching.push(terceto); }
                                ;

CUERPO_IF:                      BLOQUE_SENTENCIA
                                | BLOQUE_SENTENCIA ELSE
                                    {Terceto tercetoIncompleto = backpatching.pop(); 
                                    tercetoIncompleto.setOperando2("[" + (tercetos.size() + 1) + "]");
                                    Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                    addTerceto(bifurcacionIncondicional);
                                    backpatching.push(bifurcacionIncondicional);}
                                    BLOQUE_SENTENCIA
                                    {Terceto tercetoIncompleto = backpatching.pop();
                                    tercetoIncompleto.setOperando1("[" + tercetos.size() + "]");}
                                                        
                                ; 

SENTENCIA_REPEAT:               REPEAT '(' IDENTIFICADOR ASIGNACION CTE {
                                            String id = getAmbitoIdentificador($3.sval); 
                                            addTerceto(new Terceto(":=", id, $5.sval)); } 
                                ';' CONDICION_REPEAT { 
                                            Terceto tercetoIncompleto = new Terceto("BF", $8.sval, null); 
                                            addTerceto(tercetoIncompleto); backpatching.push(tercetoIncompleto); } 
                                ';' CTE ')' BLOQUE_SENTENCIA { 
                                        addEstructura("Sentencia REPEAT");
                                        Terceto bifurcacionFalse = backpatching.pop();
                                        Terceto destinoBifurcacionIncondicional = backpatching.pop();
                                        String id = getAmbitoIdentificador($3.sval);
                                        addTerceto(new Terceto("+", id, $11.sval));
                                        addTerceto(new Terceto(":=", id, getReferenciaUltimaInstruccion().sval));
                                        String referenciaBI = "[" + tercetos.indexOf(destinoBifurcacionIncondicional) + "]";
                                        addTerceto(new Terceto("BI", referenciaBI));
                                        String referenciaBF = "[" + tercetos.size() + "]";
                                        bifurcacionFalse.setOperando2(referenciaBF);
                                        }
                                ;



CONDICION_REPEAT:               IDENTIFICADOR OPERADOR_COMPARADOR EXPRESION {Terceto terceto = new Terceto($2.sval, getAmbitoIdentificador($1.sval), $3.sval); addTerceto(terceto); backpatching.push(terceto); $$ = getReferenciaUltimaInstruccion();}
                                ;

ASIGNACION_ERROR:               error ASIGNACION EXPRESION ';' {yyerror("Falta el identificador de la asignación.");}
                                | IDENTIFICADOR ASIGNACION error ';' {yyerror("Falta la expresión en la asignación.");}
                                ;

PRINT_ERROR:                    PRINT CADENA ')' ';' {yyerror("Falta el primer paréntesis del PRINT.");}
                                | PRINT '(' ')' ';' {yyerror("Falta la cadena del PRINT.");}
                                | PRINT '(' CADENA ';' {yyerror("Falta el último paréntesis del PRINT.");}
                                ;

CONDICION:                      CONDICION OPERADOR_LOGICO EXPRESION { addTerceto(new Terceto($2.sval, $1.sval, $3.sval)); $$ = getReferenciaUltimaInstruccion();}
                                | CONDICION OPERADOR_COMPARADOR EXPRESION {addTerceto(new Terceto($2.sval, $1.sval, $3.sval)); $$ = getReferenciaUltimaInstruccion();}
                                | EXPRESION { $$ = new ParserVal($1.sval); }
                                ;

CONVERSION:                     SINGLE '(' EXPRESION ')' { addTerceto(new Terceto("CONV", $3.sval, null, "SINGLE")); $$ = getReferenciaUltimaInstruccion();}
                                ;

EXPRESION:                      EXPRESION '+' TERMINO { 
                                    /*addTerceto(new Terceto("+", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("+",$1.sval,$3.sval);
                                    $$ = getReferenciaUltimaInstruccion(); }
                                | EXPRESION '-' TERMINO { 
                                    /*addTerceto(new Terceto("-", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("-",$1.sval,$3.sval);
                                    $$ = getReferenciaUltimaInstruccion(); }
                                | TERMINO { $$ = new ParserVal($1.sval); }
                                ;

TERMINO:                        TERMINO '*' FACTOR { 
                                    /*addTerceto(new Terceto("*", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("*",$1.sval,$3.sval);
                                    $$ = getReferenciaUltimaInstruccion(); }
                                | TERMINO '/' FACTOR { 
                                    /*addTerceto(new Terceto("/", $1.sval, $3.sval));*/
                                    addTercetoAritmetica("/",$1.sval,$3.sval);
                                    $$ = getReferenciaUltimaInstruccion();}
                                | FACTOR {$$ = new ParserVal($1.sval);}
                                ;
                                
PARAMETRO:                      TIPO IDENTIFICADOR {
                                    String identificador = setAmbitoIdentificador($2.sval);
                                    addAtributoLexema(identificador,"USO","ID_PARAMETRO"); 
                                    addAtributoLexema(identificador,"TIPO",$1.sval);
                                    }
                                | PARAMETRO_ERROR
                                ;
                                
PARAMETRO_ERROR:                TIPO error {yyerror("Falta el identificador");}
                                ;

FACTOR:                         IDENTIFICADOR { $$ = new ParserVal(getAmbitoIdentificador($1.sval));}   
                                | CTE {if (!checkRango($1.sval)){
                                            yyerror("Constante fuera de rango");
                                        }
                                        $$ = new ParserVal($1.sval); }
                                | '-' CTE {lexico.cambiarSimboloConstante($2.sval);
				                            $$ = new ParserVal("-" + $2.sval);
                                        }
                                | CONVERSION { $$ = new ParserVal($1.sval);}
                                | IDENTIFICADOR '(' EXPRESION ')' {addEstructura("Llamado a funcion como operando"); $$ = new ParserVal(getAmbitoIdentificador($1.sval));}
                                ;

OPERADOR_COMPARADOR:            '>' {$$ = new ParserVal(">");}
                                | '<' {$$ = new ParserVal("<");}
                                | MAYOR_IGUAL {$$ = new ParserVal($1.sval);}
                                | MENOR_IGUAL {$$ = new ParserVal($1.sval);}
                                | IGUALDAD {$$ = new ParserVal($1.sval);}
                                | DIFERENTE { $$ = new ParserVal($1.sval); }
                                ;

OPERADOR_LOGICO:                AND { $$ = new ParserVal($1.sval); }
                                | OR  {$$ = new ParserVal($1.sval); }
                                ;

TIPO:                           SINGLE {tipo = "SINGLE"; $$ = new ParserVal("SINGLE");}
                                | INT {tipo = "INT";  $$ = new ParserVal("INT");}
                                ;


%%

    private Lexico lexico = Lexico.getInstance();
    private List<Error> erroresSintacticos = new ArrayList<Error>(); 
    private List<Error> erroresSemanticos = new ArrayList<Error>(); 
    private List<Integer> tokensReconocidos = new ArrayList<Integer>(); 
    private List<String> estructurasReconocidas = new ArrayList<String>();
    private List<String> variables = new ArrayList<String>();
    private String tipo;
    private String nombrePrograma;
    private List<String> ambitoActual = new ArrayList<String>();
    private List<Terceto> tercetos = new ArrayList<Terceto>();
    private Stack<Terceto> backpatching = new Stack<Terceto>();
    private boolean errorSemantico = false;

    
    public static void main(String args[]){
        
    }

    public int yylex(){ 
        
        int token = 0;

        try{
            token = lexico.yylex();
        } catch(IOException e){
            
        }
        
        // En el caso que sea IDENTIFICADOR,CTE o STRING obtiene 
        // la referencia a la tabla de simbolos
        if(token  == 261 || token == 264 || token == 266) 
            yylval = lexico.getyylval();
	
        tokensReconocidos.add(token);

        return token;
    }

    public List<Integer> getTokensReconocidos(){
        return tokensReconocidos;
    }

    public void yyerror(String error){
        erroresSintacticos.add(new Error(error,false,lexico.getLinea()));
    }

    public void yyerrorSemantico(String error){
        errorSemantico = true;
        erroresSemanticos.add(new Error(error,false,lexico.getLinea()));
    }
    
    public List<Error> getErroresSintacticos(){
        return erroresSintacticos;
    }

    public List<Error> getErroresSemanticos(){
        return erroresSemanticos;
    }

    public List<String> getEstructurasReconocidas(){
        return estructurasReconocidas;
    }
    
    public boolean checkRango(String lexema){
        if(lexico.getAtributosLexema(lexema).get("TIPO").equals("INT")){
            BigDecimal valor = new BigDecimal(lexema);
            BigDecimal limite = new BigDecimal("32767");
            if(valor.compareTo(limite) > 0)
                return false;
        }
        return true;
    }
    
    public void addEstructura(String s){
        estructurasReconocidas.add(String.format("%-15s", "[Linea "+String.valueOf(lexico.getLinea())+"]") + s);
    }
    
    public void addAtributoLexema(String lexema, String nombreAtributo, Object atributo){
        Map<String, Object> atributos;
        atributos = lexico.getAtributosLexema(lexema);
        atributos.put(nombreAtributo, atributo);
    }

    public void addTipoVariables(){
        Map<String, Object> atributos;
        for(String lexema: variables){
            atributos = lexico.getAtributosLexema(lexema);
            atributos.put("TIPO", tipo);
        }
        variables.clear();
    }
/*
    public void addUsoIdentificador(String lexema, String uso) {
        Map<String, Object> atributos = lexico.getAtributosLexema(lexema);
        atributos.put("USO", uso);        
    }
*/
    public String getAmbito(List<String> ambitoActual) {
        // a partir de una lista de strings, se devuelve el ambito con el formato: '.ambito1.ambito2'.
        String ambito = "";
        for (String amb: ambitoActual) {
            ambito = "." + amb + ambito;
        }  
        return ambito; 
    }

    public String setAmbitoIdentificador(String lexema) {
        String ambito = getAmbito(ambitoActual);
        lexico.addAmbitoIdentificador(lexema,ambito);
        return lexema + ambito;
    }

    public boolean estaEnTablaSimbolos(String lexema) {
        // dado un identificador, se le agrega el ambito y se verifica si ya existe en la tabla de simbolos.
        return lexico.getTablaSimbolos().containsKey(lexema + getAmbito(ambitoActual));
    }

    public String getAmbitoIdentificador(String identificador) {
        // devuelde el identificador junto al ámbito más cercano al actual. Si no existe, devuelve null;
        Map<String, Map<String, Object>> tablaSimbolos = lexico.getTablaSimbolos();
        int cantidadAmbitos = ambitoActual.size();
        for (int i = 0; i < cantidadAmbitos; i++) {
            List<String> ambitoAux = ambitoActual.subList(i, cantidadAmbitos);
            String identificadorAux = identificador + getAmbito(ambitoAux);
            if (tablaSimbolos.containsKey(identificadorAux)) {
                tablaSimbolos.remove(identificador);
                return identificadorAux;
            }
        }
        //yyerrorSemantico("Identificador no accesible en el ámbito actual.");
        return null;
    }

    public ParserVal getReferenciaUltimaInstruccion() {
        return new ParserVal("["+ (tercetos.size() - 1) + "]");
    }

    public List<Terceto> getTercetos(){
        return tercetos;
    }

    public void verificarRedeclaracion(String identificador, String uso) {
        if (getAmbitoIdentificador(identificador) != null) {
            yyerrorSemantico("Identificador ya utilizado en el ámbito.");
        } else {
            identificador = setAmbitoIdentificador(identificador);
            variables.add(identificador);
            addAtributoLexema(identificador,"USO",uso);
        }
    }

    public String getTipo(String o){
        if(!o.startsWith("[")){
            Lexico lexico = Lexico.getInstance();
            return (String) lexico.getAtributosLexema(o).get("TIPO");
        } 
        else {
            
            String tipo = null;
            if(tercetos.size() > 0){ //Para cuando el error semantico se da antes de agregar cualquier terceto
                Terceto t = tercetos.get((Integer.parseInt(o.substring(1, o.length() - 1 ))));
                tipo = t.getTipo();
            }
            return tipo;
        }
    }

    // Como la conversion es explicita no es necesario armar una tabla de tipos
    public boolean checkTipos(String o1, String o2){
        if(!getTipo(o1).equals(getTipo(o2))){
            yyerrorSemantico("Tipos incompatibles");
            return false;
        }
        return  true;
    }

    public boolean checkDeclarado(String o1, String o2){
        if(o1 == null || o2 == null){
            return false;
        }
        return true;
    }

    public boolean checkDeclarado(String o){
        if(o == null){
            return false;
        }
        return true;
    }

    public void addTercetoAritmetica(String o, String o1, String o2){
        if(!checkDeclarado(o1,o2)){
            yyerrorSemantico("Variable no declarada.");
        } else{
            if(checkTipos(o1, o2))
                addTerceto(new Terceto(o, o1 , o2, getTipo(o1)));
            else
                addTerceto(new Terceto(o, o1 , o2, "Error de tipo"));
        }         
    }

    // En el caso de que se haya detectado un error semantico, no se genera el codigo
    // Pero se permite que el programa siga reconociendo errores y estructuras
    public void addTerceto(Terceto t){
        if(!errorSemantico)
            tercetos.add(t);
    }
    
}