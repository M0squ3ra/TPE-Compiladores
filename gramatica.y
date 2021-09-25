%{
    package com.company.Analizadores;    
    import com.company.Analizadores.Lexico;
    import com.company.Util.Error;
    import java.util.ArrayList;
    import java.util.List;
    import java.io.IOException;
%}


%token MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE CADENA AND OR IDENTIFICADOR ASIGNACION CTE ERROR INT IF THEN ELSE ENDIF PRINT FUNC RETURN BEGIN END BREAK SINGLE REPEAT PRE WHILE FLOAT DO

%left '&&'
%left '||'
%left '<' '>' MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE

%%

PROGRAMA:                       SENTENCIA_DECLARATIVA BLOQUE_SENTENCIA
                                | BLOQUE_SENTENCIA
                                | PROGRAMA_ERROR
				                ;

PROGRAMA_ERROR:                 SENTENCIA_DECLARATIVA {yyerror("Bloque principal no especificado.");}
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
                    
DECLARACION_FUNC_ERROR:         FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el tipo de la funcion.");}
                                | TIPO IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';'  {yyerror("Falta la palabra clave FUNC.");}
                                | TIPO FUNC '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el identificador de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el primer parentesis de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el parametro de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS '(' CONVERSION ')' ';' END ';' {yyerror("Falta el return de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' END ';' {yyerror("Falta el BEGIN de la funcion.");}
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' CONVERSION ')' ';' {yyerror("Falta el END de la funcion.");}
                                ;

DECLARACION_VARIABLES:          TIPO VARIABLES ';'
                                ;

VARIABLES:                      VARIABLES ',' IDENTIFICADOR
				                | IDENTIFICADOR
                                ;
                
BLOQUE_SENTENCIA:               BEGIN CONJUNTO_SENTENCIAS END
                                ;

CONJUNTO_SENTENCIAS:            CONJUNTO_SENTENCIAS SENTENCIA_EJECUTABLE
                                | SENTENCIA_EJECUTABLE
                                ;

SENTENCIA_EJECUTABLE:           IDENTIFICADOR ASIGNACION EXPRESION ';'
				                | PRINT '(' CADENA ')' ';'
				                | BREAK ';'
                                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'
				                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ENDIF ';'
                                | WHILE '(' CONDICION ')' DO BLOQUE_SENTENCIA
                                | REPEAT '(' IDENTIFICADOR ASIGNACION CTE ';' CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA
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
                                | IDENTIFICADOR error CONVERSION
                                ;

CONVERSION:                     EXPRESION
                                ;       

EXPRESION:                      EXPRESION '+' TERMINO
                                | EXPRESION '-' TERMINO
                                | EXPRESION '+' error
                                | EXPRESION '-' error
                                | TERMINO
                                ;

TERMINO:                        TERMINO '/' F
                                | TERMINO '*' F
                                | TERMINO '*' error
                                | TERMINO '/' error
                                | F
                                ;
                
PARAMETRO:                      TIPO IDENTIFICADOR
                                | TIPO error
                                ;
                
F:                              IDENTIFICADOR
                                | '-' CTE
                                | CTE
                                | IDENTIFICADOR '(' PARAMETRO ')'
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