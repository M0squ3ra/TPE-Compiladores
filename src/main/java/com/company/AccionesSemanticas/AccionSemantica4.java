package com.company.AccionesSemanticas;

import java.util.HashMap;
import java.util.Map;

import com.company.Analizadores.Lexico;
import com.company.Util.ParTokenLexema;
import com.company.Util.TokensID;
import com.company.Util.Error;

public class AccionSemantica4 implements AccionSemantica {

    /*
    
        Se devuelve a la entrada el último caracter leído. Se determina si lo que hay
        en el buffer es una palabra reservada o un identificador (sea nuevo o no).

     */

    @Override
    public void aplicarAccionSemantica(char c) {

        // Devolver a la entrada el último caracter leído.
        Lexico.getInstance().addSimboloEntradaInicio(c);    
        

        // Ver si lo almacenado en el buffer es una palabra reservada.
        Lexico lexico = Lexico.getInstance();
        String buffer = lexico.getBuffer();
        Integer token = TokensID.getTokenPalabraReservada(buffer);

        if (token != null){
            lexico.addToken(new ParTokenLexema(token, null));
        } else {
            token = TokensID.IDENTIFICADOR;
            
            if (lexico.containsTablaSimbolos(buffer)) {
                // caso en que ya está registrado el lexema en la tabla de símbolos.
                lexico.addToken(new ParTokenLexema(token, buffer));
            } else {
                // caso en que el lexema no se encuentra en la tabla de símbolos.
                Map<String, Object> propiedadesLexema = new HashMap<String, Object>();
                propiedadesLexema.put("TIPO", "ID");

                // almacenar otras propiedades.
                if (buffer.length() > Lexico.TAMANO_BUFFER){
                    buffer = buffer.substring(0, 21);
                    Error error = new Error("El identificador no puede superar los 22 caracteres. Se procede a recortarlo.", true, lexico.getLinea());
                    lexico.addError(error);
                }

                // se agrega el lexema a la tabla de símbolos.
                lexico.addLexemaTablaSimbolos(propiedadesLexema);
                
                // devolver par [token, lexema]
                lexico.addToken(new ParTokenLexema(token, buffer));
            }
            
        }

                
    }
}