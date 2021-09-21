%{
    
%}

%token MAYOR_IGUAL MENOR_IGUAL IGUALDAD DIFERENTE STRING AND OR IDENTIFICADOR ASIGNACION CTE ERROR INT IF THEN ELSE ENDIF PRINT FUNC RETURN BEGIN END BREAK SINGLE REPEAT PRE PRINT WHILE FLOAT



%%

PROGRAMA:                       IDENTIFICADOR SENTENCIA_DECLARATIVA BEGIN BLOQUE_SENTENCIA END
				;
                                	
SENTENCIA_DECLARATIVA:          SENTENCIA_DECLARATIVA DECLARACION_VARIABLES
			        | SENTENCIA_DECLARATIVA FUNC
			        | DECLARACION_VARIABLES
			        | FUNC
                		;

DECLARACION_FUNC:               TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN BLOQUE_SENTENCIA RETURN '(' CONVERSION ')' END
                                ;
                
DECLARACION_VARIABLES:          TIPO VARIABLES
                                ;
                
VARIABLES:                      VARIABLES IDENTIFICADOR ','
				| IDENTIFICADOR ','
                                ;
                
BLOQUE_SENTENCIA:               BEGIN BLOQUE_SENTENCIA SENTENCIA_EJECUTABLE END
				|BEGIN BLOQUE_SENTENCIA END					
				| SENTENCIA_EJECUTABLE				
                                ;

SENTENCIA_EJECUTABLE:           IDENTIFICADOR ASIGNACION  EXPRESION ';'
				| PRINT '(' STRING ')' ';'
				| BREAK ';'
                                | IF '(' COMP_AND ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'
				| IF '(' COMP_AND ')' THEN BLOQUE_SENTENCIA ENDIF ';'
				| WHILE COMP_AND 'DO' BLOQUE_SENTENCIA
				| REPEAT '(' IDENTIFICADOR ASIGNACION CTE ';' CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA 
                                ;
                
COMP_AND:                       COMP_AND AND COMP_OR
				| COMP_OR
                                ;

COMP_OR:                        COMP_OR OR COMPARADOR
				| COMPARADOR
                                ;

COMPARADOR:                     COMPARADOR '>' CONVERSION
				| COMPARADOR '<' CONVERSION
				| COMPARADOR MAYOR_IGUAL CONVERSION
				| COMPARADOR MENOR_IGUAL CONVERSION
				| COMPARADOR IGUALDAD CONVERSION
				| COMPARADOR DIFERENTE CONVERSION
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