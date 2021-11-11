%{
    package com.company.Analizadores;    
    import com.company.Analizadores.Lexico;
    import com.company.Analizadores.ParserVal;
    import com.company.Util.Error;
    import com.company.Util.Terceto;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.util.HashMap;
    import java.io.IOException;
    import java.math.BigDecimal;
    import java.util.Stack;
%}


%token CADENA IDENTIFICADOR ASIGNACION CTE ERROR INT IF THEN ELSE ENDIF PRINT FUNC RETURN BEGIN END BREAK SINGLE REPEAT PRE


%left OR
%left AND
%left '<' '>' MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE

%%

PROGRAMA:                       PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END
                                | PROGRAMA_ENCABEZADO BEGIN CONJUNTO_SENTENCIAS END 
                                | PROGRAMA_ERROR
				                ;

PROGRAMA_ENCABEZADO:            IDENTIFICADOR ';' {ambitoActual.add(0, $1.sval); addAtributoLexema($1.sval,"USO","Nombre de Programa"); nombrePrograma = $1.sval;}
                                ;

PROGRAMA_ERROR:                 PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA {yyerror("Bloque principal no especificado.");}
                                | PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA CONJUNTO_SENTENCIAS END {yyerror("BEGIN del bloque principal no especificado.");}
                                | PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS {yyerror("END del bloque principal no especificado.");}
                                | PROGRAMA_ENCABEZADO error SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END {yyerror("Falta el nombre del programa.");}
                                ;
                                	
SENTENCIA_DECLARATIVA:          SENTENCIA_DECLARATIVA DECLARACION_VARIABLES
			                    | SENTENCIA_DECLARATIVA DECLARACION_FUNC
                                | SENTENCIA_DECLARATIVA ASIGNACION_FUNC_VAR
			                    | DECLARACION_VARIABLES
			                    | DECLARACION_FUNC
                                | ASIGNACION_FUNC_VAR
                		        ;

ASIGNACION_FUNC_VAR:            TIPO FUNC '(' TIPO ')' VARIABLES_FUNC_VAR ';' {
                                    addEstructura("Declaracion de variables de funciones");
                                    Map<String, Object> atributos;
                                    for(String lexema: variables){
                                        atributos = lexico.getAtributosLexema(lexema);
                                        atributos.put("TIPO", $1.sval);
                                        atributos.put("TIPO_PARAMETRO", $4.sval);
                                    }
                                    variables.clear();
                                    }
                                | ASIGNACION_FUNC_VAR_ERROR
                                ;

ASIGNACION_FUNC_VAR_ERROR:      error FUNC '('TIPO')' VARIABLES_FUNC_VAR ';' {yyerror("Falta el tipo de la funcion.");}
                                | TIPO error '('TIPO ')' VARIABLES_FUNC_VAR ';' {yyerror("Falta la palabra reservada FUNC.");}
                                | TIPO FUNC error TIPO ')' VARIABLES_FUNC_VAR ';' {yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
                                | TIPO FUNC '(' error ')' VARIABLES_FUNC_VAR ';' {yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
                                | TIPO FUNC '('TIPO error VARIABLES_FUNC_VAR ';' {yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
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
    
RETURN_FUNC:                    RETURN '(' EXPRESION ')' {checkRetornoFuncion($3.sval); addTerceto(new Terceto("RETURN_FUNC", $3.sval, null, getTipo($3.sval)));}
                                ;

PRECONDICION_FUNC:              PRE ':' '(' CONDICION ')' ',' CADENA ';' {
                                        Terceto bifurcacionTrue = new Terceto("BT", $4.sval, null);
                                        addTerceto(bifurcacionTrue);
                                        addTerceto(new Terceto("PRINT", $7.sval + "%"));
                                        addCadena($7.sval + "%");
                                        // Terceto bifurcacionIncondicional = new Terceto("END_PRE", null);
                                        addTerceto(new Terceto("END_PRE", null));
                                        // addTerceto(bifurcacionIncondicional);
                                        // backpatching.push(bifurcacionIncondicional);
                                        bifurcacionTrue.setOperando2("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");
                                        }
                                ;

ENCABEZADO_FUNC:                TIPO FUNC IDENTIFICADOR '(' TIPO IDENTIFICADOR ')'
                                    {addAtributoLexema($3.sval,"TIPO",$1.sval);
                                    verificarRedeclaracion($3.sval, "ID_FUNC");
                                    addAtributoLexema(getAmbitoIdentificador($3.sval),"TIPO_PARAMETRO", $5.sval);

                                    ambitoActual.add(0, $3.sval);
                                    
                                    String identificador = setAmbitoIdentificador($6.sval);
                                    addAtributoLexema(identificador,"USO","ID_PARAMETRO"); 
                                    addAtributoLexema(identificador,"TIPO",$5.sval);
                                    addAtributoLexema(getAmbitoIdentificador($3.sval),"NOMBRE_PARAMETRO", identificador);
                                    }
                                ;                



DECLARACION_VARIABLES:          TIPO VARIABLES ';' {addEstructura("Declaracion de variables");
                                                    addTipoVariables();
                                                    }
                                | DECLARACION_VARIABLES_ERROR
                                ;

DECLARACION_VARIABLES_ERROR:    TIPO error ';' {yyerror("Variables mal declaradas.");}
                                ;

VARIABLES_FUNC_VAR:             VARIABLES_FUNC_VAR ',' IDENTIFICADOR
                                    {verificarRedeclaracion($3.sval, "ID_VAR_FUNC");}
				                | IDENTIFICADOR {verificarRedeclaracion($1.sval, "ID_VAR_FUNC");}
                                ;

VARIABLES:                      VARIABLES ',' IDENTIFICADOR
                                    {verificarRedeclaracion($3.sval, "ID_VARIABLE");}
				                | IDENTIFICADOR {verificarRedeclaracion($1.sval, "ID_VARIABLE");}
                                ;

BLOQUE_SENTENCIA_REPEAT:        BEGIN CONJUNTO_SENTENCIAS_REPEAT END {addEstructura("Bloque de sentencias con BEGIN/END");}
                                | SENTENCIA_EJECUTABLE_REPEAT
                                | BLOQUE_SENTENCIA_REPEAT_ERROR
                                ;

BLOQUE_SENTENCIA_REPEAT_ERROR:  error CONJUNTO_SENTENCIAS_REPEAT END {yyerror("Falta el BEGIN del bloque de sentencia.");}
                                | BEGIN CONJUNTO_SENTENCIAS_REPEAT error {yyerror("Falta el END del bloque de sentencia");}
                                ;

BLOQUE_SENTENCIA:               BEGIN CONJUNTO_SENTENCIAS END {addEstructura("Bloque de sentencias con BEGIN/END");}
                                | SENTENCIA_EJECUTABLE
                                | BLOQUE_SENTENCIA_ERROR
                                ;

BLOQUE_SENTENCIA_ERROR:         error CONJUNTO_SENTENCIAS END {yyerror("Falta el BEGIN del bloque de sentencia.");}
                                | BEGIN CONJUNTO_SENTENCIAS error {yyerror("Falta el END del bloque de sentencia");}
                                ;

CONJUNTO_SENTENCIAS_REPEAT:     CONJUNTO_SENTENCIAS_REPEAT SENTENCIA_EJECUTABLE_REPEAT
                                | SENTENCIA_EJECUTABLE_REPEAT
                                ;

CONJUNTO_SENTENCIAS:            CONJUNTO_SENTENCIAS SENTENCIA_EJECUTABLE
                                | SENTENCIA_EJECUTABLE
                                ;

SENTENCIA_EJECUTABLE_REPEAT:    BREAK ';' {addEstructura("BREAK"); addTerceto(new Terceto("BREAK"));}
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
                                        if($3.sval != null){
                                            if(!$3.sval.startsWith("[")){
                                                if(Character.isDigit($3.sval.charAt(0)) || $3.sval.startsWith(".") || $3.sval.startsWith("-")){
                                                    if(checkTipos(id,$3.sval))
                                                        addTerceto(new Terceto(":=", id, $3.sval, getTipo(id)));
                                                } else{
                                                    Map<String, Map<String, Object>> tablaSimbolos = lexico.getTablaSimbolos();
                                                    if(!tablaSimbolos.get($3.sval).get("USO").equals("ID_VAR_FUNC") && !tablaSimbolos.get($3.sval).get("USO").equals("ID_FUNC")){
                                                        if(checkTipos(id,$3.sval))
                                                            addTerceto(new Terceto(":=", id, $3.sval, getTipo(id)));
                                                        else 
                                                            addTerceto(new Terceto(":=", id, $3.sval, "Error de tipo"));
                                                    } else{
                                                        if(tablaSimbolos.get(id).get("USO").equals("ID_VAR_FUNC"))
                                                            addTerceto(new Terceto("ASIG_FUNC",id,$3.sval));
                                                        else
                                                            yyerrorSemantico("Error de asignacion, no se puede asignar una funcion a la variable del lado izquierdo");
                                                    }
                                                }
                                            }else{
                                                if(!errorAsignacion){
                                                    if(checkTipos(id,$3.sval))
                                                        addTerceto(new Terceto(":=", id, $3.sval, getTipo(id)));
                                                    else 
                                                        addTerceto(new Terceto(":=", id, $3.sval, "Error de tipo"));
                                                } else{
                                                    errorAsignacion = false;
                                                }
                                            }
                                        }
                                    }
                                        
                                      
                                }
				                | PRINT '(' CADENA ')' ';' {addEstructura("Sentencia PRINT"); addTerceto(new Terceto("PRINT", $3.sval + "%")); addCadena($3.sval + "%");}
                                | SENTENCIA_IF
                                | SENTENCIA_REPEAT
                                | ASIGNACION_ERROR
                                | PRINT_ERROR
                                | error ';' {yyerrorSemantico("Sentencia no reconocida");}
                                ;

SENTENCIA_IF:                   IF CONDICION_IF THEN CUERPO_IF ENDIF ';'
                                    {addEstructura("Sentencia IF");
                                    addTerceto(new Terceto("END_IF"));}
                                ;

CONDICION_IF:                   '(' CONDICION ')' { 
                                                    Terceto terceto = new Terceto("BF", $2.sval, null);
                                                    addTerceto(terceto);
                                                    backpatching.push(terceto);
                                                    }
                                ;

CUERPO_IF:                      BLOQUE_SENTENCIA
                                | BLOQUE_SENTENCIA ELSE
                                    {Terceto tercetoIncompleto = backpatching.pop(); 
                                    
                                    tercetoIncompleto.setOperando2("[" + (tercetos.get(getIdentificadorFuncionActual()).size() + 1) + "]");
                                    Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                    addTerceto(bifurcacionIncondicional);
                                    backpatching.push(bifurcacionIncondicional);}
                                    BLOQUE_SENTENCIA
                                    {Terceto tercetoIncompleto = backpatching.pop();
                                    tercetoIncompleto.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");}
                                                        
                                ; 

SENTENCIA_REPEAT:               REPEAT '(' IDENTIFICADOR ASIGNACION CTE {
                                            String id = getAmbitoIdentificador($3.sval); 
                                            if(!getTipo(id).equals("INT"))
                                                yyerrorSemantico("La variable debe ser de tipo entero");
                                            addTerceto(new Terceto(":=", id, $5.sval)); 
                                            addTerceto(new Terceto("REPEAT"));
                                            } 

                                ';' CONDICION_REPEAT { 
                                            Terceto tercetoIncompleto = new Terceto("BF", $8.sval, null); 
                                            addTerceto(tercetoIncompleto); backpatching.push(tercetoIncompleto); } 
                                ';' CTE ')' BLOQUE_SENTENCIA_REPEAT { 
                                        addEstructura("Sentencia REPEAT");
                                        Terceto bifurcacionFalse = backpatching.pop();
                                        Terceto destinoBifurcacionIncondicional = backpatching.pop();
                                        String id = getAmbitoIdentificador($3.sval);
                                        addTercetoAritmetica("+",id,$11.sval);
                                        addTerceto(new Terceto(":=", id, getReferenciaUltimaInstruccion().sval));
                                        String referenciaBI = "[" + tercetos.get(getIdentificadorFuncionActual()).indexOf(destinoBifurcacionIncondicional) + "]";
                                        addTerceto(new Terceto("BI", referenciaBI));
                                        String referenciaBF = "[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]";
                                        bifurcacionFalse.setOperando2(referenciaBF);
                                        addTerceto(new Terceto("END_REPEAT"));
                                        }
                                ;

CONDICION_REPEAT:               IDENTIFICADOR OPERADOR_COMPARADOR EXPRESION {
                                    if(!getTipo($3.sval).equals("INT"))
                                        yyerrorSemantico("La expresion debe ser de tipo entero");
                                    
                                    Terceto terceto = new Terceto($2.sval, getAmbitoIdentificador($1.sval), $3.sval); 
                                    addTerceto(terceto); 
                                    backpatching.push(terceto); 
                                    $$ = getReferenciaUltimaInstruccion();}
                                ;

ASIGNACION_ERROR:               error ASIGNACION EXPRESION ';' {yyerror("Falta el identificador de la asignación.");}
                                | IDENTIFICADOR ASIGNACION ERROR ';' {yyerror("Falta la expresión en la asignación.");}
                                ;

PRINT_ERROR:                    PRINT CADENA ')' ';' {yyerror("Falta el primer paréntesis del PRINT.");}
                                | PRINT '(' ')' ';' {yyerror("Falta la cadena del PRINT.");}
                                | PRINT '(' CADENA ';' {yyerror("Falta el último paréntesis del PRINT.");}
                                ;

CONDICION:                      CONDICION_OR {$$ = $1;}
                                ;

CONDICION_OR:                   CONDICION_OR OR CONDICION_AND { 
                                            if(checkTipos($1.sval, $3.sval))
                                                addTerceto(new Terceto("||", $1.sval, $3.sval, "INT")); 
                                            $$ = getReferenciaUltimaInstruccion();}
                                | CONDICION_AND {$$ = $1;}
                                ;

CONDICION_AND:                  CONDICION_AND AND CONDICION_COMPARACION { 
                                            if(checkTipos($1.sval, $3.sval))
                                                addTerceto(new Terceto("&&", $1.sval, $3.sval, "INT")); 
                                            $$ = getReferenciaUltimaInstruccion();
                                        }
                                | CONDICION_COMPARACION {$$ = $1;}
                                ;

CONDICION_COMPARACION:          EXPRESION OPERADOR_COMPARADOR EXPRESION {
                                            if(checkTipos($1.sval, $3.sval))
                                                addTerceto(new Terceto($2.sval, $1.sval, $3.sval,"INT")); 
                                            $$ = getReferenciaUltimaInstruccion();
                                            }
                                | EXPRESION { $$ = $1; }
                                ;

CONVERSION:                     SINGLE '(' EXPRESION ')' { addTerceto(new Terceto("CONV", $3.sval, null, "SINGLE")); $$ = getReferenciaUltimaInstruccion();}
                                ;

EXPRESION:                      EXPRESION '+' TERMINO { 
                                    if(checkUso($1.sval,$3.sval)){
                                        addTercetoAritmetica("+",$1.sval,$3.sval);
                                        $$ = getReferenciaUltimaInstruccion();
                                    }
                                    /*addTerceto(new Terceto("+", $1.sval, $3.sval)); */
                                    // addTercetoAritmetica("+",$1.sval,$3.sval);
                                    // $$ = getReferenciaUltimaInstruccion(); }
                                    }
                                | EXPRESION '-' TERMINO { 
                                    if(checkUso($1.sval,$3.sval)){
                                        addTercetoAritmetica("-",$1.sval,$3.sval);
                                        $$ = getReferenciaUltimaInstruccion();
                                    }
                                    /*addTerceto(new Terceto("-", $1.sval, $3.sval)); */
                                    // addTercetoAritmetica("-",$1.sval,$3.sval);
                                    // $$ = getReferenciaUltimaInstruccion(); }
                                    }
                                | TERMINO { $$ = $1; }
                                ;

TERMINO:                        TERMINO '*' FACTOR { 
                                    if(checkUso($1.sval,$3.sval)){
                                        addTercetoAritmetica("*",$1.sval,$3.sval);
                                        $$ = getReferenciaUltimaInstruccion();
                                    }
                                    // addTercetoAritmetica("*",$1.sval,$3.sval);
                                    // $$ = getReferenciaUltimaInstruccion(); }
                                    }
                                    
                                | TERMINO '/' FACTOR { 
                                    if(checkUso($1.sval,$3.sval)){
                                        addTercetoAritmetica("/",$1.sval,$3.sval);
                                        $$ = getReferenciaUltimaInstruccion();
                                    }
                                    // addTercetoAritmetica("/",$1.sval,$3.sval);
                                    // $$ = getReferenciaUltimaInstruccion();}
                                }
                                | FACTOR {$$ = $1;}
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
                                | LLAMADO_FUNCION {addEstructura("Llamado a funcion como operando"); $$ = $1;}
                                ;

LLAMADO_FUNCION:                IDENTIFICADOR '(' EXPRESION ')' {String id = getAmbitoIdentificador($1.sval);
                                                                
                                                                if (id != null) {
                                                                    if(lexico.getAtributosLexema(id).get("USO").equals("ID_FUNC") || lexico.getAtributosLexema(id).get("USO").equals("ID_VAR_FUNC")){
                                                                        if(lexico.getAtributosLexema(id).get("TIPO_PARAMETRO").equals(getTipo($3.sval))){
                                                                            addTerceto(new Terceto("CALL_FUNC", id, $3.sval,getTipo(id))); 
                                                                            $$ = getReferenciaUltimaInstruccion();
                                                                        } else {
                                                                            yyerrorSemantico("Tipo de la expresion incompatible con el parametro de la funcion");
                                                                            $$ = getReferenciaUltimaInstruccion();
                                                                        }
                                                                    } else{
                                                                        yyerrorSemantico("\"" + $1.sval + "\" es una variable, no una funcion");
                                                                        $$ = new ParserVal(id);
                                                                    }
                                                                } else {
                                                                    yyerrorSemantico("Funcion no declarada");
                                                                    $$ = new ParserVal(id);
                                                                }
                                                        }

                                ;

OPERADOR_COMPARADOR:            '>' {$$ = new ParserVal(">");}
                                | '<' {$$ = new ParserVal("<");}
                                | MAYOR_IGUAL {$$ = new ParserVal(">=");}
                                | MENOR_IGUAL {$$ = new ParserVal("<=");}
                                | IGUALDAD {$$ = new ParserVal("==");}
                                | DIFERENTE { $$ = new ParserVal("<>"); }
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
    private Map<String,List<Terceto>> tercetos = new HashMap<String,List<Terceto>>();
    private Stack<Terceto> backpatching = new Stack<Terceto>();
    private boolean err = false;
    private List<String> variablesFunciones = new ArrayList<String>();
    private List<String> cadenas = new ArrayList<String>();
    private boolean errorAsignacion = false;

    

    public int yylex(){ 
        
        int token = 0;

        try{
            token = lexico.yylex();
        } catch(IOException e){
            
        }
        
        // En el caso que sea IDENTIFICADOR,CTE o STRING obtiene 
        // la referencia a la tabla de simbolos
        if(token  == 258 || token == 260 || token == 257) 
            yylval = lexico.getyylval();
        
        if(token == 261) // token error
            err = true;
	
        tokensReconocidos.add(token);

        return token;
    }

    public List<Integer> getTokensReconocidos(){
        return tokensReconocidos;
    }

    public List<String> getCadenas(){
        return cadenas;
    }

    public String getNombrePrograma(){
        return nombrePrograma;
    }

    public void addCadena(String cadena){
        cadenas.add(cadena);
    }

    public void yyerror(String error){
        err = true;
        Error e = new Error(error,false,lexico.getLinea());
        if (!erroresSintacticos.contains(e))
            erroresSintacticos.add(e);
    }

    public void yyerrorSemantico(String error){
        err = true;
        Error e = new Error(error,false,lexico.getLinea());
        if(!erroresSemanticos.contains(e))
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
        List<Terceto> listaTercetos = tercetos.get(getIdentificadorFuncionActual());

        if(listaTercetos == null){
            tercetos.put(getIdentificadorFuncionActual(), new ArrayList<Terceto>());
            yyerrorSemantico("Error");
            return  new ParserVal("[-1]"); // Si pongo null a generar un error, pero si hago new ParserVal
                         // mas adelante me va a decir que hay error de compatibilidad de tipos
                         // cuando no es asi
        }

        return new ParserVal("["+ (listaTercetos.size() - 1) + "]");
    }

    public Map<String,List<Terceto>> getTercetos(){
        return tercetos;
    }

    public void verificarRedeclaracion(String identificador, String uso) {
        if (getAmbitoIdentificador(identificador) != null) {
            yyerrorSemantico("Identificador ya utilizado en el ámbito.");
        } else {
            identificador = setAmbitoIdentificador(identificador);
            if (uso.equals("ID_VARIABLE") || uso.equals("ID_VAR_FUNC")){
                variables.add(identificador);
                String funcion = getIdentificadorFuncionActual();
                // if(variablesFunciones.get(funcion) == null)
                //     variablesFunciones.put(funcion, new ArrayList<String>());
                variablesFunciones.add(identificador);
            }
                
            addAtributoLexema(identificador,"USO",uso);
        }
    }

    public List<String> getVariablesFunciones(){
        return variablesFunciones;
    }

    public String getTipo(String o){
        if(o == null)
            return "Error";
        if(!o.startsWith("[")){
            Lexico lexico = Lexico.getInstance();
            return (String) lexico.getAtributosLexema(o).get("TIPO");
        } 
        else {
            List<Terceto> listaTercetos = tercetos.get(getIdentificadorFuncionActual());
            String tipo = null;
            if(listaTercetos.size() > 0){ //Para cuando el error semantico se da antes de agregar cualquier terceto
                Terceto t = listaTercetos.get((Integer.parseInt(o.substring(1, o.length() - 1 ))));
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

    public String getIdentificadorFuncionActual(){
        String ambito = getAmbito(ambitoActual);
        String ambitoSplit[] = ambito.split("\\."); 
        String funcion = ambitoSplit[ambitoSplit.length - 1];
        funcion += ambito.split(funcion)[0];
        funcion = funcion.substring(0,funcion.length()-1);

        return funcion;
    }

    // En el caso de que se haya detectado un error semantico, no se genera el codigo
    // Pero se permite que el programa siga reconociendo errores y estructuras
    public void addTerceto(Terceto t){
        
        String funcion = getIdentificadorFuncionActual();
        List<Terceto> listaTercetos = tercetos.get(funcion);
        
        if(listaTercetos == null){
            listaTercetos = new ArrayList<Terceto>();
            tercetos.put(funcion,listaTercetos);
        }
        
        listaTercetos.add(t);
    }

    public boolean getError(){
        return err;
    }

    public void checkRetornoFuncion(String expresion){
        // Obtiene el lexema de la funcion
        String ambito = getAmbito(ambitoActual);
        String ambitoSplit[] = ambito.split("\\."); 
        String funcion = ambitoSplit[ambitoSplit.length - 1];
        funcion += ambito.split(funcion)[0];
        funcion = funcion.substring(0,funcion.length()-1);

        checkTipos(expresion, funcion);
    }

    public boolean checkUso(String o1, String o2){
        if(o1 == null || o2 == null)
            return false;
        if((o2.startsWith("[") || Character.isDigit(o2.charAt(0)) || o2.startsWith(".") || o2.startsWith("-")) && (o1.startsWith("[") || Character.isDigit(o1.charAt(0)) || o1.startsWith(".") || o1.startsWith("-"))){
            return true;
        } else{
            Map<String, Map<String, Object>> tablaSimbolos = lexico.getTablaSimbolos(); 
            if(o1.startsWith("[") || Character.isDigit(o1.charAt(0)) || o1.startsWith(".") || o1.startsWith("-")){
                if(tablaSimbolos.get(o2).get("USO").equals("ID_VAR_FUNC") || tablaSimbolos.get(o2).get("USO").equals("ID_FUNC")){
                    yyerrorSemantico("No se puede operar con funciones");
                    errorAsignacion = true;
                    return false;
                }
            } else if(o2.startsWith("[") || Character.isDigit(o2.charAt(0)) || o2.startsWith(".") || o2.startsWith("-")){
                if(tablaSimbolos.get(o1).get("USO").equals("ID_VAR_FUNC") || tablaSimbolos.get(o1).get("USO").equals("ID_FUNC")){
                    yyerrorSemantico("No se puede operar con funciones");
                    errorAsignacion = true;
                    return false;
                }
            } else {
                yyerrorSemantico("No se puede operar con funciones");
                errorAsignacion = true;
                return false;
            }
        }
        return true;
    }
}