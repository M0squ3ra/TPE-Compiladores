%{
    package com.company.Analizadores;    
    import com.company.Analizadores.Lexico;
    import com.company.Util.Error;
    import java.util.ArrayList;
    import java.util.List;
    import java.io.IOException;
%}


%token MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE STRING AND OR IDENTIFICADOR ASIGNACION CTE ERROR INT IF THEN ELSE ENDIF PRINT FUNC RETURN BEGIN END BREAK SINGLE REPEAT PRE PRINT WHILE FLOAT

%left '&&'
%left '||'
%left '<' '>' MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE

%%

PROGRAMA:                       SENTENCIA_DECLARATIVA BEGIN BLOQUE_SENTENCIA END
                                | BEGIN BLOQUE_SENTENCIA END
                                | error BLOQUE_SENTENCIA END {yyerror("");}
                                | BEGIN BLOQUE_SENTENCIA error {yyerror("Error. Falta el END del programa");}
				;
                                	
SENTENCIA_DECLARATIVA:          SENTENCIA_DECLARATIVA DECLARACION_VARIABLES
			        | SENTENCIA_DECLARATIVA DECLARACION_FUNC
			        | DECLARACION_VARIABLES
			        | DECLARACION_FUNC
                		;

DECLARACION_FUNC:               TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN BLOQUE_SENTENCIA RETURN '(' CONVERSION ')' END ';'
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' BEGIN BLOQUE_SENTENCIA RETURN '(' CONVERSION ')' END ';'

                                | error FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN BLOQUE_SENTENCIA RETURN '(' CONVERSION ')' END ';'
                                | TIPO FUNC error '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN BLOQUE_SENTENCIA RETURN '(' CONVERSION ')' END ';'
                                | TIPO FUNC IDENTIFICADOR '(' error ')' DECLARACION_VARIABLES BEGIN BLOQUE_SENTENCIA RETURN '(' CONVERSION ')' END ';'
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN BLOQUE_SENTENCIA error '(' CONVERSION ')' END ';'
                                | TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN BLOQUE_SENTENCIA RETURN '(' error ')' END ';'
                                ;
                
DECLARACION_VARIABLES:          TIPO VARIABLES
                                ;

VARIABLES:                      VARIABLES IDENTIFICADOR ','
				| IDENTIFICADOR ','
                                ;
                
BLOQUE_SENTENCIA:               BEGIN BLOQUE_SENTENCIA SENTENCIA_EJECUTABLE END
                                | error BLOQUE_SENTENCIA SENTENCIA_EJECUTABLE END
                                | error BLOQUE_SENTENCIA SENTENCIA_EJECUTABLE error
				| BEGIN BLOQUE_SENTENCIA END					
				| SENTENCIA_EJECUTABLE				
                                ;

SENTENCIA_EJECUTABLE:           IDENTIFICADOR ASIGNACION EXPRESION ';'
				| PRINT '(' STRING ')' ';'
				| BREAK ';'
                                | IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'
				| IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ENDIF ';'
				| WHILE CONDICION 'DO' BLOQUE_SENTENCIA
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
                                ;
                
CONDICION_REPEAT:               IDENTIFICADOR '>' CONVERSION
                                | IDENTIFICADOR '<' CONVERSION
		                | IDENTIFICADOR MAYOR_IGUAL CONVERSION
                                | IDENTIFICADOR MENOR_IGUAL CONVERSION
		                | IDENTIFICADOR IGUALDAD CONVERSION
                                | IDENTIFICADOR DIFERENTE CONVERSION
                                ;

CONVERSION:                     TIPO '(' EXPRESION ')'
	                        | EXPRESION
                                ;       

EXPRESION:                      CONVERSION '+' TERMINO
                                | CONVERSION '-' TERMINO
                                | TERMINO
                                ;

TERMINO:                        TERMINO '/' F
                                | TERMINO '*' F
                                | F
                                ;
                
PARAMETRO:                      TIPO IDENTIFICADOR
                                ;
                
F:                              IDENTIFICADOR
                                |'-' CTE
                                | CTE
                                | IDENTIFICADOR '(' PARAMETRO ')'
                                ;

TIPO:                           INT
                                | FLOAT
                                ;


%%

    private Lexico lexico = Lexico.getInstance();
    private List<Error> errores = new ArrayList<Error>(); 

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
	
        return token;
    }

    public void yyerror(String error){
        errores.add(new Error(error,false,lexico.getLinea()));
    }