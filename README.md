TPE Compiladores

### Requerimientos
- Linux
- mvn (Maven)
- Wine
- yacc.exe (Byacc para java)

### Parser:
- generar parser: wine "yacc.exe" "-J" "gramatica.y"
- generar parser y archivo verbose: wine "yacc.exe" "-v" "-J" "gramatica.y"

Cuando se genera el parser, añadirle el modificador public a la funcion yyparse() para que se pueda ejecutar en el Main

### Generar compilador
- mvn clean compile assembly:single

### Compilar Programa 
- ./compilador.sh ruta/programa.txt
