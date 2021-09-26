%{
    package com.company.Analizadores;    
    import com.company.Analizadores.Lexico;
    import com.company.Util.Error;
    import java.util.ArrayList;
    import java.util.List;
    import java.io.IOException;
    import java.math.BigDecimal;
%}


%token MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE CADENA AND OR IDENTIFICADOR ASIGNACION CTE ERROR INT IF THEN ELSE ENDIF PRINT FUNC RETURN BEGIN END BREAK SINGLE REPEAT PRE FLOAT

%left '&&'
%left '||'
%left '<' '>' MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE

%%

PROGRAMA:                       SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END
                                | BEGIN CONJUNTO_SENTENCIAS END
                                | PROGRAMA_ERROR
				                ;

PROGRAMA_ERROR:                 SENTENCIA_DECLARATIVA {yyerror("Bloque principal no especificado.");}
                                | SENTENCIA_DECLARATIVA BLOQUE_SENTENCIA END {yyerror("BEGIN del bloque principal no especificado.");}
                                | SENTENCIA_DECLARATIVA BEGIN BLOQUE_SENTENCIA {yyerror("END del bloque principal no especificado.");}
                                ;
                                	
SENTENCIA_DECLARATIVA:          SENTENCIA_DECLARATIVA DECLARACION_VARIABLES
			                    | SENTENCIA_DECLARATIVA DECLARACION_FUNC
			                    | DECLARACION_VARIABLES
			                    | DECLARACION_FUNC
                		        ;

DECLARACION_FUNC:               TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';'
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';'
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN PRE ':' '(' CONDICION ')' ',' CADENA ';' CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';'
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' BEGIN PRE ':' '(' CONDICION ')' ',' CADENA ';' CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';'
                                | DECLARACION_FUNC_ERROR
                                ;           
                    
DECLARACION_FUNC_ERROR:         error FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el tipo de la funcion.");}
                                | TIPO error IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';'  {yyerror("Falta la palabra clave FUNC.");}
                                | TIPO FUNC error '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el identificador de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR error PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el primer parentesis de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' error ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el parametro de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO error DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el segundo parentesis de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' error DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS '(' CONVERSION ')' ';' END ';' {yyerror("Falta el return de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES error CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el BEGIN de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' error ';' {yyerror("Falta el END de la funcion.");}
                                ;

DECLARACION_VARIABLES:          TIPO VARIABLES ';'
                                | DECLARACION_VARIABLES_ERROR
                                ;

DECLARACION_VARIABLES_ERROR:    TIPO error ';' {yyerror("Variables mal declaradas.");}
                                | TIPO VARIABLES error {yyerror("Falta el ';' en la declaracion de variables.");}
                                ;

VARIABLES:                      VARIABLES ',' IDENTIFICADOR
				                | IDENTIFICADOR
                                ;
                
BLOQUE_SENTENCIA:               BEGIN CONJUNTO_SENTENCIAS END
                                | SENTENCIA_EJECUTABLE
                                | BLOQUE_SENTENCIA_ERROR
                                ;

BLOQUE_SENTENCIA_ERROR:         error CONJUNTO_SENTENCIAS END {yyerror("Falta el BEGIN del bloque de sentencia.");}
                                | BEGIN CONJUNTO_SENTENCIAS error {yyerror("Falta el END del bloque de setnencia");}
                                ;

CONJUNTO_SENTENCIAS:            CONJUNTO_SENTENCIAS SENTENCIA_EJECUTABLE
                                | SENTENCIA_EJECUTABLE
                                ;

SENTENCIA_EJECUTABLE:           IDENTIFICADOR ASIGNACION EXPRESION ';'
				                | PRINT '(' CADENA ')' ';'
				                | BREAK ';'
                                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'
				                | IF '(' CONDICION ')C' THEN BLOQUE_SENTENCIA ENDIF ';'
                                | REPEAT '(' IDENTIFICADOR ASIGNACION CONSTANTE ';' CONDICION_REPEAT ';' CONSTANTE ')' BLOQUE_SENTENCIA
                                | ASIGNACION_ERROR
                                | PRINT_ERROR
                                | IF_ERROR
                                | REPEAT_ERROR
                                ;

ASIGNACION_ERROR:               error ASIGNACION EXPRESION ';' {yyerror("Falta el identificador de la asignación.");}
                                | IDENTIFICADOR error EXPRESION ';' {yyerror("Falta el ':=' en la asignación.");}
                                | IDENTIFICADOR ASIGNACION error ';' {yyerror("Falta la expresión en la asignación.");}
                                | IDENTIFICADOR ASIGNACION EXPRESION error {yyerror("Falta el ';' en la asignación.");}
                                ;

PRINT_ERROR:                    PRINT CADENA ')' ';' {yyerror("Falta el primer paréntesis del PRINT.");}
                                | PRINT '(' ')' ';' {yyerror("Falta la cadena del PRINT.");}
                                | PRINT '(' CADENA ';' {yyerror("Falta el último paréntesis del PRINT.");}
                                | PRINT '(' CADENA ')' {yyerror("Falta el ';' del PRINT.");}
                                ;

IF_ERROR:                       IF error CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta el primer paréntesis de la condición del IF.");}
                                | IF '(' error ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta la condición del IF.");}
                                | IF '(' CONDICION error THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta el último paréntesis de la condición del IF.");}
                                | IF '(' CONDICION ')' error BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta el THEN del IF.");}
                                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA error BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta el ELSE del IF.");}
                                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA error ';' {yyerror("Falta el ENDIF del IF.");}
                                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF error {yyerror("Falta el ';' del IF.");}
                                | IF error CONDICION ')' THEN BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta el primer paréntesiis de la condición del IF.");}
                                | IF '(' error ')' THEN BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta la condición del IF.");}
                                | IF '(' CONDICION error THEN BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta el último paréntesis de la condición del IF.");}
                                | IF '(' CONDICION ')' error BLOQUE_SENTENCIA ENDIF ';' {yyerror("Falta el THEN del IF.");}
                                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA error ';' {yyerror("Falta el ENDIF del IF.");}
                                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ENDIF error {yyerror("Falta el ';' del IF.");}
                                ;

REPEAT_ERROR:                   REPEAT '(' error ASIGNACION CONSTANTE ';' CONDICION_REPEAT ';' CONSTANTE ')' BLOQUE_SENTENCIA {yyerror("Falta el identificador del REPEAT.");}
                                | REPEAT '(' IDENTIFICADOR error CONSTANTE ';' CONDICION_REPEAT ';' CONSTANTE ')' BLOQUE_SENTENCIA {yyerror("Falta el asignador al identificador del REPEAT.");}
                                | REPEAT '(' IDENTIFICADOR ASIGNACION error ';' CONDICION_REPEAT ';' CONSTANTE ')' BLOQUE_SENTENCIA {yyerror("El identificador no tiene constante a asignar del REPEAT.");}
                                | REPEAT '(' IDENTIFICADOR ASIGNACION CONSTANTE error CONDICION_REPEAT ';' CONSTANTE ')' BLOQUE_SENTENCIA {yyerror("Falta ';' luego de la asignacion del REPEAT.");}
                                | REPEAT '(' IDENTIFICADOR ASIGNACION CONSTANTE ';' error ';' CONSTANTE ')' BLOQUE_SENTENCIA {yyerror("Falta la condicion para que cicle el bloque  del REPEAT.");}
                                | REPEAT '(' IDENTIFICADOR ASIGNACION CONSTANTE ';' CONDICION_REPEAT error CONSTANTE ')' BLOQUE_SENTENCIA  {yyerror("Falta ';' luego de la condicion del REPEAT.");}
                                | REPEAT '(' IDENTIFICADOR ASIGNACION CONSTANTE ';' CONDICION_REPEAT ';' error ')' BLOQUE_SENTENCIA  {yyerror("Falta la constante de iteracion del REPEAT.");}
                                | REPEAT error IDENTIFICADOR ASIGNACION CONSTANTE ';' CONDICION_REPEAT ';' CONSTANTE ')' BLOQUE_SENTENCIA  {yyerror("Falta el primer paréntesis del REPEAT.");}
                                | REPEAT '(' IDENTIFICADOR ASIGNACION CONSTANTE ';' CONDICION_REPEAT ';' CONSTANTE error BLOQUE_SENTENCIA  {yyerror("Falta el segundo paréntesis del REPEAT.");}
                                ;

CONDICION:                      CONDICION AND CONVERSION
                                | CONDICION OR CONVERSION
                                | CONDICION '>' CONVERSION
                                | CONDICION '<' CONVERSION
                                | CONDICION MAYOR_IGUAL CONVERSION
                                | CONDICION MENOR_IGUAL CONVERSION
                                | CONDICION IGUALDAD CONVERSION
                                | CONDICION DIFERENTE CONVERSION
                                | CONVERSION
                                ;
                
CONDICION_REPEAT:               IDENTIFICADOR '>' CONVERSION
                                | IDENTIFICADOR '<' CONVERSION
		                        | IDENTIFICADOR MAYOR_IGUAL CONVERSION
                                | IDENTIFICADOR MENOR_IGUAL CONVERSION
		                        | IDENTIFICADOR IGUALDAD CONVERSION
                                | IDENTIFICADOR DIFERENTE CONVERSION
                                | CONDICION_REPEAT_ERROR
                                ;

CONDICION_REPEAT_ERROR:         IDENTIFICADOR error CONVERSION {yyerror("Falta el comparador.");}
                                ;

CONVERSION:                     EXPRESION
                                ;       

EXPRESION:                      EXPRESION '+' TERMINO
                                | EXPRESION '-' TERMINO
                                | TERMINO
                                | EXPRESION_ERROR
                                ;

EXPRESION_ERROR:                EXPRESION '+' error {yyerror("Falta el segundo termino.");}
                                | EXPRESION '-' error {yyerror("Falta el segundo termino.");}
                                ;
                                
TERMINO:                        TERMINO '/' F
                                | TERMINO '*' F
                                | F
                                | TERMINO_ERROR
                                ;

TERMINO_ERROR:                  TERMINO '*' error {yyerror("Falta un termino a multiplicar.");}
                                | TERMINO '/' error {yyerror("Falta un termino a dividir.");}
                                ;
                                
PARAMETRO:                      TIPO IDENTIFICADOR
                                | PARAMETRO_ERROR
                                ;
                                
PARAMETRO_ERROR:                TIPO error {yyerror("Falta el identificador");}
                                ;
                
F:                              IDENTIFICADOR   
                                | CONSTANTE
                                | IDENTIFICADOR '(' PARAMETRO ')'
                                ;

CONSTANTE:                      CTE {if (!checkRango($1.sval)){
                                        yyerror("Constante fuera de rango");
                                            }}
                                | '-' CTE {lexico.cambiarSimboloConstante($2.sval);
				                            $$ = new ParserVal("-" + $2.sval);
                                        }
                                ;

TIPO:                           FLOAT
                                | INT
                                ;


%%

    private Lexico lexico = Lexico.getInstance();
    private List<Error> erroresSintacticos = new ArrayList<Error>(); 
    private List<Integer> tokensReconocidos = new ArrayList<Integer>(); 

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
    
    public List<Error> getErroresSintacticos(){
        return erroresSintacticos;
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